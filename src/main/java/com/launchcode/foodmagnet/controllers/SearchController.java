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
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.DoubleStream;

import static com.launchcode.foodmagnet.models.data.RestaurantData.getRestaurantsNearby;

@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public String displaySearchPage(Model model) throws URISyntaxException, IOException, InterruptedException {

        //fieldMap used to correlate the name of a restaurant with all of that restaurants fields.
        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

        for (Restaurant restaurant : getRestaurantsNearby("Los Angeles")) {
            fieldMap.put(restaurant.getName(), restaurant.getAllFields());
        }


        return "search";
    }

    @PostMapping
    public String processSearchInput(@RequestParam String searchInput, @RequestParam String searchType, Model model) {

        ArrayList<Restaurant> restaurants = getRestaurantsNearby(searchInput);
        ArrayList<Restaurant> filteredResults = new ArrayList<>();

        if (restaurants != null) {
            if (searchType == "all") {
                model.addAttribute("restaurants", restaurants);
            } else {
                for (Restaurant restaurant : restaurants) {
                    HashMap<String, Object> allFields = restaurant.getAllFields();
                    for (String field : allFields.keySet()) {
                        if (field == searchType && allFields.get(field) != null) {
                            filteredResults.add(restaurant);
                        }
                    }
                }
                model.addAttribute("restaurants", filteredResults);
            }

        } else {
            model.addAttribute("validation", "Please enter a valid city name.");
        }


//        if (restaurants != null || SearchType.equals("all")) {
//            for (Restaurant restaurant : restaurants) {
//                fieldMap.put(restaurant.getName(), restaurant.getAllFields());
//
//            }
//            model.addAttribute("restaurants", fieldMap);
//        } else if (SearchType.equals("breakfast")) {
//            for (Restaurant restaurant : restaurants) {
//                fieldMap.put(restaurant.getName(), restaurant.);
//                ArrayList<Restaurant> restaurantArrayList = getRestaurantsNearby(searchInput).getTypes("breakfast");
//            }
//            model.addAttribute("restaurants", fieldMap);
//        } else if (SearchType.equals("lunch")) {
//            for (Restaurant restaurant : restaurants) {
//                fieldMap.put(restaurant.getName(), restaurant.getAllFields().getTypes("lunch"));
//
//            }
//            model.addAttribute("restaurants", fieldMap);
//        } else if (SearchType.equals("dinner")){
//            for (Restaurant restaurant : restaurants) {
//                fieldMap.put(restaurant.getName(), restaurant.getAllFields().getTypes("dinner"));
//
//            }
//            model.addAttribute("restaurants", fieldMap);
//        } else {
//            model.addAttribute("validation", "Please enter a valid city name.");
//
//        }
//
        return "search";
    }
}
