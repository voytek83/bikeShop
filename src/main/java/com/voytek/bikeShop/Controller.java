package com.voytek.bikeShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    public Iterable<Bike> getAllBikes() {
        return bikeService.getAllBikesPriced();
    }

    @GetMapping("/bike/allCSV")
    public void getAllBikesCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        Iterable<Bike> bikeList = bikeService.getAllBikesPriced();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Bike ID", "Name", "producer", "Bike Price"};
        String[] nameMapping = {"id", "name", "producer", "bikePrice"};

        csvWriter.writeHeader(csvHeader);

        bikeList.forEach(bike -> {
            try {
                csvWriter.write(bike, nameMapping);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        csvWriter.close();


    }


    @GetMapping("/bike/producer/{producer}")
    public Iterable<Bike> getAllBikesByProducer(@PathVariable String producer) {
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

    @GetMapping("/bike/regularCustomer/{name}")
    public Bike getBikeByNameForRegularCustomer(@PathVariable String name) {
        return bikeService.getBikeByNameForRegularCustomer(name);
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
        return BikeService.getBikePrice(bikeService.findBikeByNameIgnoreCase(name));
    }


}




