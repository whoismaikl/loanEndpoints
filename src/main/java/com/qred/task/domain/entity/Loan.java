package com.qred.task.domain.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")

public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long companyId;
    private BigDecimal loanAmount;
    private int term;//date?
    private BigDecimal monthlyPayment;
    private LocalDateTime dateTaken;
    private boolean isValidated;
    private boolean isConfirmed;
    private boolean isRejected;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(LocalDateTime dateTaken) {
        this.dateTaken = dateTaken;
    }

    public int getTerm() {
        return term;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    //    public Company getCompany() {
//        return company;
//    }
//
//    public void setCompany(Company company) {
//        this.company = company;
//    }

    public Loan(Long companyId, BigDecimal loanAmount, int term, BigDecimal monthlyPayment, LocalDateTime dateTaken, boolean isValidated, boolean isConfirmed, boolean isRejected) {
        this.companyId = companyId;
        this.loanAmount = loanAmount;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.dateTaken = dateTaken;
        this.isValidated = isValidated;
        this.isConfirmed = isConfirmed;
        this.isRejected = isRejected;
    }

    public Loan() {
    }
}
