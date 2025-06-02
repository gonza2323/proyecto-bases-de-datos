package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateMenuItemRequestDTO {
    private final String name;
    private final String description;
    private final Float price;
    private final Long categoryId;
    private final String imageUrl;
}