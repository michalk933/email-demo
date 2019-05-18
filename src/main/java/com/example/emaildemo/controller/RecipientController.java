package com.example.emaildemo.controller;

import com.example.emaildemo.dto.Recipient;
import com.example.emaildemo.sevice.RecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipient")
@RequiredArgsConstructor
public class RecipientController {

    private RecipientService recipientService;

    @GetMapping("/{recipientId}")
    public Recipient getRecipient(@PathVariable("recipientId") long recipientId) {
        return recipientService.getRecipient(recipientId);
    }

    @GetMapping
    public List<Recipient> getRecipients() {
        return recipientService.getRecipients();
    }

    @PostMapping("/create")
    public Recipient create(@RequestBody Recipient recipient) {
        return recipientService.create(recipient);
    }

    @PostMapping("/update")
    public Recipient update(@RequestBody Recipient recipient) {
        return recipientService.update(recipient);
    }

    @DeleteMapping("/{recipientId}")
    public void delete(@PathVariable("recipientId") long recipientId) {
        recipientService.delete(recipientId);
    }

}
