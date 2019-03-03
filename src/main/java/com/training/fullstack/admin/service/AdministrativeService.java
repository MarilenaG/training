package com.training.fullstack.admin.service;

import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.User;

import java.util.NoSuchElementException;
import java.util.Optional;

public class AdministrativeService {
    UserRepository userRepository;
    MentorRepository mentorRepository;

    public AdministrativeService(UserRepository userRepository, MentorRepository mentorRepository) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
    }

    public void blockUser ( long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("User with id" + userId));
        user.setActive( false);
        userRepository.save(user);
    }

    public void blockMentor ( long mentorId){
        Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(()-> new NoSuchElementException("mentor with id" + mentorId));
        mentor.setActive( false);
        mentorRepository.save(mentor);
    }
}