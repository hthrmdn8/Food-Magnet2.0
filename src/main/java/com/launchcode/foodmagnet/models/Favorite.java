package com.launchcode.foodmagnet.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Favorite {

    @Id
    @Column(name = "place_id")
    private String placeId;

    @Column(name = "restaurant_name")
    private String favouriteRestaurant;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    // Constructors, getters, and setters

    public Favorite() {
    }

    public Favorite(String placeId, String favouriteRestaurant, User user) {
        this.placeId = placeId;
        this.favouriteRestaurant = favouriteRestaurant;
        this.user = user;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFavouriteRestaurant() {
        return favouriteRestaurant;
    }

    public void setFavouriteRestaurant(String favouriteRestaurant) {
        this.favouriteRestaurant = favouriteRestaurant;
    }
}