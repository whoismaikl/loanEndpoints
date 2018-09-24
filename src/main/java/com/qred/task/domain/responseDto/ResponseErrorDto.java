package com.qred.task.domain.responseDto;



public class ResponseErrorDto {

    private String error;

    public ResponseErrorDto(){}

    public ResponseErrorDto(String error){
        this.error=error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}