package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetMenuItemDetailsDTO {
    private final Long id;
    private final String name;
    private final String description;
    private final Float price;
    private final boolean available;
    private final MenuItemCategoryDTO category;
    private final String imageUrl;
}
