package com.gpadilla.pedidosnow.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "location")
public class RestaurantLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restauran_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "logo_img_url", length = 1000)
    private String logoImgUrl;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen = false;
}
