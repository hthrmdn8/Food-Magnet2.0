package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public String displaySearchPage(Model model) throws URISyntaxException, IOException, InterruptedException {

        //fieldMap used to correlate the name of a restaurant with all of that restaurants fields.
        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

        for (Restaurant restaurant : RestaurantData.getRestaurantsNearby("Los Angeles")) {
            fieldMap.put(restaurant.getName(), restaurant.getAllFields());
        }

        //model.addAttribute("restaurants", RestaurantData.getRestaurantsNearby("St. Louis"));
        return "search";
    }

    @PostMapping
    public String processSearchInput(@RequestParam String searchInput, Model model) {

        ArrayList<Restaurant> restaurants = RestaurantData.getRestaurantsNearby(searchInput);


        if (restaurants != null) {
            model.addAttribute("restaurants", restaurants);


        } else {
            model.addAttribute("validation", "Please enter a valid city name.");

        }

        return "search";
    }

}
