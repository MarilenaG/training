package com.training.fullstack.config;

import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.training.infrastructure.TrainingRepository;
import com.training.fullstack.training.model.Training;
import com.training.fullstack.training.model.TrainingStatus;
import com.training.fullstack.users.model.User;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader {

    private UserRepository userRepository;
    private SkillRepository skillRepository;
    private MentorRepository mentorRepository;
    private TrainingRepository trainingRepository;


    public DataLoader(TrainingRepository trainingRepository,UserRepository userRepository, SkillRepository skillRepository, MentorRepository mentorRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.mentorRepository = mentorRepository;
        this.trainingRepository = trainingRepository;
        loadAdmin();
        loadSomeUsers();
        loadSomeSkills();
        loadSomeMentors();
        loadTrainings();
    }


    private void loadAdmin() {
        User admin = new User(0l, "admin", "admin", "admin@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO", "0745444444", LocalDate.now(), "XXXXXXXX", true);
        admin.setUserType(UserType.ADMIN);
        userRepository.save(admin);
    }

    private void loadSomeUsers(){
        User user = new User(0l, "some", "user", "some.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO", "012345", LocalDate.now(), "XXXXXXXX", true);
        user.setUserType(UserType.USER);
        userRepository.save(user);
        user = new User(0l, "other", "user", "other.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO", "012345", LocalDate.now(), "XXXXXXXX", true);
        user.setUserType(UserType.USER);
        userRepository.save(user);
        user = new User(0l, "different", "user", "different.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO", "012345", LocalDate.now(), "XXXXXXXX", true);
        user.setUserType(UserType.USER);
        userRepository.save(user);


    }
    private void loadSomeSkills(){
        skillRepository.save(new Skill("java","general","nothing"));
        skillRepository.save(new Skill("java 8","java 8","java knowledge"));
        skillRepository.save(new Skill("spring","IOC, spring mvc, dependency injection","java knowledge"));

    }
    private void loadSomeMentors(){

        mentorRepository.deleteAll();
        Mentor mentor = new  Mentor("some.mentor@gmail.com",15,"http://localhost:1000", "07672223","mentor iban","aaa",true);
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

        mentor= new  Mentor("other.mentor@gmail.com",15,"http://localhost:2000", "07672223","mentor iban","aaa",true);
        mentor.setRegistrationDate(LocalDate.now());
        mentor.setActive(true);
        skill = skillRepository.findByTitle("java").get();
        mentorSkill = new MentorSkill(skill,5,15,2, Double.valueOf(1000));
        mentor.addMentorSkill(mentorSkill);
        skill = skillRepository.findByTitle("spring").get();
        mentorSkill = new MentorSkill(skill,4,4,1, Double.valueOf(1500));
        mentor.addMentorSkill(mentorSkill);

        calendar = new Calendar(
                LocalTime.of(1, 0, 0),
                LocalTime.of(23, 0, 0),
                LocalDate.of(2018,1,1),
                LocalDate.of(2020,1,1));
        mentor.addCalendar( calendar);

        mentorRepository.save(mentor);
    }

    private void loadTrainings() {

        Training training = new Training(
                mentorRepository.findByUserName("some.mentor@gmail.com").get().getId(),
                userRepository.findByUserName("some.user@training.application").get().getId(),
                skillRepository.findByTitle("java").get().getId(),
                TrainingStatus.ACCEPTED,
                15
                );
        trainingRepository.save(training);
        training = new Training(
                mentorRepository.findByUserName("some.mentor@gmail.com").get().getId(),
                userRepository.findByUserName("some.user@training.application").get().getId(),
                skillRepository.findByTitle("java").get().getId(),
                TrainingStatus.DENIED,
                0
        );
        training.setRejectComments("cannot do it on those dates!");
        trainingRepository.save(training);
        training = new Training(
                mentorRepository.findByUserName("other.mentor@gmail.com").get().getId(),
                userRepository.findByUserName("some.user@training.application").get().getId(),
                skillRepository.findByTitle("spring").get().getId(),
                TrainingStatus.ENDED,
                100
        );
        training.setPayedPercentage(100);
        trainingRepository.save(training);
        training = new Training(
                mentorRepository.findByUserName("other.mentor@gmail.com").get().getId(),
                userRepository.findByUserName("other.user@training.application").get().getId(),
                skillRepository.findByTitle("java").get().getId(),
                TrainingStatus.ENDED,
                88
        );
        training.setPayedPercentage(75);
        trainingRepository.save(training);

    }

}
