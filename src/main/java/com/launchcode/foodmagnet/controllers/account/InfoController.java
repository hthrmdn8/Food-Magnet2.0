package com.launchcode.foodmagnet.controllers.account;

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
public class InfoController {

    @Autowired
    UserService userService;

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping
    public String renderAccountPage(Model model, Principal principal, UserDto userDto) {

        if (principal != null) {
            //adds currently logged-in user to the model
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);

            userDto.setFullname(user.getFullname());
            userDto.setLocation(user.getLocation());
            userDto.setUsername(user.getUsername());

            model.addAttribute("userDto", userDto);


        }

        return "account/info";
    }

    @PostMapping
    public String handleAccountInfoUpdate(@ModelAttribute(value = "userDto") @Valid UserDto userDto, Model model, Principal principal, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "account/info";
        }

        User user = userService.findByUsername(principal.getName());

        user.setFullname(userDto.getFullname());
        user.setLocation(userDto.getLocation());
        user.setUsername(userDto.getUsername());

        userService.save(user);

        return "redirect:/account/info";

    }

}
