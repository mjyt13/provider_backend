package org.example.provider.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.provider.dto.ClientDetailsDto;
import org.example.provider.dto.ClientDetailsProjection;
import org.example.provider.dto.ClientDto;
import org.example.provider.model.Client;
import org.example.provider.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepo;

    public ClientService(ClientRepository clientRepo
    ) {
        this.clientRepo = clientRepo;

    }

    public ClientDetailsDto getClientWithDebt(Long clientId) {
        ClientDetailsProjection projection = clientRepo.getClientWithTotalDebt(clientId);
        if(projection == null) throw new IllegalArgumentException("с таким id пользователя не существует");
        ClientDetailsDto dto = new ClientDetailsDto(
                projection.getId(),
                projection.getName(),
                projection.getAddress(),
                projection.getDetails(),
                projection.getSignupDate(),
                projection.getTotalDebt()
        );
        return dto;
    }
    public List<Client> getAllClients (){
        List<Client> clients = clientRepo.findAll();
        return clients;
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
