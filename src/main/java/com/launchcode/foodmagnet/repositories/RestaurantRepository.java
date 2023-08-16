package com.launchcode.foodmagnet.repositories;

import com.launchcode.foodmagnet.models.RestaurantEntity;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,String> {


    RestaurantEntity findByName(String name);

    RestaurantEntity findByPlaceId(String place_id);

    // boolean findByPlaceId();
}
