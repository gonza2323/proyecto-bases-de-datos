package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.dtos.LocationSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByAuth0Id(String auth0Id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.locations WHERE r.auth0Id = :auth0Id")
    Optional<Restaurant> findByAuth0IdWithLocations(@Param("auth0Id") String auth0Id);

    @Query("SELECT new com.gpadilla.pedidosnow.dtos.LocationSummaryDTO(l.id, l.locationName, l.logoImgUrl, l.isOpen, AVG(r.rating)) " +
            "FROM Restaurant rest JOIN rest.locations l LEFT JOIN l.reviews r " +
            "WHERE rest.auth0Id = :auth0Id " +
            "GROUP BY l.id, l.locationName, l.logoImgUrl, l.isOpen")
    List<LocationSummaryDTO> findLocationRatingsByAuth0Id(@Param("auth0Id") String auth0Id);
}
