package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.dto.UserDto;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.models.service.FavoriteService;
import com.launchcode.foodmagnet.models.service.UserService;
import com.launchcode.foodmagnet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "account")
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    FavoriteService favoriteService;

    @GetMapping
    public String renderAccountPage(Model model, Principal principal, UserDto userDto) {

        if (principal != null) {
            //adds currently logged-in user to the model
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);


            //adds Arraylist of the users favorite restaurants
            ArrayList<Restaurant> favoriteRestaurants = new ArrayList<>(user.getFavorites().size());
            for (Favorite favorite : user.getFavorites()) {
                favoriteRestaurants.add(RestaurantData.getRestaurantDetails(favorite.getPlaceId()));
            }
            model.addAttribute("favoriteRestaurants", favoriteRestaurants);

            //adds ArrayList of the all the restaurants the user has reviewed
            ArrayList<Restaurant> reviewedRestaurants = new ArrayList<>(user.getReviews().size());
            for (Review review : user.getReviews()) {
                reviewedRestaurants.add(RestaurantData.getRestaurantDetails(review.getRestaurantEntity().getPlaceId()));
            }
            model.addAttribute("reviewedRestaurants", reviewedRestaurants);

            userDto.setFullname(user.getFullname());
            userDto.setLocation(user.getLocation());
            userDto.setUsername(user.getUsername());

            model.addAttribute("userDto", userDto);

        }

        return "account";
    }

    @PostMapping
    public String handleAccountInfoUpdate(@ModelAttribute(value = "userDto") @Valid UserDto userDto, Model model, Principal principal, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "account";
        }

        User user = userService.findByUsername(principal.getName());

        user.setFullname(userDto.getFullname());
        user.setLocation(userDto.getLocation());
        user.setUsername(userDto.getUsername());



        userService.save(user);

        return "redirect:/account";

    }

    @PostMapping(value = "delete")
    public String handleFavoriteRestaurantDeletion(@RequestParam(value = "place_id") String placeId, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        Favorite favoriteToDelete = favoriteService.findFavoriteByPlaceIdAndUser(placeId, user);

        if (favoriteToDelete != null) {
            favoriteService.deleteFavorite(favoriteToDelete);
        }

        return "redirect:/account";
    }

}
