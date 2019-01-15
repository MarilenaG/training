package com.training.fullstack.mentor.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    public Mentor( @NotNull @Email @Size(max = 100) String userName, Integer yearsExperience, @URL(protocol = "http") String linkedinUrl, String mobileNumber) {
        this.userName = userName;
        this.yearsExperience = yearsExperience;
        this.linkedinUrl = linkedinUrl;
        this.mobileNumber = mobileNumber;
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

