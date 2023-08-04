package com.launchcode.foodmagnet.service;

import com.launchcode.foodmagnet.dto.UserDto;
import com.launchcode.foodmagnet.models.User;

public interface UserService {
    User findByUsername(String username);
    User save (UserDto userDto);

}