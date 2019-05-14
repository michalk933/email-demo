package com.example.emaildemo.sevice;

import com.example.emaildemo.request.MailRequest;
import com.example.emaildemo.response.MailResponse;


public interface EmailService {

    MailResponse sendEmail(MailRequest request);

}
