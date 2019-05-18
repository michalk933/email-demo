package com.example.emaildemo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MailRequest {

    private String name;

    private String to;

    private String from;

    private String subject;

}
