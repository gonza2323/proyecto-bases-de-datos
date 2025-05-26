package com.gpadilla.pedidosnow.services;

import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.repositories.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@AllArgsConstructor
@Builder
@Service
public class UsersService {
    private final RestaurantRepository restaurantRepository;

    public void createUser(String userDTO) {
        Restaurant restaurant = new Restaurant();
        restaurantRepository.save(restaurant);
    }

    public boolean checkUserCompletedSignUp(String auth0Id) {
        Optional<Restaurant> restaurant = restaurantRepository.findByAuth0Id(auth0Id);
        return restaurant.isPresent();
    }
}
