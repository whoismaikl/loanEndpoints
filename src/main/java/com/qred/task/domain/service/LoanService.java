package com.qred.task.domain.service;


import com.qred.task.domain.constants.LoanConstants;
import com.qred.task.domain.dto.LoanApplicationDto;
import com.qred.task.domain.exception.LoanNotValidatedException;
import com.qred.task.domain.exception.LoanPaymentLimitExceededException;
import com.qred.task.domain.mapper.LoanApplicationsByCompanyMapper;
import com.qred.task.domain.responseDto.*;
import com.qred.task.domain.entity.Company;
import com.qred.task.domain.entity.Loan;
import com.qred.task.domain.exception.BlackListedException;
import com.qred.task.domain.exception.LoanApplyParameterInputException;
import com.qred.task.domain.repository.CompanyRepository;
import com.qred.task.domain.repository.LoanRepository;
import com.qred.task.domain.utils.LoanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class LoanService {

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private LoanRepository loanRepository;

    @Inject
    private LoanApplicationsByCompanyMapper loanApplicationsByCompanyMapper;

    @Transactional
    public LoanApplyResponseDto applyForLoan(LoanApplicationDto loanRequest) throws BlackListedException {

        if (loanRequest.getTerm() == 0)
            loanRequest.setTerm(6);

        if (loanRequest.getLoanAmount() == null || loanRequest.getCompanyRegistrationNumber() == null || loanRequest.getEmail() == null || loanRequest.getPhone() == null || loanRequest.getYearlyTurnover() == null) {
            throw new LoanApplyParameterInputException("Must pass all required values(Loan amount, Company registration number, e-mail, phone, Company yearly turnover");
        }

        Company company = companyRepository.findByRegistrationNumber(loanRequest.getCompanyRegistrationNumber());

        if (company == null) {

            Company newCompany = new Company(loanRequest.getCompanyRegistrationNumber(),
                    loanRequest.getCompanyName(),
                    loanRequest.getCompanyType(),
                    loanRequest.getEmail(),
                    loanRequest.getPhone(),
                    loanRequest.getYearlyTurnover(),
                    false,
                    LocalDateTime.now());

            company = companyRepository.saveAndFlush(newCompany);
        } else {

            if (company.isBlacklisted()) {
                throw new BlackListedException("Company with regNr: " + company.getRegistrationNumber() + " is blacklisted, please contact support!");
            }
            //we assume that company  g-rows and things changing(turnover)
            company.setEmail(loanRequest.getEmail());
            company.setPhone(loanRequest.getPhone());
            company.setLastLoanTime(LocalDateTime.now());
            company.setBlacklisted(false);
            company.setYearlyTurnover(loanRequest.getYearlyTurnover());
            if (loanRequest.getCompanyName() != null)
                company.setName(loanRequest.getCompanyName());
            if (loanRequest.getCompanyType() != null)
                company.setType(loanRequest.getCompanyType());
        }

        checkTimeBetweenRequests(company);

        BigDecimal monthlyPayment = LoanUtils.getMonthlyPayment(loanRequest.getLoanAmount(), loanRequest.getTerm());

        Loan loan = new Loan(company.getId(),
                loanRequest.getLoanAmount(),
                loanRequest.getTerm(),
                monthlyPayment,
                LocalDateTime.now(),
                false,
                false,
                false);
        loanRepository.saveAndFlush(loan);


        return new LoanApplyResponseDto("Company successefully applied for loan", "/v1/validateLoanApplication");

    }

    private void checkTimeBetweenRequests(Company company) throws BlackListedException {
        LocalDateTime currentLoanTime = LocalDateTime.now();
        Set<Loan> loans = company.getLoans();

        if (loans.size() >= 3) {
            List<LocalDateTime> dates = new ArrayList();

            for (Loan l : loans) {
                dates.add(l.getDateTaken());
            }
            Collections.sort(dates);
            LocalDateTime thirdDate = dates.get(dates.size()-2);

            long seconds = thirdDate.until(currentLoanTime, ChronoUnit.SECONDS);
            if(seconds <=60){
                company.setBlacklisted(true);
                companyRepository.saveAndFlush(company);
                throw new BlackListedException("To many requests per minute, company " + company.getRegistrationNumber() + " is blacklisted");
            }
        }
    }

    public LoanApplicationsByCompanyDto getLoanApplicationsByCompany(String registrationNumber) {
        return loanApplicationsByCompanyMapper.mapCompanyToResponce(companyRepository.findByRegistrationNumber(registrationNumber));
    }

    public LoansByCompanies getAllLoanApplications() {
        LoansByCompanies responseCompanies = new LoansByCompanies();
        List<Company> allCompanies = companyRepository.findAll();
        List<LoanApplicationsByCompanyDto> companies = new ArrayList<>();

        for (Company c : allCompanies) {
            companies.add(loanApplicationsByCompanyMapper.mapCompanyToResponce(c));
        }
        responseCompanies.setCompanies(companies);
        return responseCompanies;
    }

    public ResponseDto rejectLoanApplication(String registrationNumber, int loanId, boolean loanStatus) {

        ResponseDto loanResponse = new ResponseDto("Loan with id " + loanId + "for company " + registrationNumber + " not found!");

        Company company = companyRepository.findByRegistrationNumber(registrationNumber);
        Set<Loan> loans = company.getLoans();
        for (Loan loan : loans) {
            if (loan.getId() == loanId) {
                loan.setRejected(loanStatus);
                loanResponse.setResponse("Loan #" + loanId + " for company " + registrationNumber + " set status to " + (loanStatus ? "rejected" : "unrejected") + "!");
                companyRepository.saveAndFlush(company);
                break;
            }
        }
        return loanResponse;
    }

    public ResponseDto validateLoanApplication(String registrationNumber, int loanId) throws BlackListedException {

        Loan loan = loanRepository.getOne(Long.valueOf(loanId));
        if (loan.isValidated())
            return new ResponseDto("Loan #" + loanId + " for company " + registrationNumber + " alredy validated!");


        Company company = companyRepository.findByRegistrationNumber(registrationNumber);
        if (company.isBlacklisted() == true) {
            throw new BlackListedException("Loan can not be validated, company " + registrationNumber + " is blacklisted!");
        }

        BigDecimal monthlyAllowedPayments = LoanUtils.getMonthlyAllowedPaymentLimit(company.getYearlyTurnover());
        BigDecimal montlyPaymentsForCompany = LoanUtils.allMonthlyPayments(company);
        BigDecimal currentLoanPayments = LoanUtils.getMonthlyPayment(loan.getLoanAmount(), loan.getTerm());
        BigDecimal totalMonthlyPaymentsWithCurrentLoan = currentLoanPayments.add(montlyPaymentsForCompany);

        if (totalMonthlyPaymentsWithCurrentLoan.compareTo(monthlyAllowedPayments) == 1) {
            throw new LoanPaymentLimitExceededException("Total loan payments for company " + registrationNumber + " is greater than allowed limit!");
        }
        loan.setValidated(true);
        loanRepository.saveAndFlush(loan);

        return new ResponseDto("Loan #" + loanId + " for company " + registrationNumber + " successefully validated!");
    }


    public LoanSchedurelResponseDto getLoanSchedule(String companyRegistrationNumber, int loanId) {
        LoanSchedurelResponseDto scheduler = new LoanSchedurelResponseDto();
        Company company = companyRepository.findByRegistrationNumber(companyRegistrationNumber);
        Loan loan = loanRepository.getOne(Long.valueOf(loanId));


        scheduler.setAmount(String.valueOf(loan.getLoanAmount()) + "Eur");
        scheduler.setInterestRate(LoanConstants.INTEREST_RATE + "%");
        scheduler.setTerm(loan.getTerm() + " months");
        scheduler.setOnDate((DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH).format(loan.getDateTaken())));

        LocalDateTime dateTaken = loan.getDateTaken();
        BigDecimal monthlyPrincipal = loan.getLoanAmount().divide(BigDecimal.valueOf(loan.getTerm()));
        BigDecimal monthlyComnmision = LoanUtils.getInterestAmount(loan.getLoanAmount()).divide(BigDecimal.valueOf(loan.getTerm()));

        List<Integer> paymentList = new ArrayList<>();
        List<String> termDateList = new ArrayList<>();
        List<BigDecimal> principalList = new ArrayList<>();
        List<BigDecimal> commisionList = new ArrayList<>();

        for (int i = 1; i <= loan.getTerm(); i++) {

            paymentList.add(i);
            //String date = String.valueOf(dateTaken.plusMonths(1);
            termDateList.add(DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH).format((dateTaken.plusMonths(i))));
            principalList.add(monthlyPrincipal);
            commisionList.add(monthlyComnmision);
        }

        scheduler.setPaymentNumber(paymentList);
        scheduler.setTermDate(termDateList);
        scheduler.setPrincipal(principalList);
        scheduler.setCommision(commisionList);
        scheduler.setTotal(loan.getLoanAmount() + "Eur, Commision: " + LoanUtils.getInterestAmount(loan.getLoanAmount()));

        return scheduler;

    }

    public LoanConfirmResponseDto confirmLoanApplication(String companyRegistrationNumber, int loanId) {

        Loan loan = loanRepository.getOne(Long.valueOf(loanId));
        if (!loan.isValidated()) {
            throw new LoanNotValidatedException("Loan can not be confirmed before validation!");
        }
        if (loan.isConfirmed()) {
            return new LoanConfirmResponseDto("Loan already confirmed!", null);
        }


        LoanConfirmResponseDto loanConfirmResponse = new LoanConfirmResponseDto("Loan #" + loanId + " for company " + companyRegistrationNumber + " successefully confirmed !", getLoanSchedule(companyRegistrationNumber, loanId));

        return loanConfirmResponse;

    }
}




























