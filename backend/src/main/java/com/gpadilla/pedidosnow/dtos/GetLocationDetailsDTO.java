package com.gpadilla.pedidosnow.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class GetLocationDetailsDTO {
    private final String name;
    private final GetAddressDetailsDTO address;
}
