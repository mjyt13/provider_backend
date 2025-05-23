package org.example.provider.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TariffClientsProjection {
    Long getTariffId();
    String getTariffName();
    String getTariffType();
    String getTariffDescription();
    Long getClientId();
    String getClientName();
    BigDecimal getDebt();
    LocalDate getExpirationDate();
    LocalDate getSignupDate();
}
