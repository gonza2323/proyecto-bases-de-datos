package com.gpadilla.pedidosnow.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_seq")
    private Long id;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "municipio", nullable = false)
    private String municipio;

    @Column(name = "localidad", nullable = false)
    private String localidad;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "block_no")
    private String blockNo;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "floor_no")
    private String floorNo;

    @Column(name = "apartment_no")
    private String apartmentNo;

    @Column(name = "obervation")
    private String obervation;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "is_client_default")
    private boolean isClientDefault;
}
