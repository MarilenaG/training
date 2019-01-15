package com.training.fullstack;

import com.training.fullstack.users.model.User;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void signup_should_create_user_inactive(){
        User userToSignup = new User("marilena_matei@yahoo.com", "Marilena", "Gibson","parola");
        userService.signup(userToSignup);
        User savedUser = userRepository.findByUserName("marilena_matei@yahoo.com");
        assert (!savedUser.getActive());
        userRepository.delete(userToSignup);
    }

    @Test
    public void confirmation_with_incorect_code_should_fail(){
        User userToSignup = new User("marilena_matei@yahoo.com", "Marilena", "Gibson","parola");
        userService.signup(userToSignup);
        User savedUser = userRepository.findByUserName("marilena_matei@yahoo.com");

        assert (!userService.confirmUser("marilena_matei@yahoo.com","xxx"));
        userRepository.delete(userToSignup);
    }

    @Test
    public void confirmation_with_incorect_code_should_succeed(){
        User userToSignup = new User("marilena_matei@yahoo.com", "Marilena", "Gibson","parola");
        userService.signup(userToSignup);
        User savedUser = userRepository.findByUserName("marilena_matei@yahoo.com");
        assert (userService.confirmUser("marilena_matei@yahoo.com",userToSignup.getRegistrationCode()));
        userRepository.delete(userToSignup);
    }
}
