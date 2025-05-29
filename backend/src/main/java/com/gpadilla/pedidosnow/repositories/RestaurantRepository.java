package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByAuth0Id(String auth0Id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.locations WHERE r.auth0Id = :auth0Id")
    Optional<Restaurant> findByAuth0IdWithLocations(@Param("auth0Id") String auth0Id);
}
