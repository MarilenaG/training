package com.training.fullstack.users.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User extends ApplicationMember implements Serializable {




    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;



    @Size(max = 100)
    @Column(name = "contact_number")
    private String contact;




    // Hibernate requires a no-arg constructor
    public User() {

    }

    public User(Long id, @Size(max = 100) String firstName, @Size(max = 100) String lastName, @NotNull @Email @Size(max = 100) String username,
                @NotNull @Size(max = 128) String password, @Size(max = 100) String contact, LocalDate registrationDate,
                @Size(max = 100) String registrationCode, @NotNull Boolean active) {
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
        this.setUserType(UserType.USER);
    }
    public User(@NotNull @Email @Size(max = 100) String userName, @NotNull @Size(max = 128) String password) {
        this.userName = userName;
        this.password = password;
        this.setUserType(UserType.USER);
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



    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}
