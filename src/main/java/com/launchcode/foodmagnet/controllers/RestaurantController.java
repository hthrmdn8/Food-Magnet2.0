package com.launchcode.foodmagnet.controllers;


import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.service.FavoriteService;
import com.launchcode.foodmagnet.service.UserService;
import com.launchcode.foodmagnet.repositories.FavoriteRepository;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public String showRestaurantDetails(@RequestParam("placeId") String placeId, Model model, Principal principal) throws URISyntaxException {

        Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
        model.addAttribute("restaurant", restaurant);

        RestaurantEntity restaurantEntity =  restaurantRepository.findByPlaceId(placeId);
        List<Review> reviews = reviewRepository.findByRestaurantEntity(restaurantEntity);

        double averageRating = calculateAverageRating(reviews);
        model.addAttribute("averageRating", averageRating);

        if (!reviews.isEmpty()) {
            model.addAttribute("reviews", reviews);
        }

        Review review = new Review();
        model.addAttribute("review", review);
        model.addAttribute("placeId", placeId);


        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("www.google.com")
                .setPath("maps/embed/v1/place")
                .addParameter("key", "AIzaSyA27YdMxL2D735pVL7JTgpb3dxkJ6RgDWU")
                .addParameter("q", restaurant.getName())
                .build();

        model.addAttribute("title", restaurant.getName());
        model.addAttribute("src", build);

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);
        }


        return "restaurant";

    }

    private double calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRatings();
        }
        return sum / reviews.size();
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

    }

    @PostMapping("/favorites/add")
    public String addToFavorites(@RequestParam String placeId, Principal principal, Model model) {

        if (principal != null) {

            //adds restaurant to the database if it's not already present
            if(restaurantRepository.findByPlaceId(placeId) == null) {
                Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
                RestaurantEntity RestaurantEntity = new RestaurantEntity(placeId, restaurant.getName());
                restaurantRepository.save(RestaurantEntity);
            }

            //finds user and restaurant related to the favorite
            String username = principal.getName();
            User user = userService.findByUsername(username);
            RestaurantEntity restaurantEntity = restaurantRepository.findByPlaceId(placeId);

            //creates and saves a new favorite entity
            favoriteService.addToFavorites(restaurantEntity, user);
            ArrayList<Favorite> favorites = favoriteService.findByUser(user);
            model.addAttribute("favorites", favorites);

        }

        return "redirect:/account";

    }

}

