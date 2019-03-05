package com.training.fullstack;

import com.training.fullstack.users.model.User;
import com.training.fullstack.users.infrastructure.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUserRepository_whenSaveAndRetreiveUser_thenOK() {
        userRepository.deleteAll();
        User someUser = userRepository.save(new User("someone@company.com","some", "user","xxxxx"));
        someUser.setActive(false);
        userRepository.saveAndFlush(someUser);
        User foundUser = userRepository.findByUserName(someUser.getUserName()).get();

        assert (foundUser!= null);
        assert (foundUser.getId().equals(someUser.getId()));
        assert (!foundUser.getActive());
    }
}