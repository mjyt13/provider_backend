package org.example.provider.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.provider.dto.ClientDto;
import org.example.provider.dto.ContractDto;
import org.example.provider.model.Client;
import org.example.provider.model.contract.InternetContract;
import org.example.provider.model.contract.TelephonyContract;
import org.example.provider.model.tariff.InternetTariff;
import org.example.provider.model.tariff.TelephonyTariff;
import org.example.provider.repository.ClientRepository;
import org.example.provider.repository.contract.InternetContractRepository;
import org.example.provider.repository.contract.TelephonyContractRepository;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    private final InternetContractRepository internetContractRepo;
    private final TelephonyContractRepository telephonyContractRepo;
    private final ClientRepository clientRepo;
    private final TariffService tariffService;

    public ContractService(InternetContractRepository internetContractRepo,
                           TelephonyContractRepository telephonyContractRepo,
                           ClientRepository clientRepo,
                           TariffService tariffService) {
        this.internetContractRepo = internetContractRepo;
        this.telephonyContractRepo = telephonyContractRepo;
        this.clientRepo = clientRepo;
        this.tariffService = tariffService;
    }

    public Object createContract(ContractDto dto, String serviceType){
        Client client = clientRepo.findById(dto.clientId()).
                orElseThrow(()->new EntityNotFoundException("Client not found"));
        return switch (serviceType.toLowerCase()){
            case "internet" -> {
                InternetTariff tariff = (InternetTariff) tariffService.getTariffById(dto.tariffId(), "internet");
                InternetContract contract = new InternetContract();
                contract.setClient(client);
                contract.setTariff(tariff);
                contract.setDebt(dto.debt());
                contract.setExpirationDate(dto.expirationDate());
                yield internetContractRepo.save(contract);
            }
            case "telephony" -> {
                TelephonyTariff tariff = (TelephonyTariff) tariffService.getTariffById(dto.tariffId(), "telephony");
                TelephonyContract contract = new TelephonyContract();
                contract.setClient(client);
                contract.setTariff(tariff);
                contract.setDebt(dto.debt());
                contract.setExpirationDate(dto.expirationDate());
                yield telephonyContractRepo.save(contract);
            }
            default ->{
                yield 0;
            }
        };
    }

    @Transactional
    public void deleteContract(Long id, String contractType) {
        switch (contractType.toLowerCase()) {
            case "internet":
                internetContractRepo.deleteById(id);
                break;

            case "telephony":
                telephonyContractRepo.deleteById(id);
                break;

            default:
                throw new IllegalArgumentException("Invalid contract type");
        }
    }
}
