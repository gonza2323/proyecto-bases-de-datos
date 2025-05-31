package com.gpadilla.pedidosnow.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateLocationRequestDTO {
    private final String name;
    private final CreateAddressDTO address;
}
