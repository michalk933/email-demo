package com.example.emaildemo.repository;

import com.example.emaildemo.dto.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<List<Email>> findByOwnerId(long ownerId);

    Optional<List<Email>> findByRecipients(long recipients);

}
