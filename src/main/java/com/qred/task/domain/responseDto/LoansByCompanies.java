package com.qred.task.domain.responseDto;

import java.util.List;

public class LoansByCompanies {

    List<LoanApplicationsByCompanyDto> companies;

    public List<LoanApplicationsByCompanyDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<LoanApplicationsByCompanyDto> companies) {
        this.companies = companies;
    }
}
