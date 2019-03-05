package com.training.fullstack;



import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.mentor.controller.MentorController;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = TrainingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MentorControllerTest {

    @LocalServerPort    private int port;
    @Autowired          private TestRestTemplate restTemplate;
    @Autowired          WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;

    @Autowired private MentorRepository mentorRepository;
    @Autowired private SkillRepository skillRepository;

    @Before
    public void setup(){
        mockMvc = ((DefaultMockMvcBuilder) MockMvcBuilders.webAppContextSetup(webApplicationContext)).build();

    }

    private void initDatabase(){
        skillRepository.deleteAll();
        skillRepository.save(new Skill("java","general","nothing"));
        skillRepository.save(new Skill("java 8","java 8","java knowledge"));
        skillRepository.save(new Skill("spring","IOC, spring mvc, dependency injection","java knowledge"));

        mentorRepository.deleteAll();
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
        mentorRepository.save(mentor);

        mentor = new  Mentor("a.a@c.com",15,"http://localhost:1000", "444","mentor iban1","bbb",true);
        mentor.setRegistrationDate(LocalDate.now());
        mentor.setActive(true);
        skill = skillRepository.findByTitle("java").get();
        mentorSkill = new MentorSkill(skill,5,15,2, Double.valueOf(1000));
        mentor.addMentorSkill(mentorSkill);
        skill = skillRepository.findByTitle("spring").get();
        mentorSkill = new MentorSkill(skill,4,4,1, Double.valueOf(1000));
        mentor.addMentorSkill(mentorSkill);

        calendar = new Calendar(
                LocalTime.of(1, 0, 0),
                LocalTime.of(23, 0, 0),
                LocalDate.of(2018,1,1),
                LocalDate.of(2020,3,31));
        mentor.addCalendar( calendar);

        mentorRepository.save(mentor);
    }

        @Test
        public void list_mentors_shourd_return_2_mentors() throws Exception {
            initDatabase();
            mockMvc.perform(MockMvcRequestBuilders.get("/mentor/mentors"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userName").value(CoreMatchers.is("m.m@c.com")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].calendars").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].calendars.[0].startTime").value(CoreMatchers.is("01:00:00")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userName").value(CoreMatchers.is("a.a@c.com")))
            ;
        }

}