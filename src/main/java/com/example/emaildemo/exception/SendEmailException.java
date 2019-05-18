package com.example.emaildemo.exception;

public class SendEmailException extends RuntimeException {

    public SendEmailException(String errorMessage) {
        super(errorMessage);
    }

    SendEmailException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

}
