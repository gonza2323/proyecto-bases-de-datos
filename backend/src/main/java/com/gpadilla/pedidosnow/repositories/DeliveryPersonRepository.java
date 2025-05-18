package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
}
