package org.example.environement.service;

import org.example.environement.dto.travellog.TravellogDtoReceive;
import org.example.environement.dto.travellog.TravellogDtoResponse;
import org.example.environement.dto.travellog.TravellogDtoStat;
import org.example.environement.entity.Observation;
import org.example.environement.entity.Travellog;
import org.example.environement.exception.NotFoundException;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.repository.TravellogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravellogService {

    private final TravellogRepository travellogRepository;
    private final ObservationRepository observationRepository;

    public TravellogService(TravellogRepository travellogRepository, ObservationRepository observationRepository) {
        this.travellogRepository = travellogRepository;
        this.observationRepository = observationRepository;
    }

    public TravellogDtoResponse create(TravellogDtoReceive dto) {
        Observation observation = observationRepository.findById(dto.getObservationId())
                .orElseThrow(() -> new NotFoundException("Observation non trouvée"));

        Travellog travellog = dto.dtoToEntity();
        travellog.setObservation(observation);

        travellog.calculateCO2();

        return travellogRepository.save(travellog).entityToDto();
    }

    public List<TravellogDtoResponse> getAll() {
        return travellogRepository.findAll().stream()
                .map(Travellog::entityToDto)
                .collect(Collectors.toList());
    }

    public List<TravellogDtoResponse> get(int limit) {
        return travellogRepository.findAll().stream()
                .limit(limit)
                .map(Travellog::entityToDto)
                .collect(Collectors.toList());
    }

    public TravellogDtoStat getStat(long observationId) {
        Map<String, Object> stats = getStatsByObservation(observationId);

        TravellogDtoStat dtoStat = new TravellogDtoStat();
        dtoStat.setTotalDistanceKm((Double) stats.get("totalDistanceKm"));
        dtoStat.setTotalEmissionsKg((Double) stats.get("totalEmissionsKg"));
        dtoStat.setByMode((Map<String, Double>) stats.get("byMode"));

        return dtoStat;
    }

    public Map<String, Object> getStatsByObservation(Long observationId) {
        List<Travellog> logs = travellogRepository.findByObservationId(observationId);

        double totalDistance = logs.stream().mapToDouble(Travellog::getDistanceKm).sum();
        double totalEmissions = logs.stream().mapToDouble(Travellog::getEstimatedCo2Kg).sum();

        Map<String, Double> byMode = logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getMode().name(),
                        Collectors.summingDouble(Travellog::getEstimatedCo2Kg)
                ));

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDistanceKm", totalDistance);
        stats.put("totalEmissionsKg", totalEmissions);
        stats.put("byMode", byMode);

        return stats;
    }


    public Map<String, TravellogDtoStat> getStatForUserLastMonth(String username) {
        List<Observation> observations = observationRepository.findByUsernameAndDateBetween(
                username,
                LocalDate.now().minusMonths(1),
                LocalDate.now()
        );

        Map<String, TravellogDtoStat> statsByObservation = new HashMap<>();

        for (Observation obs : observations) {
            TravellogDtoStat stat = getStat(obs.getId());
            statsByObservation.put("Observation_" + obs.getId(), stat);
        }

        return statsByObservation;
    }

}
