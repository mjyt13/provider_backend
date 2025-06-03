package org.example.provider.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.provider.dto.*;
import org.example.provider.model.tariff.InternetTariff;
import org.example.provider.model.tariff.TelephonyTariff;
import org.example.provider.repository.tariff.InternetTariffRepository;
import org.example.provider.repository.tariff.TelephonyTariffRepository;
import org.example.provider.repository.view.InternetContractViewRepository;
import org.example.provider.repository.view.TelephonyContractViewRepository;
import org.springframework.stereotype.Service;

@Service
public class TariffService {
    private final TelephonyTariffRepository telephonyTariffRepo;
    private final InternetTariffRepository internetTariffRepo;
    private final InternetContractViewRepository internetRepo;
    private final TelephonyContractViewRepository telephonyRepo;

    public TariffService(
            TelephonyTariffRepository telephonyTariffRepo,
            InternetTariffRepository internetTariffRepo,
            InternetContractViewRepository internetRepo,
            TelephonyContractViewRepository telephonyRepo
    ){

        this.internetTariffRepo = internetTariffRepo;
        this.telephonyTariffRepo = telephonyTariffRepo;
        this.internetRepo = internetRepo;
        this.telephonyRepo = telephonyRepo;
    }

    public List<TariffInfoDto> getAllTariffsInfo(){
        List<TariffInfoDto> telephonyTariffs = telephonyTariffRepo.findAllTariffClients().stream()
                .map(p -> new TariffInfoDto(
                        p.getId(),
                        p.getType(),
                        p.getName(),
                        p.getCost(),
                        p.getDescription(),
                        p.getClientCount()))
                .toList();
        List<TariffInfoDto> internetTariffs = internetTariffRepo.findAllTariffClients().stream()
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
                List<TariffInfoDto> telephonyTariffs = telephonyTariffRepo.findAllTariffClients().stream()
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
                List<TariffInfoDto> internetTariffs = internetTariffRepo.findAllTariffClients().stream()
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

    // получить клиентов тарифа. пользуется фронтом при создании страниц.
    public TariffClientsDto getTariffClients(String tariffName, String serviceType){

        List<TariffClientsProjection> projections;
        switch (serviceType.toLowerCase()) {
            case "telephony" -> {
                System.out.println("получение тарифа телефонии");
                projections = telephonyTariffRepo.findClientsByTariffName(tariffName);
            }
            case "internet" -> {
                System.out.println("получение тарифа интернета");
                projections = internetTariffRepo.findClientsByTariffName(tariffName);
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
        /*
        TariffInfoProjection tariff;
        List<ClientInfoDto> clients;
        switch (serviceType.toLowerCase()){
            case "internet" -> {
                tariff = internetTariffRepo.findTariffByName(tariffName);
                clients = internetRepo.findByTariffName(tariffName).stream()
                        .map(c -> new ClientInfoDto(c.getClient(), c.getDebt(), c.getExp_date())).toList();
            }
            case "telephony" -> {
                tariff = telephonyTariffRepo.findTariffByName(tariffName);
                clients = telephonyRepo.findByTariffName(tariffName).stream()
                        .map(c -> new ClientInfoDto(c.getClient(), c.getDebt(), c.getExp_date())).toList();
            }
            default -> throw new IllegalArgumentException("Неизвестный тип услуги "+tariffName);
        }

        return new TariffClientsDto(tariff.getId(),tariffName,tariff.getType(),tariff.getDescription(),clients);*/
    }

    public Object getTariffById(Long id, String serviceType){
        return switch (serviceType.toLowerCase()){
            case "internet" -> internetTariffRepo.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Internet tariff not found"));
            case "telephony" -> telephonyTariffRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Telephony tariff not found"));
            default -> throw new IllegalArgumentException("Unknown tariff type: " + serviceType);
        };
    }
    public Object createTariff(TariffDto dto, String tariffType){
        return switch (tariffType.toLowerCase()) {
            case "internet" -> {
                InternetTariff tariff = new InternetTariff();
                tariff.setName(dto.name());
                tariff.setCost(dto.cost());
                tariff.setDescription(dto.description());
                yield internetTariffRepo.save(tariff);
            }
            case "telephony" -> {
                TelephonyTariff tariff = new TelephonyTariff();
                tariff.setName(dto.name());
                tariff.setCost(dto.cost());
                tariff.setDescription(dto.description());
                yield telephonyTariffRepo.save(tariff);
            }
            default -> throw new IllegalArgumentException("Unknown tariff type: " + tariffType);
        };
    }
    @Transactional
    public void deleteTariff(Long id, String tariffType) {
        switch (tariffType.toLowerCase()) {
            case "internet":
                InternetTariff internetTariff = internetTariffRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Internet tariff not found"));

                internetTariffRepo.delete(internetTariff);
                break;

            case "telephony":
                TelephonyTariff  telephonyTariff = telephonyTariffRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Internet tariff not found"));
                telephonyTariffRepo.delete(telephonyTariff);
                break;
            default:
                throw new IllegalArgumentException("Invalid tariff type");
        }
    }
}

