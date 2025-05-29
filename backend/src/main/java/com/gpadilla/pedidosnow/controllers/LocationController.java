package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @GetMapping
    public List<RestaurantLocation> getAllRestaurants() {
        return Collections.emptyList();
    }

}
