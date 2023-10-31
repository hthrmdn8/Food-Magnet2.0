package com.launchcode.foodmagnet.service;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public void addToFavorites(RestaurantEntity restaurant, User user) {
        Favorite favorite = new Favorite();
        favorite.setFavouriteRestaurant(restaurant.getName());
        favorite.setPlaceId(restaurant.getPlaceId());
        favorite.setUser(user);
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public Favorite findFavoriteByPlaceIdAndUser(String placeId, User user) {
        return favoriteRepository.findByPlaceIdAndUser(placeId, user);
    }

    public ArrayList<Favorite> getFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user);
    }

    public ArrayList<Favorite> getFavoritesByPlaceId(String placeId) {

        return favoriteRepository.findByPlaceId(placeId);
    }

    public ArrayList<Favorite> findByUserId(Long id) {

        return favoriteRepository.findByUserId(id);
    }

    public ArrayList<Favorite> findByUser(User user) {
        return favoriteRepository.findByUser(user);
    }
}