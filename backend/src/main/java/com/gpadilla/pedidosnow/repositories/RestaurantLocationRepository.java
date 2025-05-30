package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Long> {
    @Override
    List<RestaurantLocation> findAll();

    @Override
    Optional<RestaurantLocation> findById(Long aLong);
}
