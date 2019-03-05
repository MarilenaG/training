package com.training.fullstack.users.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.fullstack.users.model.User;
import com.training.fullstack.users.model.UserType;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UserRepresentation {
    @JsonProperty private Long id;
    @JsonProperty @NotEmpty @Email   private String userName;
    @JsonProperty private String firstName;
    @JsonProperty private String lastName;
    @JsonProperty private String password;
    @JsonProperty private String contact;
    @JsonProperty private LocalDate registrationDate;
    @JsonProperty private String registrationCode;
    @JsonProperty private Boolean active = true;
    @JsonProperty private UserType userType;

    public UserRepresentation(Long id, String userName, String firstName, String lastName, String password, String contact, LocalDate registrationDate, String registrationCode, Boolean active) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.contact = contact;
        this.registrationDate = registrationDate;
        this.registrationCode = registrationCode;
        this.active = active;
    }

    public UserRepresentation() {
    }
    public User toUser(){
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user ;
    }

    public static UserRepresentation  fromUser( User user){
        UserRepresentation userRepresentaion = new UserRepresentation();
        BeanUtils.copyProperties(user, userRepresentaion);
        return userRepresentaion;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
}
