package com.qred.task.domain.mapper.utilDtoObjects;

import java.math.BigDecimal;

public class LoanDto {
    private Long loanId;
    private BigDecimal loanAmount;
    private int term;
    private BigDecimal monthlyPayment;

    private String dateTaken;
    private boolean isValidated;
    private boolean isConfirmed;
    private boolean isRejected;

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
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

    public String getDateTaken() {
        return dateTaken;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
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

    public LoanDto() {
    }
}
