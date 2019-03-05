package com.training.fullstack;

import com.training.fullstack.config.MailClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailClientServiceTest {


    @LocalServerPort
    private int localServerPort;
    @Autowired
    private MailClientService mailClientService;


    @Test
    public void sendSimpleMessage(){
        mailClientService.sendSimpleMessage("marilena_matei@yahoo.com","test", "test");
    }
}
