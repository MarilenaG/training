package com.training.fullstack.users.service;

import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Component
public class UserService  {
    private UserRepository userRepository;
    private MailService mailService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository, MailService mailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signup (User user){

        //generate confirmation code and sava to database
        String confirmationCode = generateUserConfirmationCode();
        user.setRegistrationCode(confirmationCode);
        user.setRegistrationDate( LocalDate.now());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(false);
        System.out.println("Registration code for for user "+user.getUserName()+ " is "+ confirmationCode);
        userRepository.save(user);
        //send confirmation email
        mailService.sendConfirmationSignup(user.getUserName(), confirmationCode);
    }


    private final static String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static int USERCONFIRMATION_CODE_LENGHT = 10 ;

    private String generateUserConfirmationCode (){
        int count = USERCONFIRMATION_CODE_LENGHT;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();

    }

    public boolean confirmUser(String userName, String registrationCode){
        User user = userRepository.findByUserName(userName).orElseThrow(()-> new NoSuchElementException("No user with name "+ userName));
        boolean userConfirmed =  registrationCode.equals(user.getRegistrationCode());
        if (userConfirmed){
            user.setActive(true);
            userRepository.save(user);
        }
        return userConfirmed;

    }

    public User getUser(String userName){
        return userRepository.findByUserName(userName).orElseThrow(()-> new NoSuchElementException("No user with name "+ userName));
    }


}
