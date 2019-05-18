package com.example.emaildemo.sevice;

import com.example.emaildemo.dto.Email;
import com.example.emaildemo.request.MailRequest;
import com.example.emaildemo.response.MailResponse;

import java.util.List;


public interface EmailService {

    MailResponse sendEmail(MailRequest request);

    List<Email> getEmails();

    List<Email> getEmailsByOwnerId(long ownerId);

    List<Email> getEmailsyRecipientId(long recipientId);

    Email getEmail(long emailId);

    void delete(long emailId);

    Email update(Email updateEmail);

    Email create(Email newEmail);

}
