package com.qred.task.domain.mapper;


import com.qred.task.domain.entity.Company;
import com.qred.task.domain.entity.Loan;
import com.qred.task.domain.mapper.utilDtoObjects.LoanDto;
import com.qred.task.domain.responseDto.LoanApplicationsByCompanyDto;
import org.mapstruct.Mapper;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


@Mapper(componentModel = "spring")
public abstract class LoanApplicationsByCompanyMapper {


    public LoanApplicationsByCompanyDto mapCompanyToResponce(Company company) {
        if (company == null) {
            return null;
        }
        String name = "";
        LoanApplicationsByCompanyDto responseCompany = new LoanApplicationsByCompanyDto();
        name = company.getName();
        if(name == null)
                name = "Company name not provided";
        responseCompany.setName(name);
        responseCompany.setRegistrationNumber(company.getRegistrationNumber());
        responseCompany.setBlacklisted(company.isBlacklisted());

        responseCompany.setLoans(mapLoans(company.getLoans()));

        return responseCompany;
    }


    private Set<LoanDto> mapLoans(Set<Loan> loans) {
        Set<LoanDto> responseLoans = new HashSet<>();

        for (Loan l : loans) {
            LoanDto loanDto = new LoanDto();
            loanDto.setLoanId(l.getId());
            loanDto.setLoanAmount(l.getLoanAmount());
            loanDto.setTerm(l.getTerm());
            loanDto.setMonthlyPayment(l.getMonthlyPayment());
            loanDto.setConfirmed(l.isConfirmed());
            loanDto.setValidated(l.isValidated());
            loanDto.setRejected(l.isRejected());
            loanDto.setDateTaken(DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH).format(l.getDateTaken()));
            responseLoans.add(loanDto);
        }

        return responseLoans;
    }


}
