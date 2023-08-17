package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("restaurant")
public class RestaurantController {

    @GetMapping("")
    public String index(Model model, @RequestParam String place_id){

        Restaurant restaurant = RestaurantData.getRestaurantDetails(place_id);

        model.addAttribute("title", restaurant.getName());
        return "restaurant";
    }
}