package com.example.newestlinen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@Slf4j
public class CommonAsyncService {

    @Autowired
    private EmailService emailService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("threadPoolExecutor")
    private TaskExecutor taskExecutor;

    @Async
    public void sendEmail(String email, String msg, String subject, boolean html){
        Runnable task3 = () -> {
            try {
                emailService.sendEmail(email,msg,subject,html);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        };
        taskExecutor.execute(task3);
    }

    @Async
    public void pushToFirebase(String url, String data){
        Runnable task3 = () -> {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> entity = new HttpEntity<>(data, headers);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
                log.info("callFirebase>>Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
            } catch (Exception ex) {
                log.error("callFirebase>>error: " + ex.getMessage(), ex);
            }
        };
        taskExecutor.execute(task3);
    }
}
