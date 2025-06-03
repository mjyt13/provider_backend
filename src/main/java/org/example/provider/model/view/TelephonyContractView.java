package org.example.provider.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Immutable
@Subselect("SELECT * FROM communication.telephony_contract")
@Table(name = "telephony_contract", schema = "communication")
public class TelephonyContractView {

    @Id
    private Long id; // может быть clientId или contractId

    @Column(name = "tariffName")
    private String tariffName;
    @Column(name = "client")
    private String client;
    @Column(name = "debt")
    private BigDecimal debt;
    @Column(name = "exp_date")
    private LocalDate exp_date;

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public LocalDate getExp_date() {
        return exp_date;
    }

    public void setExp_date(LocalDate exp_date) {
        this.exp_date = exp_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // геттеры и сеттеры
}
