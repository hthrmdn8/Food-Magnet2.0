package com.launchcode.foodmagnet.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int restaurantId;
    private String name;
    @OneToMany(mappedBy = "restaurantEntity")
    private List<Review> reviews;

    public RestaurantEntity() {
    }

    public RestaurantEntity(int restaurantId, String name, List<Review> reviews) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.reviews = reviews;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
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
}
