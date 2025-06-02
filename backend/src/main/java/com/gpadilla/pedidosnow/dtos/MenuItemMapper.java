package com.gpadilla.pedidosnow.dtos;


import com.gpadilla.pedidosnow.domain.MenuItem;

public class MenuItemMapper {
    public static GetMenuItemDetailsDTO toMenuItemDetailsDTO(MenuItem entity) {

        MenuItemCategoryDTO categoryDTO = new MenuItemCategoryDTO(
                entity.getCategory().getId(),
                entity.getCategory().getName());

        return GetMenuItemDetailsDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(categoryDTO)
                .price(entity.getPrice())
                .available(entity.getAvailable())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
