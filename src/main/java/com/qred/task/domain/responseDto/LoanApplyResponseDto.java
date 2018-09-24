package com.qred.task.domain.responseDto;

public class LoanApplyResponseDto {

    private String response;
    private String applicationValidationLink;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getApplicationValidationLink() {
        return applicationValidationLink;
    }

    public void setApplicationValidationLink(String applicationValidationLink) {
        this.applicationValidationLink = applicationValidationLink;
    }

    public LoanApplyResponseDto(String response, String applicationValidationLink) {
        this.response = response;
        this.applicationValidationLink = applicationValidationLink;
    }
}

