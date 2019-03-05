package com.training.fullstack.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class ApplicationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    protected Long id;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    @JsonProperty
    protected String userName;

    @NotNull
    @Size(max = 128)
    @JsonProperty
    protected String password;


    @Column(name = "register_datetime")
    @JsonProperty
    protected LocalDate registrationDate;

    @Size(max = 100)
    @Column(name = "registration_code")
    @JsonProperty
    protected String registrationCode;

    @NotNull
    @JsonProperty
    protected Boolean active = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @JsonProperty
    protected UserType userType;

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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


}
