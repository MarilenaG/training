package com.training.fullstack.users.service;

import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Transactional
public class UserService  {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String userName){
        return userRepository.findByUserName(userName).orElseThrow(()-> new NoSuchElementException("No user with name "+ userName));
    }

    public List<User> listAllUsers(){
        return userRepository.findAll();
    }
}
