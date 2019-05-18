package com.example.emaildemo.sevice;

import com.example.emaildemo.dto.Recipient;

import java.util.List;

public interface RecipientService {

    List<Recipient> getRecipients();

    List<Recipient> getRecipientsByOwnerId(long ownerId);

    List<Recipient> getRecipientsEmailByEmailId(long emailId);

    Recipient getRecipient(long recipientId);

    Recipient create(Recipient newRecipient);

    Recipient update(Recipient updateRecipient);

    void delete(long deleteRecipientId);


}
