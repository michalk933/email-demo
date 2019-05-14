package com.example.emaildemo.request;

import lombok.Value;

@Value
public class MailRequest {

    private String name;

    private String to;

    private String from;

    private String subject;

}
