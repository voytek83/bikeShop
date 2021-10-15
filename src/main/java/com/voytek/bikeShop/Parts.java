package com.voytek.bikeShop;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Parts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String partName;

    private int partPrice;


    @ManyToMany(mappedBy = "partsList")
    private List<Bike> bikeList = new ArrayList<>();

    public Parts() {
    }

    public Parts(long id, String partName, int partPrice) {
        this.id = id;
        this.partName = partName;
        this.partPrice = partPrice;
    }

    public Parts(long id, String partName, int partPrice, List<Bike> bike) {
        this.id = id;
        this.partName = partName;
        this.partPrice = partPrice;
        this.bikeList = bike;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(int partPrice) {
        this.partPrice = partPrice;
    }

    public List<Bike> getBike() {
        return bikeList;
    }

    public void setBike(List<Bike> bike) {
        this.bikeList = bike;
    }

    @Override
    public String toString() {
        return "Parts{" +
                "partId=" + id +
                ", partName='" + partName + '\'' +
                ", partPrice=" + partPrice +
                ", bike=" + bikeList +
                '}';
    }

    //metody
    public void addBikes(Bike bike) {
        bikeList.add(bike);
    }
}
