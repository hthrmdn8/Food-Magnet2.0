package com.launchcode.foodmagnet.controllers;


import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.UpdateReview;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.models.service.CustomUserDetails;
import com.launchcode.foodmagnet.models.service.CustomUserDetailsService;
import com.launchcode.foodmagnet.models.service.UserService;
import com.launchcode.foodmagnet.repositories.RestaurantRepository;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import com.launchcode.foodmagnet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller

public class ReviewController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    private CustomUserDetails customUserDetails;

    @GetMapping("/reviews/add")
        public String displayReviewForm( Model model,@RequestParam("placeId") String placeId){

        model.addAttribute("review",new Review());
        Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
        model.addAttribute("restaurant", restaurant);
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("placeId", placeId);
        //return "reviews/add";
        return "restaurant";
        }

    @PostMapping("/reviews/add")
    public String addReview( @ModelAttribute("review") Review review, Principal principal, @RequestParam("placeId") String placeId,Model model) {

            if (principal != null) {
                String username = principal.getName();
                review.setCreatedByUser(username);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                User user = userRepository.findByUsername(userDetails.getUsername());
                review.setUser(user);
            }
                Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
                RestaurantEntity restaurantEntity =new RestaurantEntity(placeId, restaurant.getName());

                if(restaurantRepository.findByPlaceId(placeId) == null) {
                        restaurantRepository.save(restaurantEntity);
                }
                review.setRestaurantEntity(restaurantEntity);
                reviewRepository.save(review);
                model.addAttribute(" restaurantEntity", restaurantEntity);

                return "redirect:/restaurant?place_id=" + placeId;

    }


    @GetMapping("/reviews/{reviewId}/update")
    public String displayUpdateReviewForm(@PathVariable Integer reviewId/*,@RequestParam String placeId*/, Model model) {

        // Retrieve the review from the database using the reviewId
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            return "redirect:/error";
        }

        UpdateReview updateReview = new UpdateReview();
        updateReview.setUpdateReviewId(review.getReviewId());
        updateReview.setRating(review.getRatings());
        updateReview.setComments(review.getComments());
        model.addAttribute("updateReview", updateReview);
        return "reviews/update";
    }

    @PostMapping("/reviews/{reviewId}/update")
    public String updateReview(@PathVariable Integer reviewId, @ModelAttribute UpdateReview updateReview) {
        // Retrieve the existing review from the database using reviewId
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);

        if (existingReview == null) {
            return "redirect:/error";
        }

        existingReview.setRatings(updateReview.getRating());
        existingReview.setComments(updateReview.getComments());
        reviewRepository.save(existingReview);
        return "redirect:/profile" ;
    }

@PostMapping("/reviews/{reviewId}/delete")
public String deleteReview(@PathVariable Integer reviewId, Principal principal) {
    // Get the currently logged-in username
    String loggedInUsername = principal.getName();

    // Retrieve the review from the database based on the reviewId
    Optional<Review> optionalReview = reviewRepository.findById(reviewId);

    // Check if the review exists and if the logged-in user is the review creator
    if (optionalReview.isPresent()) {
        Review review = optionalReview.get();
        if (review.getCreatedByUser().equals(loggedInUsername)) {
            // User is authorized to delete the review, so delete it from the database
            reviewRepository.delete(review);
            return "redirect:/profile";
        } else {
            // User is not authorized to delete the review
            return "error";
        }
    } else {

        return "error";
    }
}
}
