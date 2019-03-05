package com.training.fullstack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailClientService {

    @Autowired MailFeignClient mailClient;

    public void sendConfirmationSignup(String userName, String confirmationCode){


////        String url = "http://localhost:8090/sendConfirmationSignup";
//        String url = "http://mail/sendConfirmationSignup";
//
//        //adding the query params to the URL
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("userName", userName)
//                .queryParam("confirmationCode", confirmationCode);
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.put(uriBuilder.toUriString(), null);
        mailClient.sendConfirmationSignup(userName,confirmationCode);
    }

    public void sendSimpleMessage(String to, String subject, String text){

////        String url = "http://localhost:8090/sendSimpleMail";
//        String url = "http://mail/sendSimpleMail";
//
//        //adding the query params to the URL
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("to", to)
//                .queryParam("subject", subject)
//                .queryParam("text", text);
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.put(uriBuilder.toUriString(), null);
        mailClient.sendSimpleMail(to, subject, text);
    }
}
