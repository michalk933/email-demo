package com.example.emaildemo.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue
    private long emailId;

    @NotBlank
    private String topic;

    @NotBlank
    private String description;

    private LocalDateTime createDate;

    private LocalDateTime sendDate;

    private long amountSend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner ownerId;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "email_recipient",
            joinColumns = {@JoinColumn(name = "email_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipient_id")}
    )
    private List<Recipient> recipients = new ArrayList<>();

}
