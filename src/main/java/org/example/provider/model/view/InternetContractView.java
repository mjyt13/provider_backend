package org.example.provider.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.hibernate.annotations.View;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Immutable
@Subselect("SELECT " +
        "t.id AS id, " +
        "t.name AS tariff_name, " +
        "c.name AS client, " +
        "ic.debt AS debt, " +
        "ic.expiration_date AS exp_date " +
        "FROM communication.\"Internet_Tariff\" t " +
        "JOIN communication.\"Internet_Contract\" ic ON t.id = ic.\"Internet_Tariff_id\" " +
        "JOIN communication.\"Client\" c ON ic.\"Client_id\" = c.id")
@Synchronize({ "Internet_Tariff", "Internet_Contract", "Client" })
public class InternetContractView {

    @Id
    private Long id; // может быть clientId или contractId

    @Column(name = "tariff_name")
    private String tariffName;
    @Column(name = "client")
    private String client;
    @Column(name = "debt")
    private BigDecimal debt;
    @Column(name = "exp_date")
    private LocalDate exp_date;

    public LocalDate getExp_date() {
        return exp_date;
    }

    public void setExp_date(LocalDate exp_date) {
        this.exp_date = exp_date;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    // геттеры и сеттеры
}