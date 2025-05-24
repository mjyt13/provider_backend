package org.example.provider.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.provider.dto.ClientDto;
import org.example.provider.model.Client;
import org.example.provider.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepo;

    public ClientService(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public Client createClient(ClientDto clientDto){
        Client client = new Client();
        client.setName(clientDto.name());
        client.setAddress(clientDto.address());
        client.setDetails(clientDto.details());
        client.setSignupDate(clientDto.signupDate());
        return clientRepo.save(client);
    }

    public void deleteClient(Long id) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        clientRepo.delete(client);
    }
}
