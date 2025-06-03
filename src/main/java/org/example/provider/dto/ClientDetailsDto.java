package org.example.provider.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClientDetailsDto(
        Long id,
        String name,
        String address,
        String details,
        LocalDate signupDate,
        BigDecimal totalDebt
) {}