package org.example.provider.repository;

import org.example.provider.dto.ClientInfoDto;
import org.example.provider.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

        // Загружаем клиента без контрактов
        @Query("SELECT c FROM Client c WHERE c.id = :id")
        Optional<Client> findByIdSimple(@Param("id") Long id);

        // Загружаем клиента + интернет-контракты
        @Query("SELECT c FROM Client c LEFT JOIN FETCH c.internetContracts WHERE c.id = :id")
        Optional<Client> findByIdWithInternetContracts(@Param("id") Long id);

        // Загружаем клиента + телефонные контракты
        @Query("SELECT c FROM Client c LEFT JOIN FETCH c.telephonyContracts WHERE c.id = :id")
        Optional<Client> findByIdWithTelephonyContracts(@Param("id") Long id);

}
