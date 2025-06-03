package org.example.provider.repository.tariff;

import org.example.provider.dto.*;
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
        t.id AS tariffId,
        t.name AS tariffName,
        'internet' AS tariffType,
        t.description AS tariffDescription,
        c.id AS clientId,
        c.name AS clientName,
        ic.debt AS debt,
        ic.expiration_date AS expirationDate,
        c.signup_date AS signupDate
    FROM communication."Internet_Tariff" t
    JOIN communication."Internet_Contract" ic ON t.id = ic."Internet_Tariff_id"
    JOIN communication."Client" c ON ic."Client_id" = c.id
    WHERE t.name = :tariffName
    ORDER BY ic.debt DESC""",
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
        GROUP BY t.id, t.name, t.cost, t.description
        ORDER BY t.id""",
        nativeQuery = true)
    List<TariffInfoProjection> findAllTariffClients();

    @Query(value = """
        SELECT
            t.id,
            'internet' as type,
            t.name, 
            t.cost, 
            t.description,
            0 as clientCount
        FROM communication."Internet_Tariff" t 
        WHERE t.name = :tariffName
        """, nativeQuery = true)
    TariffInfoProjection findTariffByName(@Param("tariffName") String tariffName);
}
