package com.launchcode.foodmagnet.repositories;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, String> {

    ArrayList<Favorite> findByUser(User user);

    ArrayList<Favorite> findByPlaceId(String id);

    ArrayList<Favorite> findByUserId(Long id);

    Favorite findByPlaceIdAndUser(String placeId, User user);
}