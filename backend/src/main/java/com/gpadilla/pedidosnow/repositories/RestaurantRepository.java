package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByAuth0Id(String auth0Id);
}
