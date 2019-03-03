package com.training.fullstack.mentor.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "mentors")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String userName;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "linkedin_url")
    @URL(protocol = "http")
    private String linkedinUrl;

    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "register_datetime")
    private LocalDate registrationDate;

    @Size(max = 100)
    @Column(name = "registration_code")
    private String registrationCode;

    @OneToMany(mappedBy ="mentor")
    private Set<MentorSkill> mentorSkills;

    @OneToMany(mappedBy ="mentor")
    private Set<Calendar> calendars;

    @NotNull
    private Boolean active = true;

    public Mentor(@NotNull @Email @Size(max = 100) String userName, Integer yearsExperience, @URL(protocol = "http") String linkedinUrl, String mobileNumber, LocalDate registrationDate, @Size(max = 100) String registrationCode) {
        this.userName = userName;
        this.yearsExperience = yearsExperience;
        this.linkedinUrl = linkedinUrl;
        this.mobileNumber = mobileNumber;
        this.registrationDate = registrationDate;
        this.registrationCode = registrationCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
}

