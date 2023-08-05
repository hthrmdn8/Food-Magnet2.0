package com.launchcode.foodmagnet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("city")
public class MapController {

    @GetMapping
    public String renderCityPage() {

        return "city";
    }
}
