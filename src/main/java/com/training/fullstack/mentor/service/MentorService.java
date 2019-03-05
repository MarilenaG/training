package com.training.fullstack.mentor.service;

import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.infrastructure.MentorSpecification;
import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Transactional
public class MentorService  {
    private MentorRepository mentorRepository;
    private SkillRepository skillRepository;

    public MentorService(MentorRepository mentorRepository, SkillRepository skillRepository) {
        this.mentorRepository = mentorRepository;
        this.skillRepository = skillRepository;
    }

    public List<Mentor> searchMentorByTimeAndTechnology (String technology, LocalTime desiredTime, LocalDate desiredDate){
        if (technology!=null && desiredTime!= null){
            return  mentorRepository.findAll(MentorSpecification.hasSkillNamedAndIsAvailableOn(technology,desiredTime, desiredDate));
        } else {
            if (technology!=null){
                return  mentorRepository.findAll(MentorSpecification.hasSkillNamed(technology));
            } else {
                return mentorRepository.findAll(MentorSpecification.isAvailableOn(desiredTime, desiredDate));
            }
        }
    }

    public Mentor addMentorCalendar(String mentorUserName , LocalTime startTime, LocalTime endTime, LocalDate startDate , LocalDate endDate) {
        Mentor subject = mentorRepository.findByUserName(mentorUserName).orElseThrow(()->new NoSuchElementException("No mentor with name " + mentorUserName));;
        subject.addCalendar(new Calendar(startTime,endTime,startDate,endDate));
        mentorRepository.save(subject);
        return  subject;
    }

    public Mentor addMentorSkill ( String mentorUserName, String skillName, Integer skillRating, Integer yearsExperience, Integer trainingsDelivered, Double fee) {
        Mentor mentor = mentorRepository.findByUserName(mentorUserName).orElseThrow(()->new NoSuchElementException("No mentor with name " + mentorUserName));
        Skill skill = skillRepository.findByTitle(skillName).orElseThrow(()-> new NoSuchElementException("No skill with title " + skillName));
        mentor.addMentorSkill( new MentorSkill(skill,skillRating,yearsExperience,trainingsDelivered, fee));
        mentorRepository.save(mentor);
        return mentor;
    }

    public Mentor updateMentorProfile (String mentorUserName,Integer yearsExperience,String linkedinUrl, String mobileNumber){
        Mentor mentor = mentorRepository.findByUserName(mentorUserName).orElseThrow(()->new NoSuchElementException("No mentor with name " + mentorUserName));
        mentor.setYearsExperience(yearsExperience);
        mentor.setMobileNumber(mobileNumber);
        mentor.setLinkedinUrl(linkedinUrl);
        mentorRepository.save(mentor);
        return mentor;
    }



    public Mentor getMentor(String mentorName){
        return mentorRepository.findByUserName(mentorName).orElseThrow(()-> new NoSuchElementException("No mentor with name "+ mentorName));
    }

    public List<Mentor> listAllMentors(){
        return mentorRepository.findAll();
    }
}

