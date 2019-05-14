package com.example.emaildemo.controller;

import com.example.emaildemo.request.MailRequest;
import com.example.emaildemo.response.MailResponse;
import com.example.emaildemo.sevice.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public MailResponse sendEmail(@RequestBody MailRequest request) {
        return emailService.sendEmail(request);
    }

}
