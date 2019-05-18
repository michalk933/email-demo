package com.example.emaildemo.controller;

import com.example.emaildemo.dto.Email;
import com.example.emaildemo.request.MailRequest;
import com.example.emaildemo.response.MailResponse;
import com.example.emaildemo.sevice.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public MailResponse sendEmail(@RequestBody MailRequest request) {
        return emailService.sendEmail(request);
    }

    @GetMapping("/{emailId}")
    public Email getEmail(@PathVariable("emailId") long emailId) {
        return emailService.getEmail(emailId);
    }

    @GetMapping
    public List<Email> getEmails() {
        return emailService.getEmails();
    }

    @PostMapping("/create")
    public Email createEmail(@RequestBody Email email) {
        return emailService.create(email);
    }

    @PostMapping("/update")
    public Email updateEmail(@RequestBody Email email) {
        return emailService.update(email);
    }

    @DeleteMapping("/{emailId}")
    public void delete(@PathVariable("emailId") long emailId) {
        emailService.delete(emailId);
    }


}
