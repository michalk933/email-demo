package com.example.emaildemo.sevice.Impl;


import com.example.emaildemo.dto.Email;
import com.example.emaildemo.dto.MailRequest;
import com.example.emaildemo.dto.Owner;
import com.example.emaildemo.dto.Recipient;
import com.example.emaildemo.exception.GeneralSqlException;
import com.example.emaildemo.exception.SendEmailException;
import com.example.emaildemo.properties.ModelEmailProperties;
import com.example.emaildemo.repository.EmailRepository;
import com.example.emaildemo.sevice.EmailService;
import com.example.emaildemo.sevice.OwnerService;
import com.example.emaildemo.sevice.RecipientService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final ModelEmailProperties modelEmailProperties;
    private final JavaMailSender sender;
    private final Configuration configuration;
    private final EmailRepository emailRepository;


    private final OwnerService ownerService;
    private final RecipientService recipientService;

    private static final String NAME = "Name";
    private static final String LOCATION = "location";

    @Override
    public void send(long ownerId, long emailId) {
        Owner owner = ownerService.getOwner(ownerId);
        List<Recipient> recipients = recipientService.getRecipientsByOwnerId(ownerId);
        Email email = this.getEmail(emailId);
        MimeMessage message = sender.createMimeMessage();

        recipients
                .stream()
                .map(recipient -> MailRequest
                        .builder()
                        .from(owner.getEmail())
                        .to(recipient.getEmail())
                        .subject(email.getDescription())
                        .name(email.getTopic())
                        .build())
                .forEach(mailRequest -> this.createEmailTemplate(mailRequest, message));
    }

    @Override
    public List<Email> getEmails() {
        log.info("Get emails");
        return Optional
                .ofNullable(emailRepository.findAll())
                .orElseThrow(() -> new GeneralSqlException("Not found emails"));
    }

    @Override
    public List<Email> getEmailsByOwnerId(long ownerId) {
        log.info(String.format("Get emails by owner id: %1$s", ownerId));
        return emailRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new GeneralSqlException(String.format("Not found emails by owner id: %1$s", ownerId)));
    }

    @Override
    public List<Email> getEmailsyRecipientId(long recipientId) {
        log.info(String.format("Get emails by recipient id: %1$s", recipientId));
        return emailRepository.findByRecipients(recipientId)
                .orElseThrow(() -> new GeneralSqlException(String.format("Not found emails by recipient id: %1$s", recipientId)));
    }

    @Override
    public Email getEmail(long emailId) {
        log.info(String.format("Get email: %1$s", emailId));
        return emailRepository.findById(emailId)
                .orElseThrow(() -> new GeneralSqlException(String.format("Not found email by id: %1$s", emailId)));
    }

    @Override
    public void delete(long emailId) {
        log.info(String.format("Delete email: %1$s", emailId));
        emailRepository.delete(this.getEmail(emailId));
    }

    @Override
    public Email update(Email updateEmail) {
        log.info(String.format("Update email: %1$s", updateEmail));
        return null;
    }

    @Override
    public Email create(Email newEmail) {
        log.info(String.format("Save email: %1$s", newEmail));
        return emailRepository.save(newEmail);
    }

    private void createEmailTemplate(MailRequest request, MimeMessage message) {
        try {
            Map<String, Object> model = this.getModelEmail();
            String html = this.getTemplateEmail(model);
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            this.buildMessageToSend(request, html, helper);
        } catch (TemplateException e) {
            throw new SendEmailException(String.format("Something wrong during make template email: %1$s", request));
        } catch (IOException | MessagingException e) {
            throw new SendEmailException(String.format("Something wrong during send email: %1$s", request));
        }
    }

    private String getTemplateEmail(Map<String, Object> model) throws IOException, TemplateException {
        Template template = configuration.getTemplate("email-template.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

    private void buildMessageToSend(MailRequest request, String html, MimeMessageHelper helper) throws MessagingException {
//        helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
        helper.setTo(request.getTo());
        helper.setText(html, true);
        helper.setSubject(request.getSubject());
        helper.setFrom(request.getFrom());
    }

    private Map<String, Object> getModelEmail() {
        Map<String, Object> model = new HashMap<>();
        model.put(NAME, modelEmailProperties.getName());
        model.put(LOCATION, modelEmailProperties.getLocation());
        return model;
    }


}
