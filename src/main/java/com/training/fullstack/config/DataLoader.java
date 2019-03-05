package com.training.fullstack.config;

import com.training.fullstack.users.model.User;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader {

    private UserRepository userRepository;


    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        loadUsers();
    }

    private void loadUsers() {

        User admin = new User(0l, "admin", "admin", "admin@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO", "0745444444", LocalDate.now(), "XXXXXXXX", true);
        admin.setUserType(UserType.ADMIN);
        userRepository.save(admin);

    }
}
