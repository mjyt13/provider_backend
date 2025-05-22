package org.example.provider.repository.contract;

import org.example.provider.dto.ClientInfoDto;
import org.example.provider.model.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
@NoRepositoryBean
public interface ContractRepository<T> {
    @Query("SELECT NEW org.example.provider.dto.ClientInfoDto" +
            "(c.client.id, c.client.name, c.debt, c.expirationDate, c.client.signupDate) " +
            "FROM #{#entityName} c WHERE c.tariff.id = :tariffId")
    List<ClientInfoDto> findClientsByTariffId(@Param("tariffId") Long tariffId);
}
