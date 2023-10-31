package com.launchcode.foodmagnet.controllers.account;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import com.launchcode.foodmagnet.service.FavoriteService;
import com.launchcode.foodmagnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "account/favorites")
public class FavoritesController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public String renderAccountFavorites(Principal principal, Model model) {

        if (principal != null) {
            //adds currently logged-in user to the model
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);


            //adds Arraylist of the users favorite restaurants
            ArrayList<Restaurant> favoriteRestaurants = new ArrayList<>(user.getFavorites().size());
            for (Favorite favorite : user.getFavorites()) {
                favoriteRestaurants.add(RestaurantData.getRestaurantDetails(favorite.getPlaceId()));
            }
            model.addAttribute("favoriteRestaurants", favoriteRestaurants);
        }

        return "account/favorites";
    }

    @PostMapping(value = "delete")
    public String handleFavoriteRestaurantDeletion(@RequestParam(value = "place_id") String placeId, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        Favorite favoriteToDelete = favoriteService.findFavoriteByPlaceIdAndUser(placeId, user);

        if (favoriteToDelete != null) {
            favoriteService.deleteFavorite(favoriteToDelete);
        }

        return "redirect:/account/favorites";
    }
}
