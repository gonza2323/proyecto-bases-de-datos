package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
