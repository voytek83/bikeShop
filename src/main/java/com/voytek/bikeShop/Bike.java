package com.voytek.bikeShop;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "bike")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Bike name is necessary")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Bike Producer is necessary")
    @Column(nullable = false)
    private String producer;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "bike_parts",
            joinColumns = @JoinColumn(name = "bike_id"),
            inverseJoinColumns = @JoinColumn(name = "parts_id"))

    private List<Parts> partsList = new ArrayList<>();

    @Transient
    private int bikePrice;

    public Bike() {
    }

    public Bike(Long id, String name, String producer) {
        this.id = id;
        this.name = name;
        this.producer = producer;
    }

    public Bike(Long id, String name, String producer, List<Parts> parts) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.partsList = parts;
    }

    public Bike(Long id, String name, String producer, List<Parts> partsList, int bikePrice) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.partsList = partsList;
        this.bikePrice = bikePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }

    public int getBikePrice() {
        return bikePrice;
    }

    public void setBikePrice(int bikePrice) {
        this.bikePrice = bikePrice;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", partsList=" + partsList +
                ", bikePrice=" + bikePrice +
                '}';
    }

    //metody
    public void addParts(Parts parts) {
        partsList.add(parts);
    }

}
