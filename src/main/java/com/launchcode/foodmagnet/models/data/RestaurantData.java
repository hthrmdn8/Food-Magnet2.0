package com.launchcode.foodmagnet.models.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.data.jsonData.ApiRequests;
import com.launchcode.foodmagnet.models.data.jsonData.JsonMapper;
import com.launchcode.foodmagnet.models.restaurant.Location;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class RestaurantData {

    //Takes in place_id argument, returns usable restaurant pojo with photos and information about the restaurant
    public static Restaurant getRestaurantDetails(String place_id)  {

        HttpResponse<String> response = null;
        try {
            ApiRequests.placeDetailsRequest(place_id);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        JsonNode node = JsonMapper.parse(response.body());
        JsonNode result = node.get("result");
        Restaurant restaurant = JsonMapper.fromJson(result, Restaurant.class);

        return restaurant;
    }

    //pareses json response from nearbySearch method into an array of Restaurant pojos
    public static ArrayList<Restaurant> getRestaurantsNearby(String address) {

        HttpResponse<String> response = null;
        try {
            ApiRequests.placeNearbySearchRequest(address);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException (e);
        }

        JsonNode node = JsonMapper.parse(response.body());
        JsonNode results = node.get("results");

        ArrayList<Restaurant> restaurantList = new ArrayList<>();

        for (JsonNode result : results) {
            Restaurant restaurant = JsonMapper.fromJson(result, Restaurant.class);
            restaurantList.add(restaurant);
        }

        return restaurantList;
    }

    //parses response from placePhotoRequest into url linking a jpeg image
    public static String getPhoto(String photoReference) {

        HttpResponse<String> photoResponse = null;
        try {
            photoResponse = ApiRequests.placePhotoRequest(photoReference);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Document doc = Jsoup.parse(photoResponse.body());
        String htmlAttachment = String.valueOf(doc.getElementsByTag("A").unwrap());
        String imageUrl = htmlAttachment.substring(9, htmlAttachment.length() - 6);

        return imageUrl;
    }

    public static Location getCoordinates(String address)  {

        HttpResponse<String> jsonResponse = null;
        try {
            ApiRequests.placeGeocodingRequest(address);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        JsonNode node = JsonMapper.parse(jsonResponse.body());
        JsonNode locationNode = node.get("results").get(0).get("geometry").get("location");

        Location coordinates = JsonMapper.fromJson(locationNode, Location.class);

        return coordinates;
    }

}
