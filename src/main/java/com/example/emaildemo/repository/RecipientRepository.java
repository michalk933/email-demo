package com.example.emaildemo.repository;

import com.example.emaildemo.dto.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

    Optional<List<Recipient>> findByOwners(long owners);

    Optional<List<Recipient>> findByEmails(long emails);

}
