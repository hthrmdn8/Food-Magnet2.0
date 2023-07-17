package com.launchcode.foodmagnet.models.data.placesapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.data.jsonparsing.Json;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class RestaurantData {

    public static JsonNode parseJson() throws IOException, InterruptedException {

        HttpResponse<String> response = PlacesRequest.sendPrototype();

        JsonNode rawNode = Json.parse(response.body());
        JsonNode results = rawNode.get("results");

        return results;
    }

    public static ArrayList<Restaurant> getRestaurants() throws IOException, InterruptedException {

        JsonNode results = parseJson();

        ArrayList<Restaurant> restaurants = new ArrayList<>();

        for (JsonNode result : results) {
            Restaurant restaurant = Json.fromJson(result, Restaurant.class);
            restaurants.add(restaurant);
        }

        return restaurants;
    }

}
