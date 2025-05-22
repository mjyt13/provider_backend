package org.example.provider.repository.tariff;

import org.example.provider.dto.TariffInfoDto;
import org.example.provider.model.tariff.TelephonyTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TelephonyTariffRepository extends  JpaRepository<TelephonyTariff, Long>{
    @Query("SELECT t FROM TelephonyTariff t JOIN FETCH t.contracts WHERE t.id = :id")
    Optional<TelephonyTariff> findByIdWithContracts(@Param("id") Long id);

    public interface TariffInfoProjection {
        Long getId();
        String getType();
        String getName();
        BigDecimal getCost();
        String getDescription();
        Integer getClientCount();
    }

    @Query(value = """
        SELECT
            t.id, 
            'telephony' as type, 
            t.name, 
            t.cost, 
            t.description, 
            COUNT(c.id) as clientCount 
        FROM communication."Telephony_Tariff" t 
        LEFT JOIN communication."Telephony_Contract" c
        ON t.id = c."Telephony_Tariff_id"
        GROUP BY t.id, t.name, t.cost, t.description""",
    nativeQuery = true)
    List<TariffInfoProjection> findAllTariffClients();
}
