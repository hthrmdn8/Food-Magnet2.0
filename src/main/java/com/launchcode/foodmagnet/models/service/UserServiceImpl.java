package com.launchcode.foodmagnet.models.service;


import com.launchcode.foodmagnet.models.User;
import com.launchcode.foodmagnet.models.dto.UserDto;
import com.launchcode.foodmagnet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;



    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getFullname());
        return userRepository.save(user);
    }



}