package com.voytek.bikeshop;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartsRepository extends PagingAndSortingRepository<Parts, Long> {

}