package org.example.provider.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
//база такая, тут инфа по клиенту относительно контркта и тарифа
public record ClientInfoDto(
        Long id,
        String name,
        BigDecimal debt,
        LocalDate expirationDate,
        LocalDate signupDate
) {}
