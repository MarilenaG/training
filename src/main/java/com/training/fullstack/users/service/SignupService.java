package com.training.fullstack.users.service;

import com.training.fullstack.config.MailClientService;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.ApplicationMember;
import com.training.fullstack.users.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@Component
@Transactional
public class SignupService {

    private MentorRepository mentorRepository;
    private UserRepository userRepository;
    private MailClientService mailClientService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RestTemplate restTemplate;


    public SignupService(MentorRepository mentorRepository, UserRepository userRepository, MailClientService mailClientService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.mailClientService = mailClientService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signup ( ApplicationMember applicationMember, boolean isMentor){

        //generate confirmation code and sava to database
        String confirmationCode = generateUserConfirmationCode();
        applicationMember.setRegistrationCode(confirmationCode);
        applicationMember.setRegistrationDate( LocalDate.now());
        applicationMember.setPassword(bCryptPasswordEncoder.encode(applicationMember.getPassword()));
        applicationMember.setActive(false);
        System.out.println("Registration code for for user "+applicationMember.getUserName()+ " is "+ confirmationCode);
        if (isMentor){
            mentorRepository.save((Mentor) applicationMember);
        } else {
            userRepository.save((User) applicationMember);
        }

        //send confirmation email
        mailClientService.sendConfirmationSignup(applicationMember.getUserName(), confirmationCode);
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

    public boolean confirmApplicationMember(String userName, String registrationCode, boolean isMentor){
        boolean applicationMemberconfirmed = false;

        if (isMentor) {
            Mentor mentor = mentorRepository.findByUserName(userName).orElseThrow(() -> new NoSuchElementException("No user with name " + userName));
            applicationMemberconfirmed = registrationCode.equals(mentor.getRegistrationCode());
            if (applicationMemberconfirmed) {
                mentor.setActive(true);
                mentorRepository.save(mentor);
            }
        } else {
            User user = userRepository.findByUserName(userName).orElseThrow(() -> new NoSuchElementException("No user with name " + userName));
            applicationMemberconfirmed = registrationCode.equals(user.getRegistrationCode());
            if (applicationMemberconfirmed) {
                user.setActive(true);
                userRepository.save(user);
            }
        }
        return applicationMemberconfirmed;

    }
}
