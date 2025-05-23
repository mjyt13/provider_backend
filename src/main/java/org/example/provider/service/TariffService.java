package org.example.provider.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import org.example.provider.dto.ClientInfoDto;
import org.example.provider.dto.TariffClientsDto;
import org.example.provider.dto.TariffClientsProjection;
import org.example.provider.dto.TariffInfoDto;
import org.example.provider.model.tariff.InternetTariff;
import org.example.provider.model.tariff.TelephonyTariff;
import org.example.provider.repository.contract.InternetContractRepository;
import org.example.provider.repository.contract.TelephonyContractRepository;
import org.example.provider.repository.tariff.InternetTariffRepository;
import org.example.provider.repository.tariff.TelephonyTariffRepository;
import org.springframework.stereotype.Service;

@Service
public class TariffService {
    private final TelephonyTariffRepository telephonyTariffRepository;
    private final InternetTariffRepository internetTariffRepository;
    private final TelephonyContractRepository telephonyContractRepository;
    private final InternetContractRepository internetContractRepository;

    public TariffService(
            TelephonyTariffRepository telephonyTariffRepository,
            InternetTariffRepository internetTariffRepository,
            TelephonyContractRepository telephonyContractRepository,
            InternetContractRepository internetContractRepository
    ){
        this.internetContractRepository = internetContractRepository;
        this.internetTariffRepository = internetTariffRepository;
        this.telephonyContractRepository = telephonyContractRepository;
        this.telephonyTariffRepository = telephonyTariffRepository;
    }

    public List<TariffInfoDto> getAllTariffsInfo(){
        List<TariffInfoDto> telephonyTariffs = telephonyTariffRepository.findAllTariffClients().stream()
                .map(p -> new TariffInfoDto(
                        p.getId(),
                        p.getType(),
                        p.getName(),
                        p.getCost(),
                        p.getDescription(),
                        p.getClientCount()))
                .toList();
        List<TariffInfoDto> internetTariffs = internetTariffRepository.findAllTariffClients().stream()
                .map(p -> new TariffInfoDto(
                        p.getId(),
                        p.getType(),
                        p.getName(),
                        p.getCost(),
                        p.getDescription(),
                        p.getClientCount()))
                .toList();
        List<TariffInfoDto> allTariffs = new ArrayList<>();
        allTariffs.addAll(telephonyTariffs);
        allTariffs.addAll(internetTariffs);
        System.out.println("а мы в тарифах");
        return allTariffs;
    }

    public List<TariffInfoDto> getTariffsByType(String serviceType){
        switch (serviceType.toLowerCase()){
            case "telephony" ->{
                List<TariffInfoDto> telephonyTariffs = telephonyTariffRepository.findAllTariffClients().stream()
                        .map(p -> new TariffInfoDto(
                                p.getId(),
                                p.getType(),
                                p.getName(),
                                p.getCost(),
                                p.getDescription(),
                                p.getClientCount()))
                        .toList();
                return telephonyTariffs;
            }
            case "internet" -> {
                List<TariffInfoDto> internetTariffs = internetTariffRepository.findAllTariffClients().stream()
                        .map(p -> new TariffInfoDto(
                                p.getId(),
                                p.getType(),
                                p.getName(),
                                p.getCost(),
                                p.getDescription(),
                                p.getClientCount()))
                        .toList();
                return internetTariffs;
            }
            default -> throw new IllegalArgumentException("Специально для тарифов исключение");
        }
    }

    public TariffClientsDto getTariffClients(String tariffName, String serviceType){
        List<TariffClientsProjection> projections = new ArrayList<>();
        switch (serviceType.toLowerCase()) {
            case "telephony" -> {
                System.out.println("получение тарифа телефонии");
                projections = telephonyTariffRepository.findClientsByTariffName(tariffName);
            }
            case "internet" -> {
                System.out.println("получение тарифа интернета");
                projections = internetTariffRepository.findClientsByTariffName(tariffName);
            }
            default -> throw new IllegalArgumentException("а это тарифы");
        }
        System.out.println("тариф получен");
        if(projections.isEmpty()) {
            throw new EntityNotFoundException("Tariff not found with name: "+tariffName);
        }
        TariffClientsProjection first = projections.get(0);
        List<ClientInfoDto> clients = projections.stream().map(
                p -> new ClientInfoDto(
                        p.getClientId(),
                        p.getClientName(),
                        p.getDebt(),
                        p.getExpirationDate(),
                        p.getSignupDate()
                )
        ).toList();
        return new TariffClientsDto(
                first.getTariffId(),
                first.getTariffName(),
                first.getTariffType(),
                first.getTariffDescription(),
                clients
        );

    }
}

