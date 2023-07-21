package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public String displaySearchPage(Model model) throws IOException, InterruptedException {

        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

        for (Restaurant restaurant : RestaurantData.getRestaurants()) {
            fieldMap.put(restaurant.getName(), restaurant.getAllFields());
        }

        model.addAttribute("sampleImage", RestaurantData.getPhoto());
        model.addAttribute("fieldMap", fieldMap);
        model.addAttribute("restaurants", RestaurantData.getRestaurants());

        return "search";
    }

}
