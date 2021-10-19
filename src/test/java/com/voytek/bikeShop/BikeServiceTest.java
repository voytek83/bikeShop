package com.voytek.bikeShop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BikeServiceTest {

    @Test
    void getBikePrice() {

        List<Parts> partsList = new ArrayList<>();
        Parts part1 = new Parts();
        part1.setPartPrice(50);
        partsList.add(part1);
        Parts part2 = new Parts();
        part2.setPartPrice(100);
        partsList.add(part2);
        Parts part3 = new Parts();
        part3.setPartPrice(150);
        partsList.add(part3);
        Bike bike = new Bike();
        bike.setPartsList(partsList);
        int result = BikeService.getBikePrice(bike);

        Assertions.assertEquals(300,result);

    }
}