package com.launchcode.foodmagnet.models.service;


import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.dto.UserDto;

public interface UserService {
    User findByUsername(String username);

    User save (UserDto userDto);

}