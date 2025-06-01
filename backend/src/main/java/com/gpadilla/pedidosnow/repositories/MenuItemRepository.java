package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findById(Long id);
}
