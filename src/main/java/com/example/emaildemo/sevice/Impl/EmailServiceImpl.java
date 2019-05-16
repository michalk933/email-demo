package com.example.emaildemo.sevice.Impl;


import com.example.emaildemo.properties.ModelEmailProperties;
import com.example.emaildemo.request.MailRequest;
import com.example.emaildemo.response.MailResponse;
import com.example.emaildemo.sevice.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
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
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final ModelEmailProperties modelEmailProperties;
    private final JavaMailSender sender;
    private final Configuration configuration;
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
