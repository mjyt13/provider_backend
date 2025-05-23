package org.example.provider.dto;

import java.math.BigDecimal;

public interface TariffInfoProjection {
    Long getId();
    String getType();
    String getName();
    BigDecimal getCost();
    String getDescription();
    Integer getClientCount();
}
