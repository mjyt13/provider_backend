package org.example.provider.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.provider.dto.ClientDto;
import org.example.provider.model.Client;
import org.example.provider.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @PostMapping
    public ResponseEntity<Client> createClient(
            @RequestBody
            @Valid ClientDto clientDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(String.valueOf(bindingResult.getFieldErrors()));
        }
        Client createdClient = clientService.createClient(clientDto);
        return ResponseEntity.created(URI.create("/api/clients/" + createdClient.getId()))
                .body(createdClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
