package com.qred.task.domain.dto;

import java.math.BigDecimal;

public class LoanApplicationDto {

    private BigDecimal loanAmount;
    private String companyRegistrationNumber;
    private String email;
    private String phone;
    private BigDecimal yearlyTurnover;
    private int term;
    private String companyName;
    private String companyType;


    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
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

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }


    public LoanApplicationDto(BigDecimal loanAmount, String companyRegistrationNumber, String email, String phone, BigDecimal yearlyTurnover, int term, String companyName, String companyType) {
        this.loanAmount = loanAmount;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.email = email;
        this.phone = phone;
        this.yearlyTurnover = yearlyTurnover;
        this.term = term;
        this.companyName = companyName;
        this.companyType = companyType;
    }

    public LoanApplicationDto() {
    }
}
