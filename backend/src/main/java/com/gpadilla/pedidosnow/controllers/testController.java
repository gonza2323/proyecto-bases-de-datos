package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.domain.Client;
import com.gpadilla.pedidosnow.repositories.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class testController {

    private final ClientRepository clientRepository;

    public testController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

//    @PostMapping
//    public void addClient(@RequestBody Client client) {
//        clientRepository.save(client);
//    }
}
