package org.example.provider.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ClientDetailsProjection {
    Long getId();
    String getName();
    String getAddress();
    String getDetails();
    LocalDate getSignupDate();
    BigDecimal getTotalDebt();
}
