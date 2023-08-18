package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Controller
@RequestMapping("restaurant")
public class RestaurantController {

    @GetMapping("")
    public String index(Model model, @RequestParam String place_id) throws URISyntaxException {

        Restaurant restaurant = RestaurantData.getRestaurantDetails(place_id);

        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("www.google.com")
                .setPath("maps/embed/v1/place")
                .addParameter("key", "AIzaSyA27YdMxL2D735pVL7JTgpb3dxkJ6RgDWU")
                .addParameter("q", restaurant.getName())
                .build();

        model.addAttribute("title", restaurant.getName());
        model.addAttribute("src", build);
        return "restaurant";
    }
}