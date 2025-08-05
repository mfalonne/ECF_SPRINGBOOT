package org.example.environement.repository;

import org.example.environement.entity.Observation;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ObservationRepositoryPaginate extends PagingAndSortingRepository<Observation,Long> {
}
