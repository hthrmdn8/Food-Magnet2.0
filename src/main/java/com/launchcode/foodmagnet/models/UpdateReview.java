package com.launchcode.foodmagnet.models;

public class UpdateReview {

    private int updateReviewId;
    private int rating;
    private String comments;
    public UpdateReview(){}

    public UpdateReview(int updateReviewId, int rating, String comments) {
        this.updateReviewId = updateReviewId;
        this.rating = rating;
        this.comments = comments;
    }

    public int getUpdateReviewId() {
        return updateReviewId;
    }

    public void setUpdateReviewId(int updateReviewId) {
        this.updateReviewId = updateReviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}