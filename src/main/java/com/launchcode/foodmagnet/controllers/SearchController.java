package com.launchcode.foodmagnet.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.data.jsonData.ApiRequests;
import com.launchcode.foodmagnet.models.data.jsonData.JsonMapper;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.*;

@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public String displaySearchPage(Model model) throws URISyntaxException, IOException, InterruptedException {

        //fieldMap used to correlate the name of a restaurant with all of that restaurants fields.
//        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();
//
//        for (Restaurant restaurant : RestaurantData.getRestaurantsNearby("Los Angeles")) {
//            fieldMap.put(restaurant.getName(), restaurant.getAllFields());
//        }


        return "search";
    }

    @PostMapping
    public String processSearchInput(@RequestParam String searchInput, Model model) {

        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();
        ArrayList<Restaurant> restaurants = RestaurantData.getRestaurantsNearby(searchInput);


        if (restaurants != null) {
            for (Restaurant restaurant : restaurants) {
                fieldMap.put(restaurant.getName(), restaurant.getAllFields());

            }
            model.addAttribute("restaurants", fieldMap);

        } else {
            model.addAttribute("validation", "Please enter a valid city name.");

        }

        return "search";
    }

}
