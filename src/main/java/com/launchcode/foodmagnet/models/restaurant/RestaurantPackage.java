package com.launchcode.foodmagnet.models.restaurant;

import java.util.ArrayList;

public class RestaurantPackage {

    //holds up to 20 restaurants per page of results
    private ArrayList<Restaurant> pageOfRestaurants;

    //Token used to retrieve next page of results
    private String nextPageToken;

    //true if the nextPageToken is null, indicating there are no more results.
    private Boolean lastPage;

    public RestaurantPackage(ArrayList<Restaurant> pageOfRestaurants, String nextPageToken) {
        this.pageOfRestaurants = pageOfRestaurants;
        this.nextPageToken = nextPageToken;
    }

    public void addPageOfRestaurants(ArrayList<Restaurant> restaurants) {
        ArrayList<Restaurant> newPageOfRestaurants = new ArrayList<>();
        newPageOfRestaurants.addAll(restaurants);
        newPageOfRestaurants.addAll(this.pageOfRestaurants);

        System.out.println(this.getPageOfRestaurants());
        this.pageOfRestaurants = newPageOfRestaurants;
    }

    public ArrayList<Restaurant> getPageOfRestaurants() {
        return pageOfRestaurants;
    }

    public void setPageOfRestaurants(ArrayList<Restaurant> pageOfRestaurants) {
        this.pageOfRestaurants = pageOfRestaurants;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
