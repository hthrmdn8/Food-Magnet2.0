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
    public String displaySearchPage(Model model) throws IOException, InterruptedException, URISyntaxException {

        //fieldMap used to correlate the name of a restaurant with all of that restaurants fields.
        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

        for (Restaurant restaurant : RestaurantData.getRestaurantList()) {
            fieldMap.put(restaurant.getName(), restaurant.getAllFields());
        }

        Restaurant restaurant = RestaurantData.getRestaurant();

        //model.addAttribute("sampleImage", RestaurantData.getPhoto());
        model.addAttribute("fieldMap", fieldMap);
        model.addAttribute("restaurants", RestaurantData.getRestaurantList());
        model.addAttribute("restaurantDetails", restaurant.getPhotoSrcList());

        return "search";
    }

}
