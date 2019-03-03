package com.training.fullstack;


import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.service.MentorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MentorServiceTest {

    @Autowired
    MentorService mentorService;
    @Autowired
    MentorRepository mentorRepository;

    public MentorServiceTest(MentorService mentorService, MentorRepository mentorRepository) {
        this.mentorService = mentorService;
        this.mentorRepository = mentorRepository;
    }

}
