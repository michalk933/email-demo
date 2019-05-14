package com.example.emaildemo.sevice.Impl;


import com.example.emaildemo.request.MailRequest;
import com.example.emaildemo.response.MailResponse;
import com.example.emaildemo.sevice.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final CreateModelServiceImpl createModelService;
    private final JavaMailSender sender;
    private final Configuration configuration;

    @Override
    public MailResponse sendEmail(MailRequest request) {
        Map<String, Object> model = createModelService.getModelEmail();

        MailResponse response = new MailResponse();
        MimeMessage message = sender.createMimeMessage();

        try {


            createEmailTemplate(request, model, message);

            sender.send(message);

            response.setMessage("Mail send to: " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure: " + e.getMessage());
            response.setStatus(Boolean.FALSE);
        }

        return response;
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


}
