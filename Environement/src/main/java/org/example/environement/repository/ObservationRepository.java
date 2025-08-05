package org.example.environement.repository;

import org.example.environement.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long> {

    // Trouver les observations par lieu (LIKE)
    List<Observation> findObservationByLocationIsLike(String location);

    // Trouver les observations par ID de l'espèce
    List<Observation> findObservationBySpecieId(Long specieId);
}
