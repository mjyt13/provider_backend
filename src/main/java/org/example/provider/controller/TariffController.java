package org.example.provider.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.provider.dto.TariffClientsDto;
import org.example.provider.dto.TariffDto;
import org.example.provider.dto.TariffInfoDto;
import org.example.provider.model.tariff.InternetTariff;
import org.example.provider.model.tariff.TelephonyTariff;
import org.example.provider.service.TariffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.net.URI;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class TariffController {
    private final TariffService tariffService;
    //для ответа на POST запросы
    private URI buildLocationUri(String tariffType, Object tariff) {
        Long id = switch (tariffType.toLowerCase()) {
            case "internet" -> ((InternetTariff) tariff).getId();
            case "telephony" -> ((TelephonyTariff) tariff).getId();
            default -> throw new IllegalArgumentException("Invalid tariff type");
        };
        return URI.create("/api/" + tariffType + "/tariffs/" + id);
    }
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

    // 1.5. создание тарифа какой-то услуги
    @PostMapping("/{serviceType}/tariffs")
    public ResponseEntity<?> createTariff(
            @PathVariable String serviceType,
            @RequestBody TariffDto dto
    ){
        Object createdTariff = tariffService.createTariff(dto,serviceType);
        return ResponseEntity.created(buildLocationUri(serviceType,createdTariff)).body(createdTariff);
    }

    // 1.5.5. удаление определенного тарифа по id
    @DeleteMapping("/{tariffType}/tariffs/{id}")
    public ResponseEntity<Void> deleteTariff(
            @PathVariable String tariffType,
            @PathVariable Long id) {

        tariffService.deleteTariff(id, tariffType);
        return ResponseEntity.noContent().build();
    }

    // 2. список клиентов данного тарифа
    @GetMapping("/{serviceType}/{tariffName}/clients")
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
