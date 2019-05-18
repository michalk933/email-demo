package com.example.emaildemo.sevice;

import com.example.emaildemo.dto.Email;

import java.util.List;


public interface EmailService {

    void send(long ownerId, long emailId);

    List<Email> getEmails();

    List<Email> getEmailsByOwnerId(long ownerId);

    List<Email> getEmailsyRecipientId(long recipientId);

    Email getEmail(long emailId);

    void delete(long emailId);

    Email update(Email updateEmail);

    Email create(Email newEmail);

}
