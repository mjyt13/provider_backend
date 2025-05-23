package org.example.provider.repository.tariff;

import org.example.provider.dto.TariffClientsProjection;
import org.example.provider.dto.TariffInfoDto;
import org.example.provider.dto.TariffInfoProjection;
import org.example.provider.model.tariff.InternetTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InternetTariffRepository extends JpaRepository<InternetTariff, Long> {
    @Query(value = """
    SELECT 
        t.id as tariffId,
        t.name as tariffName,
        'internet' as tariffType,
        t.description as tariffDescription,
        c."Client_id" as clientId,
        cl.name as clientName,
        c.debt as debt,
        c.expiration_date as expirationDate,
        cl.signup_date as signupDate
        FROM communication."Internet_Tariff" t
        JOIN communication."Internet_Contract" c ON t.id = c."Internet_Tariff_id"
        JOIN communication."Client" cl ON c."Client_id" = cl.id
        WHERE t.name = :tariffName""",
            nativeQuery = true)
    List<TariffClientsProjection> findClientsByTariffName(@Param("tariffName") String tariffName);
    @Query(value = """
        SELECT
            t.id, 
            'internet' as type, 
            t.name, 
            t.cost, 
            t.description, 
            COUNT(c.id) as clientCount 
        FROM communication."Internet_Tariff" t 
        LEFT JOIN communication."Internet_Contract" c
        ON t.id = c."Internet_Tariff_id"
        GROUP BY t.id, t.name, t.cost, t.description""",
        nativeQuery = true)
    List<TariffInfoProjection> findAllTariffClients();
}
