package com.training.fullstack.users.service;

import com.training.fullstack.users.model.UserPrincipal;
import com.training.fullstack.users.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserPrincipalService implements UserDetailsService {
    private UserRepository userRepository;

    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new UserPrincipal(userRepository.findByUserName(userName));
    }
}
