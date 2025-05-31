package com.gpadilla.pedidosnow.services;

import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.dtos.LocationDetailsDTO;
import com.gpadilla.pedidosnow.dtos.LocationMapper;
import com.gpadilla.pedidosnow.dtos.UserCreationDetailsDTO;
import com.gpadilla.pedidosnow.dtos.UserDetailsDTO;
import com.gpadilla.pedidosnow.repositories.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Service
public class UserService {
    private final RestaurantRepository restaurantRepository;

    public void createUser(UserCreationDetailsDTO userCreationDetailsDTO) {
        Restaurant restaurant = Restaurant.builder()
                .email(userCreationDetailsDTO.getEmail())
                .name(userCreationDetailsDTO.getName())
                .legalAddress(userCreationDetailsDTO.getLegalAddress())
                .auth0Id(userCreationDetailsDTO.getAuth0Id())
                .build();
        restaurantRepository.save(restaurant);
    }

    public UserDetailsDTO getUserDetailsByAuth0Id(String auth0Id) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByAuth0Id(auth0Id);

        if (restaurantOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        Restaurant restaurant = restaurantOpt.get();
        return UserDetailsDTO.builder()
                .name(restaurant.getName())
                .email(restaurant.getEmail())
                .logoImgUrl(restaurant.getLogoImgUrl())
                .build();
    }

    public boolean checkUserCompletedSignUp(String auth0Id) {
        Optional<Restaurant> restaurant = getRestaurantByAuth0Id(auth0Id);
        return restaurant.isPresent();
    }

    public List<LocationDetailsDTO> getUserLocations(String auth0Id) {
        Optional<Restaurant> restaurant = restaurantRepository.findByAuth0IdWithLocations(auth0Id);

        if (restaurant.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");

        return restaurant.get().getLocations().stream()
                .map(LocationMapper::toLocationDetailsDTO)
                .collect(Collectors.toList());
    }

    public Optional<Restaurant> getRestaurantByAuth0Id(String auth0Id) {
        return restaurantRepository.findByAuth0Id(auth0Id);
    }
}
