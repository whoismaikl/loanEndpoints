package com.qred.task.domain.exception;

public class BlackListedException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String message;

    public BlackListedException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}