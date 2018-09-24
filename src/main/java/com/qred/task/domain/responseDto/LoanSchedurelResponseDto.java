package com.qred.task.domain.responseDto;

import java.math.BigDecimal;
import java.util.List;

public class LoanSchedurelResponseDto {


    private String amount;
    private String interestRate;
    private String term;
    private String onDate;

    private List<Integer> paymentNumber;
    private List<String> termDate;
    private List<BigDecimal> principal;
    private List<BigDecimal> commision;

    private String total;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getOnDate() {
        return onDate;
    }

    public void setOnDate(String onDate) {
        this.onDate = onDate;
    }

    public List<Integer> getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(List<Integer> paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public List<String> getTermDate() {
        return termDate;
    }

    public void setTermDate(List<String> termDate) {
        this.termDate = termDate;
    }

    public List<BigDecimal> getPrincipal() {
        return principal;
    }

    public void setPrincipal(List<BigDecimal> principal) {
        this.principal = principal;
    }

    public List<BigDecimal> getCommision() {
        return commision;
    }

    public void setCommision(List<BigDecimal> commision) {
        this.commision = commision;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
