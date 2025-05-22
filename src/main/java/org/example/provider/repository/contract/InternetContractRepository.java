package org.example.provider.repository.contract;

import org.example.provider.model.contract.InternetContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface InternetContractRepository extends JpaRepository<InternetContract,Long>,
        ContractRepository<InternetContract> {}