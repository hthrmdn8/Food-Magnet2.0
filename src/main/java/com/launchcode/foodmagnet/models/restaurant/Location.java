package com.launchcode.foodmagnet.models.restaurant;

public class Location {

    private double lat;

    private double lng;

    public Location() {}

    public double getLat() {
        return lat;
    }

    public String getLatString() { return String.valueOf(lat); }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public String getLngString() { return String.valueOf(lng); }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
