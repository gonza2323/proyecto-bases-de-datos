package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import com.gpadilla.pedidosnow.dtos.LocationSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Long> {
    @Override
    List<RestaurantLocation> findAll();

    @Override
    Optional<RestaurantLocation> findById(Long id);

    @Query("SELECT r FROM RestaurantLocation r LEFT JOIN FETCH r.menuItems WHERE r.id = :id")
    Optional<RestaurantLocation> findByIdWithMenuItems(@Param("id") Long id);

    @Query("SELECT new com.gpadilla.pedidosnow.dtos.LocationSummaryDTO(l.id, l.locationName, l.logoImgUrl, l.isOpen, AVG(r.rating)) " +
            "FROM RestaurantLocation l LEFT JOIN l.reviews r " +
            "GROUP BY l.id, l.locationName, l.logoImgUrl, l.isOpen")
    List<LocationSummaryDTO> findAllLocationsWithRatings();

    @Query("SELECT AVG(r.rating) FROM RestaurantLocation l LEFT JOIN l.reviews r WHERE l.id = :id")
    Optional<Double> findAverageRatingById(@Param("id") Long id);
}
