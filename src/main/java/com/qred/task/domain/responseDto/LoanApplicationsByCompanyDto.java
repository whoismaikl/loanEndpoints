package com.qred.task.domain.responseDto;

import com.qred.task.domain.entity.Loan;
import com.qred.task.domain.mapper.utilDtoObjects.LoanDto;

import java.util.Set;

public class LoanApplicationsByCompanyDto {


    private String name;
    private String registrationNumber;
    private boolean isBlacklisted;
    private Set<LoanDto> loans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        isBlacklisted = blacklisted;
    }

    public Set<LoanDto> getLoans() {
        return loans;
    }

    public void setLoans(Set<LoanDto> loans) {
        this.loans = loans;
    }

    public LoanApplicationsByCompanyDto() {
    }
}
