package com.training.fullstack;


import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.infrastructure.MentorSpecification;
import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.mentor.service.MentorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MentorRepositoryTest {


    @Autowired
    MentorRepository mentorRepository;
    @Autowired
    SkillRepository skillRepository;



    @Before
    public void initSkillRepository (){
        skillRepository.deleteAll();

        skillRepository.save(new Skill("java","general","nothing"));
        skillRepository.save(new Skill("java 8","java 8","java knowledge"));
        skillRepository.save(new Skill("spring","IOC, spring mvc, dependency injection","java knowledge"));

    }

    private Mentor constructMentor(){

        Mentor mentor = new  Mentor("m.m@c.com",15,"http://localhost:1000", "07672223","mentor iban","aaa",true);
        mentor.setRegistrationDate(LocalDate.now());
        mentor.setActive(true);
        Skill skill = skillRepository.findByTitle("java").get();
        MentorSkill mentorSkill = new MentorSkill(skill,5,15,2, Double.valueOf(1000));
        mentor.addMentorSkill(mentorSkill);
        skill = skillRepository.findByTitle("java 8").get();
        mentorSkill = new MentorSkill(skill,4,4,1, Double.valueOf(1000));
        mentor.addMentorSkill(mentorSkill);

        Calendar calendar = new Calendar(
                LocalTime.of(1, 0, 0),
                LocalTime.of(23, 0, 0),
                LocalDate.of(2018,1,1),
                LocalDate.of(2019,3,31));
        mentor.addCalendar( calendar);
        //15 days vacation  april 1- april 15
        calendar = new Calendar(
                LocalTime.of(1, 0, 0),
                LocalTime.of(23, 0, 0),
                LocalDate.of(2019,4,15),
                LocalDate.of(2019,12,31));
        mentor.addCalendar( calendar );
        return mentor;
    }

    @Test
    public void add_non_existing_mentor_should_work(){
        mentorRepository.deleteAll();
        Mentor mentor = constructMentor();
        mentorRepository.save(mentor);
        assert (mentorRepository.findAll().size()==1);
    }
    @Test
    public void find_by_existing_available_time_should_return_mentor(){
        mentorRepository.deleteAll();
        Mentor mentor = constructMentor();
        mentorRepository.save(mentor);

        assert (mentorRepository.findAll(MentorSpecification.isAvailableOn(LocalTime.now(), LocalDate.now())).size()>0);
    }
    @Test
    public void find_by_existing_skill_should_return_mentor(){
        mentorRepository.deleteAll();
        Mentor mentor = constructMentor();
        mentorRepository.save(mentor);

        assert (mentorRepository.findAll(MentorSpecification.hasSkillNamed("java")).size()>0);
    }
}
