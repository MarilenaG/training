package com.training.fullstack.users.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String userName;

    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Size(max = 128)
    private String password;

    @Size(max = 100)
    @Column(name = "contact_number")
    private String contact;


    @Column(name = "register_datetime")
    private LocalDate registrationDate;

    @Size(max = 100)
    @Column(name = "registration_code")
    private String registrationCode;


    @NotNull
    private Boolean active = true;

    // Hibernate requires a no-arg constructor
    public User() {

    }

    public User(Long id, @Size(max = 100) String firstName, @Size(max = 100) String lastName, @NotNull @Email @Size(max = 100) String username,
                @NotNull @Size(max = 128) String password, @Size(max = 100) String contact, LocalDate registrationDate, @Size(max = 100) String registrationCode, @NotNull Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.password = password;
        this.contact = contact;
        this.registrationDate = registrationDate;
        this.registrationCode = registrationCode;
        this.active = active;
    }

    public User(@NotNull @Email @Size(max = 100) String userName, @Size(max = 100) String firstName, @Size(max = 100) String lastName, @NotNull @Size(max = 128) String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
    public User(@NotNull @Email @Size(max = 100) String userName, @NotNull @Size(max = 128) String password) {
        this.userName = userName;
        this.password = password;
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

    public void setUserName(String username) {
        this.userName = username;
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
