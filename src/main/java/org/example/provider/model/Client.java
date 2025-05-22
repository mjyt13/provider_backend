package org.example.provider.model;

import jakarta.persistence.*;
import org.example.provider.model.contract.InternetContract;
import org.example.provider.model.contract.TelephonyContract;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "\"Client\"", schema = "communication")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
//    private String address;
    private String details;

    @Column(name="signup_date")
    private LocalDate signupDate;

    @OneToMany(mappedBy = "client")
    private List<InternetContract> internetContracts;

    @OneToMany(mappedBy = "client")
    private List<TelephonyContract> telephonyContracts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDate signupDate) {
        this.signupDate = signupDate;
    }
}
