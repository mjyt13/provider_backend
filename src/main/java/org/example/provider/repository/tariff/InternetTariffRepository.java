package org.example.provider.repository.tariff;

import org.example.provider.dto.TariffInfoDto;
import org.example.provider.model.tariff.InternetTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InternetTariffRepository extends JpaRepository<InternetTariff, Long> {
    public interface TariffInfoProjection {
        Long getId();
        String getType();
        String getName();
        BigDecimal getCost();
        String getDescription();
        Integer getClientCount();
    }
    @Query("SELECT t FROM InternetTariff t JOIN FETCH t.contracts WHERE t.id = :id")
    Optional<InternetTariff> findByIdWithContracts(@Param("id") Long id);
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
