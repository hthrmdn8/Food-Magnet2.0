package com.launchcode.foodmagnet.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.CarouselData;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.data.jsonData.ApiRequests;
import com.launchcode.foodmagnet.models.data.jsonData.JsonMapper;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.models.service.UserService;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());

            ArrayList<Restaurant> restaurants = RestaurantData.getRestaurantsNearby(user.getLocation());
            model.addAttribute("featuredRestaurants", restaurants);

        }

        model.addAttribute("title", "Food Magnet");
        model.addAttribute("photos", CarouselData.getCarouselPhotos());

        return "home";
    }
}