package org.example.provider.model.tariff;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.example.provider.model.contract.InternetContract;
import org.example.provider.model.tariff.Tariff;

import java.util.List;

@Entity
@Table(name = "\"Internet_Tariff\"",schema = "communication")
public class InternetTariff extends Tariff {
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
    private List<InternetContract> contracts;

    public List<InternetContract> getContracts() {
        return contracts;
    }

    public void setContracts(List<InternetContract> contracts) {
        this.contracts = contracts;
    }
}
