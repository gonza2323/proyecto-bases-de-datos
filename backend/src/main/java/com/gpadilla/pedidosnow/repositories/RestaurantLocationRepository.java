package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import com.gpadilla.pedidosnow.dtos.LocationReportDTO;
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

    @Query("""
        SELECT new com.gpadilla.pedidosnow.dtos.LocationReportDTO(
            l.locationName,
            l.isOpen,
            AVG(rev.rating),
            COUNT(DISTINCT CASE WHEN o.state = 'DELIVERED' THEN o.id ELSE null END),
            AVG(CASE WHEN o.state = 'DELIVERED' THEN o.subtotal ELSE null END),
            SUM(CASE WHEN o.state = 'DELIVERED' THEN o.subtotal ELSE 0 END)
        )
        FROM Restaurant r
        JOIN r.locations l
        LEFT JOIN l.orders o
        LEFT JOIN l.reviews rev
        WHERE r.auth0Id = :auth0Id
        GROUP BY l.id, l.locationName, l.isOpen""")
    List<LocationReportDTO> getLocationsReport(@Param("auth0Id") String auth0Id);
}
