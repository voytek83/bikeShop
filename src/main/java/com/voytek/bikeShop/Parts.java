package com.voytek.bikeShop;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="PARTS")
public class Parts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long partId;

    private String partName;

    @ManyToOne()
    @JoinColumn(name = "bike_id")
    @JsonBackReference
    private Bike bike;

    private int partPrice;

    public Parts() {
    }

    public long getPartId() {
        return partId;
    }

    public void setPartId(long partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public int getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(int partPrice) {
        this.partPrice = partPrice;
    }

    @Override
    public String toString() {
        return "Parts{" +
                "id=" + partId +
                ", partName='" + partName + '\'' +
                ", bike=" + bike +
                ", partPrice=" + partPrice +
                '}';
    }


}
