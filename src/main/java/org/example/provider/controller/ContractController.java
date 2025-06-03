package org.example.provider.controller;

import jakarta.validation.Valid;
import org.example.provider.dto.ContractDto;
import org.example.provider.model.contract.InternetContract;
import org.example.provider.model.contract.TelephonyContract;
import org.example.provider.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/{serviceType}")
    public ResponseEntity<?> createContract(
            @PathVariable String serviceType,
            @RequestBody @Valid ContractDto dto){
        Object createdContract = contractService.createContract(dto,serviceType);
        System.out.println("а мы попали после создания вот сюда CONTROLLER");
        ContractResponseDto response = convertToDto(createdContract);
        return ResponseEntity.created(buildLocationUri(serviceType,createdContract)).body(response);
    }

    @DeleteMapping("/{serviceType}/{id}")
    public ResponseEntity<Void> deleteContract(
            @PathVariable String serviceType,
            @PathVariable Long id) {

        contractService.deleteContract(id, serviceType);
        return ResponseEntity.noContent().build();
    }
//    вспомогательное
    private ContractResponseDto convertToDto(Object contract) {
        if (contract instanceof TelephonyContract tc) {
            return new ContractResponseDto(
                    tc.getId(),
                    tc.getClient().getId(),
                    tc.getTariff().getId(),
                    tc.getDebt(),
                    tc.getExpirationDate(),
                    "telephony"
            );
        } else if (contract instanceof InternetContract ic) {
            return new ContractResponseDto(
                    ic.getId(),
                    ic.getClient().getId(),
                    ic.getTariff().getId(),
                    ic.getDebt(),
                    ic.getExpirationDate(),
                    "internet"
            );
        }
        throw new IllegalArgumentException("Unknown contract type");
    }

    public record ContractResponseDto(
            Long id,
            Long clientId,
            Long tariffId,
            BigDecimal debt,
            LocalDate expirationDate,
            String contractType
    ) {}

    private URI buildLocationUri(String contractType, Object contract) {
        Long id = switch (contractType.toLowerCase()) {
            case "internet" -> ((InternetContract) contract).getId();
            case "telephony" -> ((TelephonyContract) contract).getId();
            default -> throw new IllegalArgumentException("Invalid contract type");
        };
        return URI.create("/api/contracts/" + contractType + "/" + id);
    }
}
