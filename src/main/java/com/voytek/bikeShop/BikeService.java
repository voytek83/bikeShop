package com.voytek.bikeShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Service
public class BikeService {
    BikeRepository bikeRepository;
    PartsRepository partsRepository;

    @Autowired
    public BikeService(BikeRepository bikeRepository, PartsRepository partsRepository) {
        this.bikeRepository = bikeRepository;
        this.partsRepository = partsRepository;
    }

    public long addNewBike(Bike bike) {
        Bike newBike = bikeRepository.save(bike);
        long id = newBike.getBikeId();
        List<Parts> partsList = newBike.getParts();
        for (Parts part : partsList) {
            part.setBike(newBike);
            partsRepository.save(part);
        }

        return newBike.getBikeId();
    }

    public Optional<Bike> getBike(long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for id = " + id);
        }
        return bikeRepository.findById(id);
    }

    public Iterable<Bike> getAllBikesSortedByName(int page) {
        Pageable sortedByName = PageRequest.of(page, 3, Sort.by("name"));
        return bikeRepository.findAll(sortedByName);
    }

    public Iterable<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    public Bike getBikeByName(String name) {
        if (!bikeRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for name = " + name);
        }
        return bikeRepository.findByNameIgnoreCase(name);
    }

    public String delBike(long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            bikeRepository.deleteById(id);
            return "bike deleted";
        }
    }

    public String delBikeByName(String name) {
        if (!bikeRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for name = " + name);
        } else {
            Bike bike = bikeRepository.findByNameIgnoreCase(name);
            bikeRepository.deleteById(bike.getBikeId());
            return "bike deleted";
        }
    }

    public Optional<Bike> modifyBike(Bike bikeMod, long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Bike not found for id = " + id);
        }
        bikeRepository.findById(id).map(bike -> {
            bike.setName(bikeMod.getName());
            return bikeRepository.save(bike);
        });
        return bikeRepository.findById(id);
    }

    public Optional<Bike> modifyBikeByName(Bike bikeMod, String name) {
        if (!bikeRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for name = " + name);
        }
        Bike bike = bikeRepository.findByNameIgnoreCase(name);
        bikeRepository.findById(bike.getBikeId()).map(bikes -> {
            bikes.setName(bikeMod.getName());
            return bikeRepository.save(bikes);
        });
        return bikeRepository.findById(bike.getBikeId());
    }

    public int getBikePrice(String name) {
        int bikePrice = 0;
        Bike bike = bikeRepository.findByNameIgnoreCase(name);
        List<Parts> partsList = bike.getParts();
        for (Parts part : partsList) {
            bikePrice += part.getPartPrice();
        }
        return bikePrice;
    }


}
