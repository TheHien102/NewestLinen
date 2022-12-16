package com.example.newestlinen.service;

import com.example.newestlinen.storage.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final Environment env;

    public void sendEmail(Account account, String code, String subject) {
        try {
            Context context = new Context();
            Map<String, Object> properties = new HashMap<>();
            properties.put("account_name", account.getFullName());
            properties.put("code", code);
            context.setVariables(properties);
            String html = templateEngine.process("Template", context);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(new InternetAddress(env.getProperty("spring.mail.username"),"Linen À"));
            helper.setTo(account.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            emailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
