package com.gpadilla.pedidosnow.repositories;

import com.gpadilla.pedidosnow.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
