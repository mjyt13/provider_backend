package org.example.provider.repository.view;

import org.example.provider.model.view.TelephonyContractView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelephonyContractViewRepository extends JpaRepository<TelephonyContractView, Long> {
    List<TelephonyContractView> findByTariffName(String tariffName);
}
