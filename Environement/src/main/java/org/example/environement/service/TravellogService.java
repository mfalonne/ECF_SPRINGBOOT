package org.example.environement.service;

import org.example.environement.dto.travelLog.TravellogDtoReceive;
import org.example.environement.dto.travelLog.TravellogDtoResponse;
import org.example.environement.entity.Observation;
import org.example.environement.entity.Travellog;
import org.example.environement.entity.enums.TravelMode;
import org.example.environement.exception.NotFoundException;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.repository.TravellogRepository;
import org.springframework.stereotype.Service;

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
}
