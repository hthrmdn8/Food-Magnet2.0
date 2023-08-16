package com.launchcode.foodmagnet.models.dto;


import jakarta.validation.constraints.Email;

public class UserDto {

    @Email
    private String username;
    private String password;
    private String fullname;

    private String location;


    public UserDto() {

    }


    public UserDto(String username, String password, String fullname, String location) {

        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFullname() {
        return fullname;
    }


    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


}