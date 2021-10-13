package com.voytek.bikeShop;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Entity
@Table(name="BIKE")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bikeId;

    @NotEmpty(message = "Bike name is necessary")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Bike Producer is necessary")
    @Column(nullable = false, unique = true)
    private String producer;

    @JsonManagedReference
    @OneToMany(mappedBy = "bike",cascade=CascadeType.ALL)
    private List<Parts> parts;

    public String getProducer() {
        return producer;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public List<Parts> getParts() {
        return parts;
    }

    public void setParts(List<Parts> parts) {
        this.parts = parts;
    }

    public Bike() {
    }

    public Bike(Long id, String name, String producer, List<Parts> parts) {
        this.bikeId = id;
        this.name = name;
        this.producer = producer;
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + bikeId +
                ", name='" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", parts=" + parts +
                '}';
    }
}
