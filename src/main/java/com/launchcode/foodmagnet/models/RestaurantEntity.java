package com.launchcode.foodmagnet.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class RestaurantEntity {
    @Id
    //@Column(name = "place_id")
    private String placeId;
    private String name;

    @OneToMany(mappedBy = "restaurantEntity")
    private List<Review> reviews;

    public RestaurantEntity() {
    }

    public RestaurantEntity(String placeId, String name, List<Review> reviews) {
        this.placeId = placeId;
        this.name = name;
        this.reviews = reviews;
    }

    public RestaurantEntity(String placeId, String name) {
        this.placeId = placeId;
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String restaurantId) {
        this.placeId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                ", name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
