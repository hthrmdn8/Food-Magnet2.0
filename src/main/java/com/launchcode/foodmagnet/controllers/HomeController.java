package com.launchcode.foodmagnet.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.data.CarouselData;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.data.jsonData.ApiRequests;
import com.launchcode.foodmagnet.models.data.jsonData.JsonMapper;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {


    @GetMapping
    public String redirectToHome() {

        return "redirect:home";
    }

    @GetMapping("home")
    public String index(Model model, @RequestParam("userLocation") Optional<String> userLocation) throws IOException, URISyntaxException, InterruptedException {


        if (userLocation.isPresent()) {
            HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

            for (Restaurant restaurant : RestaurantData.getRestaurantsNearby(userLocation.get())) {
                fieldMap.put(restaurant.getName(), restaurant.getAllFields());

            }
            model.addAttribute("featuredRestaurants", fieldMap);

        }

        model.addAttribute("title", "Food Magnet");
        model.addAttribute("photos", CarouselData.getCarouselPhotos());

        //ArrayList<Restaurant> testRestaurantList = RestaurantData.getRestaurantsNearby("St. Louis");
        //Restaurant testRestaurant = RestaurantData.getRestaurantDetails(testRestaurantList.get(0).getPlaceId());
        HttpResponse<String> response = null;
        try {
            response = ApiRequests.placeNearbySearchRequest("st. louis");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException (e);
        } if (response == null) return null;

        JsonNode node = JsonMapper.parse(response.body());
        JsonNode results = node.get("results");

        Restaurant restaurant = JsonMapper.fromJson(results.get(0), Restaurant.class);

        model.addAttribute("test", restaurant);

        return "home";
    }
}