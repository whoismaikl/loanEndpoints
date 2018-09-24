package com.qred.task.domain.utils;

import com.qred.task.domain.constants.LoanConstants;
import com.qred.task.domain.entity.Company;
import com.qred.task.domain.entity.Loan;

import java.math.BigDecimal;
import java.util.Set;

public class LoanUtils {


    public static BigDecimal allMonthlyPayments(Company company) {

        Set<Loan> loans = company.getLoans();
        BigDecimal monthlyPayment = new BigDecimal("0");
        if ((loans != null)) {
            for (Loan l : loans) {
                if (l.isConfirmed()){
                    monthlyPayment = monthlyPayment.add(l.getMonthlyPayment());
                }
            }
        }

        return monthlyPayment;
    }


    public static BigDecimal getMonthlyAllowedPaymentLimit(BigDecimal yearlyTurnover) {
        BigDecimal year = new BigDecimal("12");
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal thirty = new BigDecimal("30");
        return yearlyTurnover.divide(year, 2).divide(hundred, 2).multiply(thirty);
    }


    public static BigDecimal getInterestAmount(BigDecimal confirmedLoanAMount) {
        return getPercent(confirmedLoanAMount, LoanConstants.INTEREST_RATE);
    }


    public static BigDecimal getPercent(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(new BigDecimal(100));
    }

    public static BigDecimal getMonthlyPayment(BigDecimal loanAmount, int term) {
        BigDecimal interestAmount = getInterestAmount(loanAmount);
        BigDecimal totalPayment = loanAmount.add(interestAmount);
        return totalPayment.divide(BigDecimal.valueOf(term),2);
    }
}
























