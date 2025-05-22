package org.example.provider.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import org.example.provider.dto.ClientInfoDto;
import org.example.provider.dto.TariffClientsDto;
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
                System.out.println("телефония тарифы здесь?");
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

    public TariffClientsDto getTariffClients(Long tariffId, String tariffType){
        return switch (tariffType.toLowerCase()) {
            case "telephony" -> {
                TelephonyTariff tariff =
                    telephonyTariffRepository.findByIdWithContracts(tariffId).
                            orElseThrow(
                            ()-> new EntityNotFoundException("Telephony Tariff not found"));
                List<ClientInfoDto> clients = telephonyContractRepository.findClientsByTariffId(tariffId);
                yield new TariffClientsDto(tariff.getId(),tariff.getName(),"telephony",clients);
            }
            case "internet" -> {
                InternetTariff tariff =
                    internetTariffRepository.findByIdWithContracts(tariffId).orElseThrow(
                            ()->new EntityNotFoundException("Internet Tariff not found"));
                List<ClientInfoDto> clients = internetContractRepository.findClientsByTariffId(tariffId);
                yield new TariffClientsDto(tariff.getId(),tariff.getName(),"internet",clients);
            }
            default -> throw new IllegalArgumentException("а это тарифы");
        };
    }
}

