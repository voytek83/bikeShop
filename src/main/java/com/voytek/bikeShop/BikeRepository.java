package com.voytek.bikeShop;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BikeRepository extends PagingAndSortingRepository<Bike, Long> {

    Bike findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);


}
