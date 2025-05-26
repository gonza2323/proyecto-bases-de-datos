package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserCreationDetails {
    private final String name;
    private final String email;
    private final String auth0Id;
    private final String legalAddress;
}
