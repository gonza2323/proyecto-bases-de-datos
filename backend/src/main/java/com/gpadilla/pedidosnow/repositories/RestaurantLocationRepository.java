package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Long> {
}
