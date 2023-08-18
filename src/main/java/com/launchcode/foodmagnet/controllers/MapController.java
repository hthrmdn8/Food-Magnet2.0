package com.launchcode.foodmagnet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("city")
public class MapController {

    @GetMapping
    public String renderCityPage(Model model) {

        model.addAttribute("title", "Cities");
        return "city";
    }
}
