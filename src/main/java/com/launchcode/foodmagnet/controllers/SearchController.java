package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.RestaurantPackage;
import com.launchcode.foodmagnet.service.UserService;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;

@SessionAttributes(value = {"restaurants", "location", "cuisine"})
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
                                     @RequestParam(value = "location", required = false) String location,
                                     Model model, Principal principal) {

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);
        }

        if (selectedFilter == null || selectedFilter.equals("Remove Filter")) selectedFilter = "";
        if (searchType == null) searchType = "Location";

        if (searchType.equals("Location")) {
            model.addAttribute("cuisine", null);
            RestaurantPackage locationResults = RestaurantData.getSpecificRestaurantsNearby(searchInput, "", selectedFilter);

            if (locationResults != null) {
                model.addAttribute("restaurants", locationResults);
                model.addAttribute("location", searchInput);

            } else {
                model.addAttribute("validation", "Please enter a valid city name.");
            }

        } else if (searchType.equals("Cuisine")) {

            if (location != null) {
                RestaurantPackage cuisineResults = RestaurantData.getSpecificRestaurantsNearby(location, searchInput, selectedFilter);

                if (cuisineResults != null) {
                    model.addAttribute("restaurants", cuisineResults);
                    model.addAttribute("location", location);
                    model.addAttribute("cuisine", searchInput);

                } else {
                    model.addAttribute("validation", "Please enter a valid city name.");

                }

            } else {
                User user = userService.findByUsername(principal.getName());
                RestaurantPackage cuisineResults = RestaurantData.getSpecificRestaurantsNearby(user.getLocation(), searchInput, selectedFilter);

                if (cuisineResults != null) {
                    model.addAttribute("restaurants", cuisineResults);
                    model.addAttribute("location", user.getLocation());
                    model.addAttribute("cuisine", searchInput);

                } else {
                    model.addAttribute("validation", "Please enter a valid city name.");

                }
            }
        }

        return "search";
    }

    @PostMapping(value = "/next")
    public String loadMoreRestaurants(@ModelAttribute("restaurants") RestaurantPackage restaurantPkg,
                                      @RequestParam(value = "location", required = false) String location,
                                      @RequestParam(value = "cuisine", required = false) String cuisine, Model model,
                                      RedirectAttributes redirectAttributes) {

        if (restaurantPkg.getNextPageToken() != null) {
            RestaurantPackage newRestaurantPkg = RestaurantData.getNextPageResults(restaurantPkg.getNextPageToken());
            newRestaurantPkg.addPageOfRestaurants(restaurantPkg.getPageOfRestaurants());

            model.addAttribute("restaurants", newRestaurantPkg);

        } else {
            model.addAttribute("No more results.", "No more results.");
            model.addAttribute("restaurants", restaurantPkg);

        }
        redirectAttributes.addFlashAttribute("location", location);
        redirectAttributes.addFlashAttribute("cuisine", cuisine);
        System.out.println(location);

        return "redirect:/search";
    }
}
