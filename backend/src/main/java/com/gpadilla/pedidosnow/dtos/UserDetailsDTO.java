package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDetailsDTO {
    private final String name;
    private final String email;
    private final String logoImgUrl;
}
