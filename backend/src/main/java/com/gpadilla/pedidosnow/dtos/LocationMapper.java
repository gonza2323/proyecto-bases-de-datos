package com.gpadilla.pedidosnow.dtos;

import com.gpadilla.pedidosnow.domain.RestaurantLocation;

public class LocationMapper {
    public static LocationSummaryDTO toLocationDetailsDTO(RestaurantLocation entity) {
        return LocationSummaryDTO.builder()
                .id(entity.getId())
                .name(entity.getLocationName())
                .isOpen(entity.getIsOpen())
                .logoUrl(entity.getLogoImgUrl())
                .rating(null)
                .build();
    }
}
