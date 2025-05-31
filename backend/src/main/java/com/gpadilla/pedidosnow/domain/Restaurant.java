package com.gpadilla.pedidosnow.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "auth0_id", nullable = false, unique = true)
    private String auth0Id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @Column(name = "legal_address", length = 1000, nullable = false)
    private String legalAddress;

    @Column(name = "logo_img_url", length = 1000)
    private String logoImgUrl;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantLocation> locations = new ArrayList<>();
}
