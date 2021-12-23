package com.voytek.bikeshop;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends PagingAndSortingRepository<Bike, Long> {

    Bike findByNameIgnoreCase(String name);
    Bike findById(long id);

    boolean existsByNameIgnoreCase(String name);
    List<Bike> findByProducerIgnoreCase(String producer);


}
