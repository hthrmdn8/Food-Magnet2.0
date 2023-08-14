package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.dto.UserDto;
import com.launchcode.foodmagnet.models.service.UserService;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;
    public UserController(UserService userService) {

        this.userService = userService;
    }
    @Autowired
    public UserController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    @GetMapping("/profile")
    public String home(Model model, Principal principal) {
        String loggedInUsername = principal.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(loggedInUsername);
        model.addAttribute("userDetails" , userDetails);

        // Retrieve the reviews associated with the logged-in user
        List<Review> userReviews = reviewRepository.findByCreatedByUser(loggedInUsername);

//        // Add the user's reviews to the model
       model.addAttribute("userReviews", userReviews);
        return "profile";
    }

    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }


    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {

        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = userService.findByUsername(userDto.getUsername());
        if (user != null) {
            model.addAttribute("userexist", user);
            return "register";

        }
        userService.save(userDto);
        return "redirect:/register?success";
    }

}