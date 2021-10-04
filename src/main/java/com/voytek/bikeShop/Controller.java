package com.voytek.bikeShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api/bikes")
public class Controller {


        @Autowired
        BikeRepository bikeRepository;


        @PostMapping("/new")
        public ResponseEntity<String> enterNewBike(@Valid @RequestBody Bikes bike) {
            Bikes newBike = bikeRepository.save(bike);
            long id = bike.getId();
            return ResponseEntity.ok("{\"id\":" + id + "\n }");

        }

        @GetMapping("/{id}")
        public Optional<Bikes> getBike(@PathVariable long id) {
            if (!bikeRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Bike not found for id = " + id);
            }
            return bikeRepository.findById(id);
        }

    @GetMapping("/all")
    public Iterable<Bikes> getAllBikes() {
        return bikeRepository.findAll();
    }


        @DeleteMapping("/{id}")
        public ResponseStatusException delBike(@PathVariable long id) {
            if (!bikeRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else {
                bikeRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
        }
}
