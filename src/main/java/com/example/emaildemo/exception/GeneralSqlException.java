package com.example.emaildemo.exception;

public class GeneralSqlException extends RuntimeException {

    public GeneralSqlException(String errorMessage) {
        super(errorMessage);
    }

    GeneralSqlException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

}
