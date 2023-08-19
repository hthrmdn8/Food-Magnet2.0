package com.launchcode.foodmagnet.controllers;


import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.data.CarouselData;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.models.service.CustomUserDetailsService;
import com.launchcode.foodmagnet.models.service.UserServiceImpl;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {

@Autowired
private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserServiceImpl userService;
    @GetMapping
    public String redirectToHome() {

        return "redirect:home";
    }

    @GetMapping("home")
    public String index( Model model, @RequestParam("userLocation") Optional<String> userLocation) {

        if (userLocation.isPresent()) {
            HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

            for (Restaurant restaurant : RestaurantData.getRestaurantsNearby(userLocation.get())) {
                fieldMap.put(restaurant.getName(), restaurant.getAllFields());

            }
            model.addAttribute("featuredRestaurants", fieldMap);

        }

        model.addAttribute("title", "Food Magnet");
        model.addAttribute("photos", CarouselData.getCarouselPhotos());

        return "home";
    }}