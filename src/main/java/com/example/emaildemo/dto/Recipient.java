package com.example.emaildemo.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "recipient")
public class Recipient {

    @Id
    @GeneratedValue
    private long recipientId;

    private String name;

    @Email
    private String email;

    private LocalDate registerDate;

    @ManyToMany(mappedBy = "recipients")
    Set<Owner> owners = new HashSet<>();

    @ManyToMany(mappedBy = "recipients")
    private List<com.example.emaildemo.dto.Email> emails = new ArrayList<>();

}
