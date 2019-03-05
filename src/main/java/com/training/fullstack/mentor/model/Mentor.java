package com.training.fullstack.mentor.model;

import com.training.fullstack.users.model.ApplicationMember;
import com.training.fullstack.users.model.UserType;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mentors")
public class Mentor extends ApplicationMember implements Serializable {


    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "linkedin_url")
    @URL(protocol = "http")
    private String linkedinUrl;

    @Column (name = "mentor_IBAN")
    private String mentorIBAN;

    @Column(name = "mobile_number")
    private String mobileNumber;


    @OneToMany(mappedBy ="mentor" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MentorSkill> mentorSkills = new HashSet<MentorSkill>();

    @OneToMany(mappedBy ="mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Calendar> calendars =  new HashSet<Calendar>();



    public Mentor(@NotNull @Email @Size(max = 100) String userName,
                  Integer yearsExperience,
                  @URL(protocol = "http") String linkedinUrl,
                  String mobileNumber,
                  String mentorIBAN,
                  String password,
                  Boolean active
    ) {
        this.userName = userName;
        this.yearsExperience = yearsExperience;
        this.linkedinUrl = linkedinUrl;
        this.mobileNumber = mobileNumber;
        this.mentorIBAN = mentorIBAN;
        this.password = password;
        this.active = active;
        this.userType = UserType.MENTOR;
    }

    // No setter, only a getter which returns an immutable collection
    public Set<MentorSkill> getMentorSkills() {
        return Collections.unmodifiableSet(this.mentorSkills);
    }

    public void addMentorSkill(MentorSkill mentorSkill) {
        mentorSkill.setMentor(this);
        this.mentorSkills.add(mentorSkill);
    }
    // No setter, only a getter which returns an immutable collection
    public Set<Calendar> getCalendars() {
        return Collections.unmodifiableSet(this.calendars);
    }

    public void addCalendar(Calendar calendar) {
        calendar.setMentor(this);
        this.calendars.add(calendar);
    }
    public Mentor(Long id) {
        this.id = id;
    }

    public Mentor() {
       super();
       this.mentorSkills = new HashSet<>();
       this.calendars = new HashSet<>();
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMentorIBAN() {
        return mentorIBAN;
    }

    public void setMentorIBAN(String mentorIBAN) {
        this.mentorIBAN = mentorIBAN;
    }
}

