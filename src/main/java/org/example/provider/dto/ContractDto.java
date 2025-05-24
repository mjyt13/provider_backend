package org.example.provider.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractDto(
        @NotNull(message = "ID клиента обязательно")
        Long clientId,

        @NotNull(message = "ID тарифа обязательно")
        Long tariffId,

        @NotNull(message = "Задолженность должна быть указана")
        @DecimalMin(value = "0.0", message = "Задолженность не может быть отрицательной")
        BigDecimal debt,

        @NotNull(message = "Дата окончания обязательна")
        @Future(message = "Дата окончания должна быть в будущем")
        LocalDate expirationDate
) {}
