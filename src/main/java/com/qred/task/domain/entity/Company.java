package com.qred.task.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy="companyId")
    private Set<Loan> loans;
    private String registrationNumber;
    private String name;
    private String type;
    private String email;
    private String phone;
    private BigDecimal yearlyTurnover;
    private boolean isBlacklisted;
    private LocalDateTime lastLoanTime; //???? DUNNO


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getYearlyTurnover() {
        return yearlyTurnover;
    }

    public void setYearlyTurnover(BigDecimal yearlyTurnover) {
        this.yearlyTurnover = yearlyTurnover;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        isBlacklisted = blacklisted;
    }

    public LocalDateTime getLastLoanTime() {
        return lastLoanTime;
    }

    public void setLastLoanTime(LocalDateTime lastLoanTime) {
        this.lastLoanTime = lastLoanTime;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    public Company(String registrationNumber, String email, String phone, BigDecimal yearlyTurnover, boolean isBlacklisted, LocalDateTime lastLoanTime) {
        this.registrationNumber = registrationNumber;
        this.email = email;
        this.phone = phone;
        this.yearlyTurnover = yearlyTurnover;
        this.isBlacklisted = isBlacklisted;
        this.lastLoanTime = lastLoanTime;
    }

    public Company(String registrationNumber, String name, String type, String email, String phone, BigDecimal yearlyTurnover, boolean isBlacklisted, LocalDateTime lastLoanTime) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.yearlyTurnover = yearlyTurnover;
        this.isBlacklisted = isBlacklisted;
        this.lastLoanTime = lastLoanTime;
    }

    public Company() {
    }
}
