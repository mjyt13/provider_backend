package org.example.provider.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.provider.dto.TariffClientsDto;
import org.example.provider.dto.TariffInfoDto;
import org.example.provider.service.TariffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tariffs")
public class TariffController {
    private final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }
    @GetMapping("/{tariffId}/clients")
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
