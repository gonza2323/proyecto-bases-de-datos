package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationSummaryDTO {
    private final long id;
    private final String name;
    private final String logoUrl;
    private final boolean isOpen;
    private final int rating;
}
