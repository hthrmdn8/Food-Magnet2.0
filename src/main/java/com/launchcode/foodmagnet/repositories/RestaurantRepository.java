package com.launchcode.foodmagnet.repositories;

import com.launchcode.foodmagnet.models.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Integer> {


    RestaurantEntity findByName(String name);
}
