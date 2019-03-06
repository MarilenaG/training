package com.training.fullstack.config;


import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.ApplicationMember;
import com.training.fullstack.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserPrincipalController {

    @Autowired    MentorRepository mentorRepository;
    @Autowired    UserRepository userRepository;
    @PutMapping("/getUserPrincipal")
    public ResponseEntity<ApplicationMember> getUserPrincipal(@RequestParam String userName) {
        Mentor mentor = mentorRepository.findByUserName(userName).orElse(null);
        User user = userRepository.findByUserName(userName).orElse(null);

        ApplicationMember userPrincipal = mentor!=null? mentor:user ;

        if (userPrincipal == null ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return new ResponseEntity(userPrincipal, HttpStatus.OK);
    }
}
