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
//    @GetMapping("/reviews/add")
//    public String displayReviewForm(@RequestParam(name = "restaurantId") Integer restaurantId, @RequestParam("placeId") String placeId,Model model) {
//        Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
//        restaurantRepository.save(restaurant.getName());
//
//        model.addAttribute("restaurantId", restaurantId);
//        model.addAttribute(new Review());
//
//        return "reviews/add";
//    }
//this is working for add review
//    @GetMapping("/reviews/add")
//    public String displayReviewForm( Model model){
//
//        model.addAttribute("review",new Review());
//        model.addAttribute("restaurants",restaurantRepository.findAll());
//
//        LocalDate currentDate = LocalDate.now();
//        model.addAttribute("currentDate", currentDate);
//        return "reviews/add";
//    }

    @GetMapping("/reviews/add")
        public String displayReviewForm( Model model,@RequestParam("placeId") String placeId){

        model.addAttribute("review",new Review());
        Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);
        model.addAttribute("restaurant", restaurant);
        //model.addAttribute("name",restaurant.getName());
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("test", RestaurantData.getRestaurantDetails(placeId).getName());
        model.addAttribute("placeId", placeId);

        return "reviews/add";

        }

    @PostMapping("/reviews/add")
    public String addReview(Review review, Principal principal, @RequestParam("placeId") String placeId,Model model) {

            if (principal != null) {

                String username = principal.getName();
                review.setCreatedByUser(username);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                User user = userRepository.findByUsername(userDetails.getUsername());
                review.setUser(user);
            }
        Restaurant restaurant = RestaurantData.getRestaurantDetails(placeId);


                    RestaurantEntity restaurantEntity =new RestaurantEntity(placeId, restaurant.getName());
//                    restaurantEntity.setName( restaurant.getName());
                    //restaurantEntity.setPlaceId (restaurant.getPlace_id());
       // restaurantEntity.setPlaceId ("ChIJcQ0R1rBqkFQR8kvZfk8COTE");
                if(restaurantRepository.findByPlaceId(placeId) == null) {
                        restaurantRepository.save(restaurantEntity);
                }
                    review.setRestaurantEntity(restaurantEntity);
                    reviewRepository.save(review);
        model.addAttribute(" restaurantEntity", restaurantEntity);

        return "redirect:/restaurant?placeId=" + placeId;

    }


//    @PostMapping("/reviews/add")
//    public String addReview(Review review, Principal principal, Restaurant restaurant) {
//
//        if (principal != null) {
//
//            String username = principal.getName();
//            review.setCreatedByUser(username);
//            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//            User user = userRepository.findByUsername(userDetails.getUsername());
//            review.setUser(user);
//        }
//
//
//
//            RestaurantEntity restaurantEntity =new RestaurantEntity();
//            restaurantEntity.setName( restaurant.getName());
//            restaurantEntity.setPlaceId (restaurant.getPlace_id());
//            if(restaurantRepository.findByPlaceId(restaurant.getPlace_id()) == null) {
//                restaurantRepository.save(restaurantEntity);
//            }
//            review.setRestaurantEntity(restaurantEntity);
//            reviewRepository.save(review);
//
//
//        return "redirect:/profile";
//
//    }
//

    @GetMapping("/reviews/{reviewId}/update")
    public String displayUpdateReviewForm(@PathVariable Integer reviewId, Model model) {
        // Retrieve the review from the database using the reviewId
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            // Handle the case where the review with the given reviewId doesn't exist
            return "redirect:/error"; // or display an error page
        }

        // Create an UpdateReview DTO and populate it with the current review data
        UpdateReview updateReview = new UpdateReview();
        updateReview.setUpdateReviewId(review.getReviewId());
        updateReview.setRating(review.getRatings());
        updateReview.setComments(review.getComments());

        model.addAttribute("updateReview", updateReview);
        model.addAttribute("restaurants",restaurantRepository.findAll());

        return "reviews/update"; // Adjust the template path based on your project structure
    }

    @PostMapping("/reviews/{reviewId}/update")
    public String updateReview(@PathVariable Integer reviewId, @ModelAttribute UpdateReview updateReview) {
        // Retrieve the existing review from the database using reviewId
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);

        if (existingReview == null) {
            // Handle the case where the review with the given reviewId doesn't exist
            return "redirect:/error"; // or display an error page
        }

        // Update the review properties with the data from the UpdateReview entity
        existingReview.setRatings(updateReview.getRating());
        existingReview.setComments(updateReview.getComments());

        // Save the updated review back to the database
        reviewRepository.save(existingReview);

        // Redirect to the view page for the updated review
        return "redirect:/profile" ; // or adjust the URL based on your project structure
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
            // Redirect to a success page or another appropriate page after deletion
            return "redirect:/profile"; // or adjust the URL based on your project structure
        } else {
            // User is not authorized to delete the review
            // You can handle this case with an error message or redirect to an error page
            return "error"; // or return a view that shows an error message
        }
    } else {
        // Review not found in the database
        // You can handle this case with an error message or redirect to an error page
        return "error"; // or return a view that shows an error message
    }
}
}
