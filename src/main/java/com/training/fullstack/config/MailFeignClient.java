package com.training.fullstack.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@FeignClient(name = "mail")
public interface MailFeignClient {

    @PutMapping("/sendConfirmationSignup")
    ResponseEntity<Void>  sendConfirmationSignup( @RequestParam  String username, @RequestParam  String confirmationCode);

    @PutMapping("/sendSimpleMail")
    ResponseEntity<Void> sendSimpleMail(@NotNull @RequestParam  String to, @RequestParam(required = false)  String subject, @NotNull @RequestParam  String text) ;




    }