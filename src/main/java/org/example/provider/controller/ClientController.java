package org.example.provider.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.provider.dto.ClientDetailsDto;
import org.example.provider.dto.ClientDto;
import org.example.provider.dto.ClientInfoDto;
import org.example.provider.model.Client;
import org.example.provider.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<List<ClientResponseDto>> getAllClients(){
        List<Client> clients = clientService.getAllClients();
        List<ClientResponseDto> response = new ArrayList<>();
        for (Client client: clients){
            response.add(convertToDto(client));
        };
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailsDto> getClientById(@PathVariable Long id) {
        try{
            ClientDetailsDto dto = clientService.getClientWithDebt(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
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

    private ClientResponseDto convertToDto(Client client){
        return new ClientResponseDto(client.getId(),
                client.getName(),
                client.getDetails(),
                client.getAddress(),
                client.getSignupDate());
    }
    public record ClientResponseDto(
            Long id,
            String name,
            String details,
            String address,
            LocalDate signupDate
    ){}
}
