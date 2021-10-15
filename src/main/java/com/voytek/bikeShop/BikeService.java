package com.voytek.bikeShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
public class BikeService {
    BikeRepository bikeRepository;

    @Autowired
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public long addNewBike(Bike bike) {
        Bike newBike = bikeRepository.save(bike);
        long id = newBike.getId();
        return newBike.getId();
    }

    public Bike getBike(long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for id = " + id);
        }
        Bike bike = bikeRepository.findById(id);
        bike.setBikePrice(getBikePrice(bike.getName()));
        return bike;

    }

    public Iterable<Bike> getAllBikesSortedByName(int page) {
        Pageable sortedByName = PageRequest.of(page, 3, Sort.by("name"));
        Iterable<Bike> bikes = bikeRepository.findAll(sortedByName);
        bikes.forEach(bike -> bike.setBikePrice(getBikePrice(bike.getName())));
        return bikes;
    }

    public Iterable<Bike> getAllBikesPriced() {
        Iterable<Bike> bikes = bikeRepository.findAll();
        bikes.forEach(bike -> bike.setBikePrice(getBikePrice(bike.getName())));
        return bikes;
    }

    public Iterable<Bike> getAllBikesByProducer(String producer) {
        Iterable<Bike> bikes = bikeRepository.findByProducerIgnoreCase(producer);
        bikes.forEach(bike -> bike.setBikePrice(getBikePrice(bike.getName())));
        return bikes;
    }

    public Bike getBikeByName(String name) {
        if (!bikeRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for name = " + name);
        }
        Bike bike = bikeRepository.findByNameIgnoreCase(name);
        bike.setBikePrice(getBikePrice(bike.getName()));
        return bike;
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
            bikeRepository.deleteById(bike.getId());
            return "bike deleted";
        }
    }

    public Bike modifyBike(Bike bikeMod, long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Bike not found for id = " + id);
        }
        Bike bike = bikeRepository.findById(id);
        bike.setName(bikeMod.getName());
        bike.setProducer(bikeMod.getProducer());
        bike.setPartsList(bikeMod.getPartsList());
        bikeRepository.save(bike);
        Bike bikeSaved = bikeRepository.findByNameIgnoreCase(bikeMod.getName());
        bikeSaved.setBikePrice(getBikePrice(bikeMod.getName()));
        return bikeSaved;
    }

    public Bike modifyBikeByName(Bike bikeMod, String name) {
        if (!bikeRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for name = " + name);
        }
        Bike bike = bikeRepository.findByNameIgnoreCase(name);
        bike.setName(bikeMod.getName());
        bike.setProducer(bikeMod.getProducer());
        bike.setPartsList(bikeMod.getPartsList());
        bikeRepository.save(bike);
        Bike bikeSaved = bikeRepository.findByNameIgnoreCase(bikeMod.getName());
        bikeSaved.setBikePrice(getBikePrice(bikeMod.getName()));
        return bikeSaved;
    }

    public int getBikePrice(String name) {
        int bikePrice = 0;
        Bike bike = bikeRepository.findByNameIgnoreCase(name);
        List<Parts> partsList = bike.getPartsList();
        for (Parts part : partsList) {
            bikePrice += part.getPartPrice();
        }
        return bikePrice;
    }

}
