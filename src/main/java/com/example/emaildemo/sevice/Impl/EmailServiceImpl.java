package com.example.emaildemo.sevice.Impl;


import com.example.emaildemo.dto.Email;
import com.example.emaildemo.exception.GeneralSqlException;
import com.example.emaildemo.properties.ModelEmailProperties;
import com.example.emaildemo.repository.EmailRepository;
import com.example.emaildemo.request.MailRequest;
import com.example.emaildemo.response.MailResponse;
import com.example.emaildemo.sevice.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
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

    private static final String NAME = "Name";
    private static final String LOCATION = "location";

    @Override
    public MailResponse sendEmail(MailRequest request) {
        Map<String, Object> model = this.getModelEmail();
        MimeMessage message = sender.createMimeMessage();

        try {
            this.createEmailTemplate(request, model, message);
            sender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Something wrong during send email: %s", e);
            return new MailResponse(String.format("Mail Sending failure: %s", request.getTo()), Boolean.FALSE);
        }

        return new MailResponse(String.format("Mail send to: %s", request.getTo()), Boolean.TRUE);
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

    private void createEmailTemplate(MailRequest request, Map<String, Object> model, MimeMessage message)
            throws IOException, TemplateException, MessagingException {
        Template template = configuration.getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
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
