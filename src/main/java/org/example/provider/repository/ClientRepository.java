package org.example.provider.repository;

import org.example.provider.dto.ClientDetailsDto;
import org.example.provider.dto.ClientDetailsProjection;
import org.example.provider.dto.ClientInfoDto;
import org.example.provider.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = """
    SELECT 
        c.id as id,
        c.name as name,
        c.address as address,
        c.details as details,
        c.signup_date as signupDate,
        COALESCE(SUM(d.debt), 0) as totalDebt
    FROM communication."Client" c
    LEFT JOIN (
        SELECT "Client_id", debt FROM communication."Internet_Contract"
        UNION ALL
        SELECT "Client_id", debt FROM communication."Telephony_Contract"
    ) d ON c.id = d."Client_id"
    WHERE c.id = :clientId
    GROUP BY c.id
""", nativeQuery = true)
    ClientDetailsProjection getClientWithTotalDebt(@Param("clientId") Long clientId);

}
