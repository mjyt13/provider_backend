package org.example.provider.model.tariff;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.example.provider.model.contract.TelephonyContract;

import java.util.List;
@Entity
@Table(name = "\"Telephony_Tariff\"",schema = "communication")
public class TelephonyTariff extends Tariff{
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
    private List<TelephonyContract> contracts;

    public List<TelephonyContract> getContracts() {
        return contracts;
    }

    public void setContracts(List<TelephonyContract> contracts) {
        this.contracts = contracts;
    }
}
