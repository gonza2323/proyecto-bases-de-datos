package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Customer, Long> {
}
