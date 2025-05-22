package org.example.provider.repository.tariff;

import org.example.provider.dto.TariffInfoDto;
import org.example.provider.model.tariff.InternetTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InternetTariffRepository extends JpaRepository<InternetTariff, Long> {
    @Query("SELECT t FROM InternetTariff t JOIN FETCH t.contracts WHERE t.id = :id")
    Optional<InternetTariff> findByIdWithContracts(@Param("id") Long id);
    @Query("""
        SELECT NEW org.example.provider.dto.TariffInfoDto(
            t.id, 'internet', t.name, t.cost, t.description, COUNT(c))
        FROM InternetTariff t LEFT JOIN t.contracts c
        GROUP BY t.id""")
    List<TariffInfoDto> findAllTariffClients();


}
