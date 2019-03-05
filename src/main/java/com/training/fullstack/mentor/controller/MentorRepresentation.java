package com.training.fullstack.mentor.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.users.model.UserType;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MentorRepresentation {
    @JsonProperty  Long id;
    @JsonProperty @NotEmpty @Email  public  String userName;
    @JsonProperty public String password;
    @JsonProperty public LocalDate registrationDate;
    @JsonProperty public String registrationCode;
    @JsonProperty public Boolean active = true;
    @JsonProperty public Integer yearsExperience;
    @JsonProperty public String linkedinUrl;
    @JsonProperty public String mentorIBAN;
    @JsonProperty public String mobileNumber;
    @JsonProperty public List<Calendar> calendars ;
    @JsonProperty public List<MentorSkillRepresentation> skils;
    @JsonProperty private UserType userType;


    static class MentorSkillRepresentation{
        @JsonProperty public Long id;
        @JsonProperty public Skill skill;
        @JsonProperty public Integer skillRating;
        @JsonProperty public Integer yearsExperience;
        @JsonProperty public Integer trainingsDelivered;
        @JsonProperty public Double fee;


        public MentorSkill toMentorSkill(){
            MentorSkill mentorSkill = new MentorSkill();
            BeanUtils.copyProperties(this, mentorSkill);
            return mentorSkill ;
        }

        public static MentorSkillRepresentation fromMentorSkill(MentorSkill mentorSkill){
            MentorSkillRepresentation mentorSkillRepresentaion = new MentorSkillRepresentation();
            BeanUtils.copyProperties(mentorSkill, mentorSkillRepresentaion);
            return mentorSkillRepresentaion;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Skill getSkill() {
            return skill;
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }

        public Integer getSkillRating() {
            return skillRating;
        }

        public void setSkillRating(Integer skillRating) {
            this.skillRating = skillRating;
        }

        public Integer getYearsExperience() {
            return yearsExperience;
        }

        public void setYearsExperience(Integer yearsExperience) {
            this.yearsExperience = yearsExperience;
        }

        public Integer getTrainingsDelivered() {
            return trainingsDelivered;
        }

        public void setTrainingsDelivered(Integer trainingsDelivered) {
            this.trainingsDelivered = trainingsDelivered;
        }

        public Double getFee() {
            return fee;
        }

        public void setFee(Double fee) {
            this.fee = fee;
        }
    }


    public Mentor toMentor(){
        Mentor mentor = new Mentor();
        BeanUtils.copyProperties(this, mentor);
        this.skils.stream().map(MentorSkillRepresentation::toMentorSkill).forEach((ms)-> mentor.addMentorSkill(ms));
        this.calendars.forEach(c->mentor.addCalendar(c));
        return mentor ;
    }

    public static MentorRepresentation fromMentor(Mentor mentor){
        MentorRepresentation mentorRepresentaion = new MentorRepresentation();
        BeanUtils.copyProperties(mentor, mentorRepresentaion);
        mentorRepresentaion.skils =mentor.getMentorSkills().stream().map(MentorSkillRepresentation::fromMentorSkill).collect(Collectors.toList());
        mentorRepresentaion.calendars = mentor.getCalendars().stream().collect(Collectors.toList());
        return mentorRepresentaion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getMentorIBAN() {
        return mentorIBAN;
    }

    public void setMentorIBAN(String mentorIBAN) {
        this.mentorIBAN = mentorIBAN;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
