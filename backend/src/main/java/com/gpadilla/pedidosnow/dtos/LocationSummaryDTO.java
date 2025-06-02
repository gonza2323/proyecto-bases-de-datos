package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationSummaryDTO {
    private final Long id;
    private final String name;
    private final String logoUrl;
    private final Boolean isOpen;
    private final Double rating;
}
