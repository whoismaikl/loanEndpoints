package com.qred.task.domain.exception;

public class LoanApplyParameterInputException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public LoanApplyParameterInputException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}