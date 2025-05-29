package com.gpadilla.pedidosnow.dtos;

import com.gpadilla.pedidosnow.domain.RestaurantLocation;

public class LocationMapper {
    public static LocationDetailsDTO toLocationDetailsDTO(RestaurantLocation entity) {
        return LocationDetailsDTO.builder()
                .id(entity.getId())
                .name(entity.getLocationName())
                .isOpen(entity.getIsOpen())
                .logoUrl(entity.getLogoImgUrl())
                .build();
    }
}
