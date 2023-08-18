package com.launchcode.foodmagnet.repositories;

import com.launchcode.foodmagnet.models.Favorite;
import com.launchcode.foodmagnet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    List<Favorite> findByUser(User user);
    List<Favorite> findByPlaceId(String id);

    List<Favorite> findByUserId(Long id);
}