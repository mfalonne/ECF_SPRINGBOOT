package org.example.environement.repository;

import org.example.environement.entity.Travellog;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TravellogRepositoryPaginate extends PagingAndSortingRepository<Travellog,Long> {
}
