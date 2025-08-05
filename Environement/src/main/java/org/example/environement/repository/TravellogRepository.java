package org.example.environement.repository;

import org.example.environement.entity.Travellog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravellogRepository extends JpaRepository<Travellog, Long> {

    public List<Travellog> findTravellogByObservation_Id (long id);


//    @Query("select t from Travellog t where t.observation.observerName = ?1 and t.observation.observationDate > ?2")
//    public List<Travellog> findTravellogByUserForLastMonth (String user, LocalDate date);

    List<Travellog> findByObservationId(Long observationId);
}
