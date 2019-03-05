package com.training.fullstack.auth;

import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.users.model.ApplicationMember;
import com.training.fullstack.users.model.User;
import com.training.fullstack.auth.UserPrincipal;
import com.training.fullstack.users.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class UserPrincipalService implements UserDetailsService {
    private UserRepository userRepository;
    private MentorRepository mentorRepository;

    public UserPrincipalService(UserRepository userRepository, MentorRepository mentorRepository) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Mentor mentor = mentorRepository.findByUserName(userName).orElse(null);
        User user = userRepository.findByUserName(userName).orElse(null);

        ApplicationMember userPrincipal = mentor!=null? mentor:user ;

        if (userPrincipal == null ){
            throw new UsernameNotFoundException("No user or mentor with name " + userName);
        }

        return new UserPrincipal(userPrincipal);
    }

}
