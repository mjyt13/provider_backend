package org.example.provider.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TariffDto(
    @NotBlank(message = "Название тарифа обязательно")
    String name,
    @NotNull(message = "Стоимость обязательна")
    @Positive(message = "Стоимость должна быть положительной")
    BigDecimal cost,
    String description
) {}
