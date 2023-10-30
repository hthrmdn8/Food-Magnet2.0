package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.dto.UserDto;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.service.FavoriteService;
import com.launchcode.foodmagnet.service.UserService;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
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

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping
    public String renderAccountPage(Model model, Principal principal, UserDto userDto, Review reviewObject) {

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

            model.addAttribute("reviewObj", reviewObject);


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

    @PostMapping(value = "/favorite/delete")
    public String handleFavoriteRestaurantDeletion(@RequestParam(value = "place_id") String placeId, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        Favorite favoriteToDelete = favoriteService.findFavoriteByPlaceIdAndUser(placeId, user);

        if (favoriteToDelete != null) {
            favoriteService.deleteFavorite(favoriteToDelete);
        }

        return "redirect:/account";
    }

    @PostMapping(value = "/review/edit")
    public String handleRestaurantReviewUpdate(@RequestParam(value = "reviewId") Integer reviewId,
                                               @ModelAttribute(value = "reviewObj") @Valid Review updateReview,
                                               Principal principal, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", "Rating must be an integer value.");
            return "redirect:/account";
        }

        User user = userService.findByUsername(principal.getName());
        Review review = reviewRepository.getReferenceById(reviewId);

        if (review != null) {
            review.setComments(updateReview.getComments());
            review.setRatings(updateReview.getRatings());

            reviewRepository.save(review);
        }


        return "redirect:/account";
    }

    @PostMapping(value = "/review/delete")
    public String handleRestaurantReviewDeletion(@RequestParam(value = "reviewId") Integer reviewId) {

        Review review = reviewRepository.getReferenceById(reviewId);

        if (review != null) {
            reviewRepository.delete(review);
        }

        return "redirect:/account";
    }

}
