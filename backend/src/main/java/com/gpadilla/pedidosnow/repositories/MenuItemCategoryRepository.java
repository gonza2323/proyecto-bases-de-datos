package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.MenuItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemCategoryRepository extends JpaRepository<MenuItemCategory, Long> {
    @Override
    public List<MenuItemCategory> findAll();
}
