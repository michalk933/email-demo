package com.example.emaildemo.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue
    private long ownerId;

    @NotNull
    private String firstName;

    @NotNull
    private String LastName;

    @Email
    private String email;

    private long amountEmail;

    private LocalDate registerDate;

    @OneToMany(mappedBy = "ownerId")
    private Set<com.example.emaildemo.dto.Email> emails;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "owner_recipient",
            joinColumns = {@JoinColumn(name = "owner_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipient_id")}
    )
    private Set<Recipient> recipients = new HashSet<>();

}
