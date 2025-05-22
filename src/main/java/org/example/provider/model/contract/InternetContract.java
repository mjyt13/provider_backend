package org.example.provider.model.contract;
import jakarta.persistence.*;
import org.example.provider.model.tariff.InternetTariff;

@Entity
@Table(name="\"Internet_Contract\"",schema = "communication")
public class InternetContract extends Contract{
    @ManyToOne
    @JoinColumn(name = "\"Internet_Tariff_id\"")
    private InternetTariff tariff;

    public InternetTariff getTariff() {
        return tariff;
    }

    public void setTariff(InternetTariff tariff) {
        this.tariff = tariff;
    }
}
