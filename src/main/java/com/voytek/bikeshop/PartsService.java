package com.voytek.bikeshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartsService {

    BikeRepository bikeRepository;
    PartsRepository partsRepository;

    @Autowired
    public PartsService(BikeRepository bikeRepository, PartsRepository partsRepository) {
        this.bikeRepository = bikeRepository;
        this.partsRepository = partsRepository;
    }

    public long addNewPart(Parts parts) {
        Parts partNew = partsRepository.save(parts);
        return partNew.getId();
    }


}
