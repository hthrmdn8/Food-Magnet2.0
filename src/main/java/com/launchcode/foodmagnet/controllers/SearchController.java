package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.data.jsonData.ApiRequests;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.models.restaurant.RestaurantPackage;
import com.launchcode.foodmagnet.models.service.UserService;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("search")
public class SearchController {
@Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserService userService;
    @GetMapping
    public String displaySearchPage(Model model, Principal principal)  {

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);
        }


        model.addAttribute("title", "Search Restaurants");

        //model.addAttribute("restaurants", RestaurantData.getSpecificRestaurantsNearby("Los Angeles", "Chinese"));
        //model.addAttribute("restaurants", RestaurantData.getSpecificRestaurantsNearby("Los Angeles", "mexican"));
        //model.addAttribute("restaurants", RestaurantData.getSpecificRestaurantsNearby("St. Louis", "", ""));
        return "search";
    }

    @PostMapping
    public String processSearchInput(@RequestParam String searchInput,
                                     @RequestParam(value = "searchType", required = false) String searchType,
                                     @RequestParam(value = "selectedFilter", required = false) String selectedFilter,
                                     Model model, Principal principal) {

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);
        }

        if (selectedFilter == null || selectedFilter.equals("Remove Filter")) selectedFilter = "";
        if (searchType == null) searchType = "Location";


        if (searchType.equals("Location")) {
            RestaurantPackage locationResults = RestaurantData.getSpecificRestaurantsNearby(searchInput, "", selectedFilter);

            if (locationResults != null) {
                model.addAttribute("restaurants", locationResults.getPageOfRestaurants());
                model.addAttribute("location", searchInput);

            } else {
                model.addAttribute("validation", "Please enter a valid city name.");

            }

        } else if (searchType.equals("Cuisine")) {
            User user = userService.findByUsername(principal.getName());

            RestaurantPackage cuisineResults = RestaurantData.getSpecificRestaurantsNearby(user.getLocation(), searchInput, selectedFilter);

            if (cuisineResults != null) {
                model.addAttribute("restaurants", cuisineResults.getPageOfRestaurants());
                model.addAttribute("location", user.getLocation());
                model.addAttribute("cuisine", searchInput);

            } else {
                model.addAttribute("validation", "Please enter a valid city name.");

            }
        }

        return "search";
    }

}
