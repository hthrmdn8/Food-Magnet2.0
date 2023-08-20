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
//@RequestMapping("restaurant")
//public class RestaurantController {
//
//    @GetMapping("")
//    public String index(Model model, @RequestParam String place_id){
//
//        Restaurant restaurant = RestaurantData.getRestaurantDetails(place_id);
//
//        model.addAttribute("title", restaurant.getName());
//        return "restaurant";
//    }

public class RestaurantController {
@GetMapping("/restaurant")
public String showRestaurantDetails(@RequestParam("place_id") String place_id, Model model) {

        // Here, you can fetch the restaurant details using the placeId
        // and pass the details to the view template using the Model

        Restaurant restaurant = RestaurantData.getRestaurantDetails(place_id);
        model.addAttribute("restaurant", restaurant);

//        restaurantEntity.setName(restaurant.getName());
//        restaurantRepository.save(restaurantEntity);
        //RestaurantEntity restaurantEntity =  restaurantRepository.findByName(restaurant.getName());
//if(restaurantEntity!=null){

        //restaurantRepository.findByName(restaurant.getName())
//model.addAttribute("restaurantName",restaurantEntity.getName());
        //List<Review> reviews = reviewRepository.findByRestaurantEntity(restaurantEntity);
        //model.addAttribute("reviews", reviews);}

        model.addAttribute("place_id", place_id);
        return "restaurant"; // Create a new Thymeleaf template named "restaurant_details.html"

        }

}