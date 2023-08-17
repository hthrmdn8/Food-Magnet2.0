package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;
@Autowired
private ReviewRepository reviewRepository;
    private RestaurantEntity restaurantEntity;
    @GetMapping("/restaurant")
    public String showRestaurantDetails(@RequestParam("placeId") String placeId, Model model) {

        // Here, you can fetch the restaurant details using the placeId
        // and pass the details to the view template using the Model

        Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
        model.addAttribute("restaurant", restaurant);

//        restaurantEntity.setName(restaurant.getName());
//        restaurantRepository.save(restaurantEntity);
        // RestaurantEntity restaurantEntity=  restaurantRepository.findByName(restaurant.getName());
//if(restaurantEntity!=null){

        RestaurantEntity restaurantEntity=  restaurantRepository.findByPlaceId(placeId);
//model.addAttribute("restaurantName",restaurantEntity.getName());
        List<Review> reviews = reviewRepository.findByRestaurantEntity(restaurantEntity);
       model.addAttribute("reviews", reviews);

        model.addAttribute("placeId", placeId);
        return "restaurant"; // Create a new Thymeleaf template named "restaurant_details.html"

    }}