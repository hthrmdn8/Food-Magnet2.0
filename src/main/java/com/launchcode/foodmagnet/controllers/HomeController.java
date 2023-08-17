package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.data.CarouselData;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping
    public String redirectToHome() {

        return "redirect:home";
    }

    @GetMapping("home")
    public String index(Model model, @RequestParam("userLocation") Optional<String> userLocation) {

        //loads photo array with restaurants from seattle
        //ArrayList<Restaurant> restaurantArrayList = RestaurantData.getRestaurantsNearby("Brooklyn");
        //Restaurant restaurant = RestaurantData.getRestaurantDetails(restaurantArrayList.get(2).getPlace_id());


//        for (Restaurant restauranta : restaurantArrayList) {
//            photos.add(restauranta.getAllPhotos().get(0));
//        }


        if (userLocation.isPresent()) {
            HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

            for (Restaurant restaurant : RestaurantData.getRestaurantsNearby(userLocation.get())) {
                fieldMap.put(restaurant.getName(), restaurant.getAllFields());

            }
            model.addAttribute("featuredRestaurants", fieldMap);

        }

        model.addAttribute("title", "Food Magnet");
        model.addAttribute("photos", CarouselData.getCarouselPhotos());

        return "home";
    }
}