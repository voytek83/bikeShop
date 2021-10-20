package com.voytek.bikeShop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BikeServiceTest {

    @ParameterizedTest(name="For set bike with part prices {0},{1},{2} should return bikePrice {3}")
    @CsvSource({
            "50     ,       100     ,       150     ,           300",
            "1      ,       1       ,       1       ,           3",
            "500    ,       500     ,       500     ,           1500",

    })
    void getBikePrice(int price1,int price2,int price3, int bikePrice) {

        List<Parts> partsList = new ArrayList<>();
        Parts part1 = new Parts();
        part1.setPartPrice(price1);
        partsList.add(part1);
        Parts part2 = new Parts();
        part2.setPartPrice(price2);
        partsList.add(part2);
        Parts part3 = new Parts();
        part3.setPartPrice(price3);
        partsList.add(part3);
        Bike bike = new Bike();
        bike.setPartsList(partsList);

        int result = BikeService.getBikePrice(bike);

        Assertions.assertEquals(bikePrice, result);

    }


    @ParameterizedTest(name="For bike price {0} it calculates price with set discount {1}% should return price {2}")
    @CsvSource({
            "300        ,       5      ,       285",
            "300        ,       10     ,       270",
            "500        ,       100    ,       0"
                })
    void forGivenBikeAndDiscountCalculatePrice (int bikePrice, int discount, int expectedDiscountedPrice) {
        var result = BikeService.calculateBikePriceDiscounted(bikePrice,discount);
        Assertions.assertEquals(expectedDiscountedPrice,result);

    }




}