package com.launchcode.foodmagnet.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private int reviewId;
    @NonNull
    private int ratings;
    private String comments;

    private String createdByUser;

    @ManyToOne
    @JoinColumn(name = "restaurant_restaurantId")
    private RestaurantEntity restaurantEntity;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    public Review() {
    }

    public Review(int reviewId, int ratings, String comments, String createdByUser, RestaurantEntity restaurantEntity, User user) {
        this.reviewId = reviewId;
        this.ratings = ratings;
        this.comments = comments;
        this.createdByUser = createdByUser;
        this.restaurantEntity = restaurantEntity;
        this.user = user;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
