package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LocationSummaryWithMenuDTO {
    private final long id;
    private final String name;
    private final String logoUrl;
    private final boolean isOpen;
    private final Float rating;
    private final List<GetMenuItemDetailsDTO> menuItems;
    private final List<MenuItemCategoryDTO> categories;
}
