package org.example.provider.repository.view;

import org.example.provider.model.view.InternetContractView;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InternetContractViewRepository extends JpaRepository<InternetContractView, Long> {
    List<InternetContractView> findByTariffName(String tariffName);
}
