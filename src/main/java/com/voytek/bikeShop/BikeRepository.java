package com.voytek.bikeShop;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface BikeRepository extends PagingAndSortingRepository<Bikes, Long> {

    Bikes findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

}
