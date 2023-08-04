package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.data.jsonData.ApiRequests;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public String displaySearchPage(Model model) throws IOException, InterruptedException, URISyntaxException {

        //fieldMap used to correlate the name of a restaurant with all of that restaurants fields.
        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

//        for (Restaurant restaurant : RestaurantData.getRestaurantsNearby("Los Angeles")) {
//            fieldMap.put(restaurant.getName(), restaurant.getAllFields());
//        }
//
//        Restaurant restaurant = RestaurantData.getRestaurantDetails("ChIJ-SF4shmz2IcROVewwNljeZQ");
//
        Restaurant laRestaurant = RestaurantData.getRestaurantsNearby("Los Angeles").get(2);
        Restaurant laRestaurantDetails = RestaurantData.getRestaurantDetails(laRestaurant.getPlace_id());

        //model.addAttribute("sampleImage", RestaurantData.getPhoto(restaurant.getPhotoSrcList().get(0)));
        //model.addAttribute("fieldMap", fieldMap);
        //model.addAttribute("restaurants", );
        model.addAttribute("restaurantDetails", laRestaurantDetails.getAllPhotos());
        //model.addAttribute("geocode", RestaurantData.getCoordinates("St. louis").toString());


        //ApiRequests.placeGeocodingRequest("St. Louis").body()
        return "search";
    }

}
