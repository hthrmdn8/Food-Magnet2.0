package com.launchcode.foodmagnet.repositories;


import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByRestaurantEntity(RestaurantEntity restaurantEntity);

    List<Review> findByCreatedByUser(String createdByUser);
}
