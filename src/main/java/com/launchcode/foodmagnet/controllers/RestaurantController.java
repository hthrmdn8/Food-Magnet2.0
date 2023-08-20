package com.launchcode.foodmagnet.controllers;


import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.models.service.FavoriteService;
import com.launchcode.foodmagnet.models.service.UserService;
import com.launchcode.foodmagnet.repositories.FavoriteRepository;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Optional;

@Controller
public class RestaurantController {
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ReviewRepository reviewRepository;
    private RestaurantEntity restaurantEntity;
    @GetMapping("/restaurant")
    public String showRestaurantDetails(@RequestParam("place_id") String placeId, Model model) {

        Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
        model.addAttribute("restaurant", restaurant);
      
        RestaurantEntity restaurantEntity =  restaurantRepository.findByPlaceId(placeId);
        List<Review> reviews = reviewRepository.findByRestaurantEntity(restaurantEntity);
        model.addAttribute("reviews", reviews);
        model.addAttribute("placeId", placeId);
      

        model.addAttribute("title", restaurant.getName());
      
        return "restaurant"; // Create a new Thymeleaf template named "restaurant_details.html"

    }

    @GetMapping("/favorites/add")
    public String showAddToFavoritesPage( Principal principal,@RequestParam String placeId,Model model) {

        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            List<Favorite> favorites = favoriteService.getFavoritesByUser(user);
            model.addAttribute("favorites", favorites);
        }
        model.addAttribute("placeId", placeId);
        return "favorites";
//        return "profile";
    }

    @PostMapping("/favorites/add")
    public String addToFavorites(@RequestParam String placeId, Principal principal, Model model) {

        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
            RestaurantEntity newRestaurantEntity = new RestaurantEntity(placeId, restaurant.getName());

            if(restaurantRepository.findByPlaceId(placeId) == null) {
                restaurantRepository.save(newRestaurantEntity);
            }

            RestaurantEntity restaurantEntity = restaurantRepository.findByPlaceId(placeId);
            favoriteService.addToFavorites(restaurantEntity, user);
            List<Favorite> favorites = favoriteService.findByUser(user);
            model.addAttribute("favorites", favorites);

        }

        return "redirect:/profile";

    }

}

