package org.example.provider.model.contract;
import jakarta.persistence.*;
import org.example.provider.model.tariff.TelephonyTariff;

@Entity
@Table(name="\"Telephony_Contract\"",schema = "communication")
public class TelephonyContract extends Contract {
    @ManyToOne
    @JoinColumn(name = "\"Telephony_Tariff_id\"")
    private TelephonyTariff tariff;

    public TelephonyTariff getTariff() {
        return tariff;
    }

    public void setTariff(TelephonyTariff tariff) {
        this.tariff = tariff;
    }
}
