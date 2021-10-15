package com.voytek.bikeShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@RestController
public class Controller {

    BikeService bikeService;
    PartsService partsService;

    @Autowired
    public Controller(BikeService bikeService, PartsService partsService) {
        this.bikeService = bikeService;
        this.partsService = partsService;
    }

    @PostMapping("/bike/new")
    public long addNewBike(@Valid @RequestBody Bike bike) {
        return bikeService.addNewBike(bike);
    }

    @GetMapping("/bike/{id}")
    public Bike getBike(@PathVariable long id) {
        return bikeService.getBike(id);
    }


    @GetMapping("/bike/all")
    public Iterable<Bike> getAllBikes(){
        return bikeService.getAllBikesPriced();
    }


    @GetMapping("/bike/producer/{producer}")
    public Iterable<Bike> getAllBikesByProducer(@PathVariable String producer){
        return bikeService.getAllBikesByProducer(producer);
    }

    @GetMapping("/bike/all/{page}")
    public Iterable<Bike> getAllBikesSortedByName(@PathVariable int page) {
        return bikeService.getAllBikesSortedByName(page);
    }

    @GetMapping("/bike/name/{name}")
    public Bike getBikeByName(@PathVariable String name) {
        return bikeService.getBikeByName(name);
    }


    @DeleteMapping("/bike/{id}")
    public String delBike(@PathVariable long id) {
        return bikeService.delBike(id);
    }

    @DeleteMapping("/bike/name/{name}")
    public String delBikeByName(@PathVariable String name) {
        return bikeService.delBikeByName(name);
    }

    @PutMapping("/bike/{id}")
    public Bike modifyBike(@Valid @RequestBody Bike bikeMod, @PathVariable long id) {
        return bikeService.modifyBike(bikeMod, id);
    }

    @PutMapping("/bike/name/{name}")
    public Bike modifyBikeByName(@Valid @RequestBody Bike bikeMod, @PathVariable String name) {
        return bikeService.modifyBikeByName(bikeMod, name);
    }

    @GetMapping("/bike/price/{name}")
    public int getBikePrice(@PathVariable String name) {
        return bikeService.getBikePrice(name);
    }


}




