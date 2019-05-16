package com.example.emaildemo.response;


import lombok.Value;

@Value
public class MailResponse {

    private String message;

    private boolean status;

}
