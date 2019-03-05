package com.training.fullstack;

import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.training.infrastructure.TrainingRepository;
import com.training.fullstack.training.model.Training;
import com.training.fullstack.training.model.TrainingStatus;
import com.training.fullstack.training.service.TrainingService;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.User;
import org.h2.tools.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"spring.h2.console.enabled=true"})
@Transactional
public class TrainingServiceTest {

    @Autowired    TrainingService trainingService;
    @Autowired    UserRepository userRepository;
    @Autowired    MentorRepository mentorRepository;
    @Autowired    SkillRepository skillRepository;
    @Autowired    TrainingRepository trainingRepository;

    @Before
    public void initDatabase(){
//        try {
//            Server webServer = Server.createWebServer("-web",
//                    "-webAllowOthers", "-webPort", "8082");
//            webServer.start();
//        } catch (SQLException ee) {
//            ee.printStackTrace();
//        }

        userRepository.deleteAll();
        User user = new User("marilena_matei@yahoo.com", "Marilena", "Gibson","parola");
        user.setActive(true);
        userRepository.save(user);

        skillRepository.deleteAll();

        skillRepository.save(new Skill("java","general","nothing"));
        skillRepository.save(new Skill("java 8","java 8","java knowledge"));
        skillRepository.save(new Skill("spring","IOC, spring mvc, dependency injection","java knowledge"));

        mentorRepository.deleteAll();
        Mentor mentor = new  Mentor("marilenam@gmail.com",15,"http://localhost:1000", "07672223","mentor iban","aaa",true);
        mentor.setRegistrationDate(LocalDate.now());
        mentor.setActive(true);
        Skill skill = skillRepository.findByTitle("java").get();
        MentorSkill mentorSkill = new MentorSkill(skill,5,15,2, Double.valueOf(1000));
        mentor.addMentorSkill(mentorSkill);
        skill = skillRepository.findByTitle("java 8").get();
        mentorSkill = new MentorSkill(skill,4,4,1, Double.valueOf(1500));
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

    }
    @Test
    public void test_full_workflow_of_training(){
        Skill skill = skillRepository.findByTitle("java 8").get();
        Training proposed = trainingService.proposeTraining("marilena_matei@yahoo.com",
                "marilenam@gmail.com",
                skill.getId().longValue(),
                LocalDate.of(2019,3,15),
                LocalDate.of(2019,3,25),
                LocalTime.of(14, 0, 0),
                LocalTime.of(16, 0, 0)
                );
        assert (trainingRepository.findById(proposed.getId()).isPresent());
        assert (userRepository.findById(proposed.getUserId()).isPresent());
        assert (trainingRepository.findById(proposed.getId()).get().getStatus().equals(TrainingStatus.PROPOSED));

        trainingService.acceptTraining(proposed.getId());
        assert (trainingRepository.findById(proposed.getId()).get().getStatus().equals(TrainingStatus.ACCEPTED));

        trainingService.finalizeTraining(proposed.getId());
        assert (trainingRepository.findById(proposed.getId()).get().getStatus().equals(TrainingStatus.AWAITING_PAYMENT));

        trainingService.payTraining(proposed.getId());
        assert (trainingRepository.findById(proposed.getId()).get().getStatus().equals(TrainingStatus.STARTED));

        trainingService.updateProgress(proposed.getId(),33);
        assert (trainingRepository.findById(proposed.getId()).get().getStatus().equals(TrainingStatus.STARTED));
        assert (trainingRepository.findById(proposed.getId()).get().getPayedPercentage()==25);

        trainingService.updateProgress(proposed.getId(),88);
        assert (trainingRepository.findById(proposed.getId()).get().getStatus().equals(TrainingStatus.STARTED));
        assert (trainingRepository.findById(proposed.getId()).get().getPayedPercentage()==75);

        trainingService.updateProgress(proposed.getId(),100);
        assert (trainingRepository.findById(proposed.getId()).get().getStatus().equals(TrainingStatus.ENDED));
        assert (trainingRepository.findById(proposed.getId()).get().getPayedPercentage()==100);


    }
}
