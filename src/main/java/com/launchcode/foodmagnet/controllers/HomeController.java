package com.launchcode.foodmagnet.controllers;


import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.CarouselData;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.RestaurantPackage;
import com.launchcode.foodmagnet.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@SessionAttributes("featuredRestaurants")
@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    UserService userService;


    @GetMapping
    public String redirectToHome() {

        return "redirect:home";
    }

    @GetMapping("home")
    public String index(Model model, Principal principal) {

        if (!model.containsAttribute("featuredRestaurants") && principal != null) {
            User user = userService.findByUsername(principal.getName());

            RestaurantPackage restaurantPkg = RestaurantData.getSpecificRestaurantsNearby(user.getLocation(), "", "");
            model.addAttribute("featuredRestaurants", restaurantPkg);

        }

        model.addAttribute("title", "Food Magnet");
        model.addAttribute("photos", CarouselData.getCarouselPhotos());

        return "home";
    }

    @PostMapping("home/next")
    public String loadMoreFeaturedRestaurants(@ModelAttribute("featuredRestaurants") RestaurantPackage restaurantPkg, Model model, Principal principal) {

        if (restaurantPkg.getNextPageToken() != null) {
            RestaurantPackage newRestaurantPkg = RestaurantData.getNextPageResults(restaurantPkg.getNextPageToken());
            newRestaurantPkg.addPageOfRestaurants(restaurantPkg.getPageOfRestaurants());

            model.addAttribute("featuredRestaurants", newRestaurantPkg);

        } else {
            model.addAttribute("No more results.", "No more results.");
            model.addAttribute("featuredRestaurants", restaurantPkg);

        }

        return "redirect:/home";
    }
}