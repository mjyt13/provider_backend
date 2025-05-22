package org.example.provider.repository.tariff;

import org.example.provider.dto.TariffInfoDto;
import org.example.provider.model.tariff.TelephonyTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TelephonyTariffRepository extends  JpaRepository<TelephonyTariff, Long>{
    @Query("SELECT t FROM TelephonyTariff t JOIN FETCH t.contracts WHERE t.id = :id")
    Optional<TelephonyTariff> findByIdWithContracts(@Param("id") Long id);

    @Query("""
        SELECT NEW org.example.provider.dto.TariffInfoDto(
            t.id, 'telephony', t.name, t.cost, t.description, COUNT(c))
        FROM TelephonyTariff t LEFT JOIN t.contracts c
        GROUP BY t.id""")
    List<TariffInfoDto> findAllTariffClients();
}
