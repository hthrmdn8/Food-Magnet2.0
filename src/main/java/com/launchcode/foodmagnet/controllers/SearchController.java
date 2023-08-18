package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.data.jsonData.ApiRequests;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@Autowired
    RestaurantRepository restaurantRepository;
    @GetMapping
    public String displaySearchPage(Model model) throws URISyntaxException, IOException, InterruptedException {

        //fieldMap used to correlate the name of a restaurant with all of that restaurants fields.
        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

        for (Restaurant restaurant : RestaurantData.getRestaurantsNearby("Los Angeles")) {
            fieldMap.put(restaurant.getName(), restaurant.getAllFields());
        }

        model.addAttribute("title", "Search Restaurants");
        //model.addAttribute("restaurants", RestaurantData.getRestaurantsNearby("St. Louis"));
        model.addAttribute("restaurants", RestaurantData.getSpecificRestaurantsNearby("Los Angeles", "Chinese"));
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


//         RestaurantEntity test = restaurantRepository.findByPlaceId("ChIJcQ0R1rBqkFQR8kvZfk8COTE");
//         model.addAttribute("test", test.toString());

        model.addAttribute("location", searchInput);

        return "search";
    }

}
