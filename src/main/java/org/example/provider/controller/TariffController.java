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
        List<TariffInfoDto> tariffs = tariffService.getAllTariffsInfo();
        return ResponseEntity.ok(tariffs);
    }

    // 1. тарифы данной услуги
    @GetMapping("/{serviceType}/tariffs")
    public ResponseEntity<List<TariffInfoDto>> getTarifffsByType(
            @PathVariable String serviceType){
        List<TariffInfoDto> dtos = tariffService.getTariffsByType(serviceType);
        return ResponseEntity.ok(dtos);
    }

    // 2. список клиентов данного тарифа
    @GetMapping("/{serviceType}/tariffs/{tariffName}/clients")
    public ResponseEntity<TariffClientsDto> getTariffClients(
            @PathVariable String serviceType,
            @PathVariable String tariffName
    ){
        TariffClientsDto dto = tariffService.getTariffClients(tariffName,serviceType);
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
