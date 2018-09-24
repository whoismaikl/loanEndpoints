package com.qred.task.domain.responseDto;

public class LoanConfirmResponseDto {


    private String responce;
    private LoanSchedurelResponseDto loanScheduler;

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }

    public LoanSchedurelResponseDto getLoanScheduler() {
        return loanScheduler;
    }

    public void setLoanScheduler(LoanSchedurelResponseDto loanScheduler) {
        this.loanScheduler = loanScheduler;
    }


    public LoanConfirmResponseDto(String responce, LoanSchedurelResponseDto loanScheduler) {
        this.responce = responce;
        this.loanScheduler = loanScheduler;
    }
}

