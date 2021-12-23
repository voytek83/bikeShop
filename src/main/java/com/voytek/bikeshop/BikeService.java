package com.voytek.bikeshop;

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
        var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bike.getName()));
        var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
        bike.setBikePrice(taxedBikePrice);
        return bike;

    }

    public Iterable<Bike> getAllBikesSortedByName(int page) {
        Pageable sortedByName = PageRequest.of(page, 3, Sort.by("name"));
        Iterable<Bike> bikes = bikeRepository.findAll(sortedByName);
        bikes.forEach(bike -> {
            var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bike.getName()));
            var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
            bike.setBikePrice(taxedBikePrice);
        });
        return bikes;
    }

    public Iterable<Bike> getAllBikesPriced() {
        Iterable<Bike> bikes = bikeRepository.findAll();
        bikes.forEach(bike -> {
            var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bike.getName()));
            var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
            bike.setBikePrice(taxedBikePrice);
        });
        return bikes;
    }

    public Iterable<Bike> getAllBikesByProducer(String producer) {
        Iterable<Bike> bikes = bikeRepository.findByProducerIgnoreCase(producer);
        bikes.forEach(bike -> {
            var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bike.getName()));
            var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
            bike.setBikePrice(taxedBikePrice);
        });
        return bikes;
    }

    public Bike getBikeByName(String name) {
        if (!bikeRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for name = " + name);
        }
        Bike bike = bikeRepository.findByNameIgnoreCase(name);
        var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bike.getName()));
        var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
        bike.setBikePrice(taxedBikePrice);
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
        var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bikeMod.getName()));
        var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
        bikeSaved.setBikePrice(taxedBikePrice);
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
        var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bikeMod.getName()));
        var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
        bikeSaved.setBikePrice(taxedBikePrice);
        return bikeSaved;
    }

    public static int getBikePrice(Bike bike) {
        int bikePrice = 0;
        List<Parts> partsList = bike.getPartsList();
        for (Parts part : partsList) {
            bikePrice += part.getPartPrice();
        }
        return bikePrice;
    }

    public Bike findBikeByNameIgnoreCase(String name) {
        return bikeRepository.findByNameIgnoreCase(name);
    }

    public static int calculateBikePriceDiscounted(int bikePrice, int discountInPercents) {
        return bikePrice - bikePrice * discountInPercents / 100;
    }

    public static int calculateBikePriceTaxed(int bikePrice, int taxRate) {
        return bikePrice + bikePrice * taxRate / 100;
    }


    public Bike getBikeByNameForRegularCustomer(String name) {
        if (!bikeRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike not found for name = " + name);
        }
        Bike bike = bikeRepository.findByNameIgnoreCase(name);
        var bikePrice = getBikePrice(findBikeByNameIgnoreCase(bike.getName()));
        var taxedBikePrice = calculateBikePriceTaxed(bikePrice, TaxAndDiscounts.getTaxRate());
        var discountedPrice = calculateBikePriceDiscounted(taxedBikePrice, TaxAndDiscounts.getReturningClientDiscount());
        bike.setBikePrice(discountedPrice);
        return bike;
    }
}
