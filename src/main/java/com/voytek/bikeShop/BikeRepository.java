package com.voytek.bikeShop;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface BikeRepository extends PagingAndSortingRepository<Bikes, Long> {

    Bikes findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query(value = "SELECT * FROM bike_shop ORDER BY price",
            nativeQuery = true)
    Collection<Bikes> findAllBikesSortedByPrice();

}
