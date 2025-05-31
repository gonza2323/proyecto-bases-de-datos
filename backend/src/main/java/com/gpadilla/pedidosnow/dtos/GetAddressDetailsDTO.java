package com.gpadilla.pedidosnow.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class GetAddressDetailsDTO {
    private final String province;
    private final String municipio;
    private final String localidad;
    private final String street;
    private final String number;
    private final String floor;
    private final String apartment;
    private final String phoneNumber;
    private final String observation;
    private final Double latitude;
    private final Double longitude;
}
