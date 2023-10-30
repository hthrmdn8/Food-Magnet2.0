package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.Review;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.dto.UserDto;
import com.launchcode.foodmagnet.service.CustomUserDetailsService;
import com.launchcode.foodmagnet.service.UserService;
import com.launchcode.foodmagnet.service.UserServiceImpl;
import com.launchcode.foodmagnet.repositories.FavoriteRepository;
import com.launchcode.foodmagnet.repositories.ReviewRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserService userService;

    private ReviewRepository reviewRepository;

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
        User user = userService.findByUsername(loggedInUsername);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loggedInUsername);
        model.addAttribute("userDetails" , userDetails);
        String location = userServiceImpl.findByUsername(loggedInUsername).getLocation();
        List<Review> userReviews = reviewRepository.findByCreatedByUser(loggedInUsername);
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        model.addAttribute("userReviews", userReviews);
        model.addAttribute("test", location);
        model.addAttribute("favorites", favorites);
        return "profile";
    }

    @PostMapping
    public String setLocation(Principal principal,Model model) {
        String loggedInUsername = principal.getName();
        UserDetails customUserDetailsService = userDetailsService.loadUserByUsername(loggedInUsername);
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
        userService.createUser(userDto);
        return "redirect:/register?success";
    }

}