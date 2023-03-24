package com.climatechange.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;
public class UserDTO {
    private Long id;

    @Size(min = 5, max = 20)
    private String firstName;

    @Size(min = 1, max = 15)
    private String lastName;

    @NotNull @Size(min = 4, max = 20)
    @Column(unique = true)
    private String username;

    @NotNull @Email
    @Column(unique = true)
    private String email;

    @Size(min = 5, max = 20)
    private String country;

    @Size(min = 5, max = 20)
    private String city;

    @Size(min = 5, max = 500)
    public String aboutMe;

    public UserDTO() {
    }
    public UserDTO(Long id, String firstName, String lastName, String username, String email, String country, String city, String aboutMe) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.country = country;
        this.city = city;
        this.aboutMe = aboutMe;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAboutMe() {
        return aboutMe;
    }
}