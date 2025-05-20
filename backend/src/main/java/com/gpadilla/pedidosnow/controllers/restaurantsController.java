package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import com.gpadilla.pedidosnow.repositories.RestaurantLocationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class restaurantsController {

    private final RestaurantLocationRepository restaurantsRepository;

    public restaurantsController(RestaurantLocationRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    @GetMapping
    public List<RestaurantLocation> getAllRestaurants() {
        return restaurantsRepository.findAll();
    }

    @PostMapping
    public void addRestaurant(@RequestBody RestaurantLocation restaurant) {
        restaurantsRepository.save(restaurant);
    }
}
