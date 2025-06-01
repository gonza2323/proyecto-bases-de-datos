package com.gpadilla.pedidosnow.dtos;


import com.gpadilla.pedidosnow.domain.MenuItem;

public class MenuItemMapper {
    public static MenuItemDetailsDTO toMenuItemDetailsDTO(MenuItem entity) {
        return MenuItemDetailsDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory().getName())
                .price(entity.getPrice())
                .available(entity.getAvailable())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
