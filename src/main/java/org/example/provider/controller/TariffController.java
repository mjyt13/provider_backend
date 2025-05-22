package org.example.provider.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.provider.dto.TariffClientsDto;
import org.example.provider.dto.TariffInfoDto;
import org.example.provider.service.TariffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TariffController {
    private final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    // 0. все тарифы
    @GetMapping("/tariffs")
    public ResponseEntity<List<TariffInfoDto>> getAllTariffs(){
        System.out.println("пытаемся получить все тарифы");
        List<TariffInfoDto> tariffs = tariffService.getAllTariffsInfo();
        System.out.println("тарифы получились");
        return ResponseEntity.ok(tariffs);
    }

    // 1. тарифы данной услуги
    @GetMapping("/{serviceType}/tariffs")
    public ResponseEntity<List<TariffInfoDto>> getTarifffsByType(
            @PathVariable String serviceType){
        System.out.println("пытаемся получить конкретные тарифы");
        List<TariffInfoDto> dtos = tariffService.getTariffsByType(serviceType);
        System.out.println("получили, дай пять чувак");
        return ResponseEntity.ok(dtos);
    }

    // 2. список тарифов данной услуги
    @GetMapping("/{serviceType}/tariffs/{tariffId}/clients")
    public ResponseEntity<TariffClientsDto> getTariffClients(
            @PathVariable Long tariffId,
            @RequestParam String type
    ){
        TariffClientsDto dto = tariffService.getTariffClients(tariffId,type);
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
