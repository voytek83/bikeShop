package com.voytek.bikeShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.sql.*;
import java.util.Optional;


@RestController
public class Controller {


    @Autowired
    BikeRepository bikeRepository;


    @PostMapping("/bike")
    public ResponseEntity<String> enterNewBike(@Valid @RequestBody Bikes bike) {
        Bikes newBike = bikeRepository.save(bike);
        long id = bike.getId();
        return ResponseEntity.ok("{\"id\":" + id + "\n }");

    }

    @GetMapping("/bike/{id}")
    public Optional<Bikes> getBike(@PathVariable long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Bike not found for id = " + id);
        }
        return bikeRepository.findById(id);
    }


    @GetMapping("/bike/all")
    public Iterable<Bikes> getAllBikes() {
        return bikeRepository.findAll();
    }

    @GetMapping("/bike/all/{page}")
    public Iterable<Bikes> getAllBikesByName(@PathVariable int page) {
        Pageable sortedByName =
                PageRequest.of(page, 3, Sort.by("name"));
        return bikeRepository.findAll(sortedByName);
    }


    @DeleteMapping("/bike/{id}")
    public String delBike(@PathVariable long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            bikeRepository.deleteById(id);
            return "bike deleted";
        }
    }

    @PutMapping("/bike/{id}")
    public Optional<Bikes> modifyBike(@Valid @RequestBody Bikes bikeMod, @PathVariable long id) {
        if (!bikeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Bike not found for id = " + id);
        }
        bikeRepository.findById(id).map(bikes -> {
            bikes.setName(bikeMod.getName());
            bikes.setPrice(bikeMod.getPrice());
            bikes.setDescription(bikeMod.getDescription());
            return bikeRepository.save(bikes);


        });
        return bikeRepository.findById(id);
    }

}




