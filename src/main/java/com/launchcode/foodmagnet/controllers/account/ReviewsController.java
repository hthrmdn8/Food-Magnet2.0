package com.launchcode.foodmagnet.controllers.account;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import com.launchcode.foodmagnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "account/reviews")
public class ReviewsController {

    @Autowired
    private UserService userService;

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping
    public String renderAccountReviewsPage(Principal principal, Model model, Review reviewObject) {

        if (principal != null) {
            //adds currently logged-in user to the model
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);

            //adds ArrayList of the all the restaurants the user has reviewed
            ArrayList<Restaurant> reviewedRestaurants = new ArrayList<>(user.getReviews().size());
            for (Review review : user.getReviews()) {
                reviewedRestaurants.add(RestaurantData.getRestaurantDetails(review.getRestaurantEntity().getPlaceId()));
            }
            model.addAttribute("reviewedRestaurants", reviewedRestaurants);

            model.addAttribute("reviewObj", reviewObject);
        }

        return "account/reviews";
    }


    @PostMapping(value = "edit")
    public String handleRestaurantReviewUpdate(@RequestParam(value = "reviewId") Integer reviewId,
                                               @ModelAttribute(value = "reviewObj") @Valid Review updateReview,
                                               Principal principal, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", "Rating must be an integer value.");
            return "redirect:/account/info";
        }

        User user = userService.findByUsername(principal.getName());
        Review review = reviewRepository.getReferenceById(reviewId);

        if (review != null) {
            review.setComments(updateReview.getComments());
            review.setRatings(updateReview.getRatings());

            reviewRepository.save(review);
        }


        return "redirect:/account/info";
    }

    @PostMapping(value = "delete")
    public String handleRestaurantReviewDeletion(@RequestParam(value = "reviewId") Integer reviewId) {

        Review review = reviewRepository.getReferenceById(reviewId);

        if (review != null) {
            reviewRepository.delete(review);
        }

        return "redirect:/account/info";
    }

}
