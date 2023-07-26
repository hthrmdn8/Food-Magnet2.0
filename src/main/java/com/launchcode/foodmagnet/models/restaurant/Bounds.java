package com.launchcode.foodmagnet.models.restaurant;

public class Bounds {

    private Location northeast;

    private Location southwest;

    public Bounds() {}

    public Location getNortheast() {
        return northeast;
    }

    public void setNortheast(Location northeast) {
        this.northeast = northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Location southwest) {
        this.southwest = southwest;
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "northeast=" + northeast +
                ", southwest=" + southwest +
                '}';
    }
}
