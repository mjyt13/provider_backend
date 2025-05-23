package org.example.provider.dto;

import java.util.List;
// JSON 2, понадобится на странице тарифа
public record TariffClientsDto(
        Long tariffId,
        String tariffName,
        String tariffType,
        String tariffDescription,
        List<ClientInfoDto> clients
) {}
