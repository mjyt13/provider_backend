package org.example.provider.repository.contract;

import org.example.provider.model.contract.TelephonyContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface TelephonyContractRepository extends JpaRepository<TelephonyContract, Long>,
        ContractRepository<TelephonyContract> {}
