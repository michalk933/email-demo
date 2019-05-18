package com.example.emaildemo.sevice.Impl;

import com.example.emaildemo.dto.Recipient;
import com.example.emaildemo.exception.GeneralSqlException;
import com.example.emaildemo.repository.RecipientRepository;
import com.example.emaildemo.sevice.RecipientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipientServiceImpl implements RecipientService {

    private final RecipientRepository recipientRepository;

    @Override
    public List<Recipient> getRecipients() {
        log.info("Get all recipient");
        return Optional
                .ofNullable(recipientRepository.findAll())
                .orElseThrow(() -> new GeneralSqlException("Not found recipients"));
    }

    public List<Recipient> getRecipientsByOwnerId(long ownerId) {
        log.info(String.format("Get all recipient for owner id: %1$s", ownerId));
        return recipientRepository.findByOwners(ownerId)
                .orElseThrow(() -> new GeneralSqlException(String.format("Not found recipients by owner id: %1$s", ownerId)));
    }

    @Override
    public List<Recipient> getRecipientsEmailByEmailId(long emailId) {
        log.info(String.format("Get all recipient for email id: %1$s", emailId));
        return recipientRepository.findByEmails(emailId)
                .orElseThrow(() -> new GeneralSqlException(String.format("Not found recipients by email id: %1$s", emailId)));
    }

    @Override
    public Recipient getRecipient(long recipientId) {
        log.info(String.format("Get recipient for recipient id: %1$s", recipientId));
        return recipientRepository.findById(recipientId)
                .orElseThrow(() -> new GeneralSqlException(String.format("Not found recipient by id: %1$s", recipientId)));
    }

    @Override
    public Recipient create(Recipient newRecipient) {
        log.info(String.format("Save new recipient: %1$s", newRecipient));
        return recipientRepository.save(newRecipient);
    }

    @Override
    public Recipient update(Recipient updateRecipient) {
        log.info(String.format("Update recipient: %1$s", updateRecipient));
        return null;
    }

    @Override
    public void delete(long deleteRecipientId) {
        log.info(String.format("Delete recipient: %1$s", deleteRecipientId));
        recipientRepository.delete(this.getRecipient(deleteRecipientId));
    }

}
