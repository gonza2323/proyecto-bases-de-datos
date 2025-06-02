package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    void deleteByLocationId(Long locationId);
}
