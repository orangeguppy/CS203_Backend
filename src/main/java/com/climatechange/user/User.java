package com.climatechange.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.*;
import com.climatechange.family.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.climatechange.carbon.CarbonRecord;

@Entity
public class User implements UserDetails {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CarbonRecord> carbonEmissions;

    @ManyToOne
    @JoinColumn(name = "family_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private Family family;

    @Size(min = 5, max = 20)
    private String firstName;

    @Size(min = 1, max = 15)
    private String lastName;

    @NotNull @Size(min = 4, max = 20)
    @Column(unique = true)
    private String username;

    @NotNull @Size(min = 6, max = 127)
    private String password;

    @NotNull @Email
    @Column(unique = true)
    private String email;

    @Size(min = 5, max = 20)
    private String country;

    @Size(min = 5, max = 20)
    private String city;

    @Size(min = 5, max = 500)
    public String aboutMe;

    private double latestCarbonScore;

    public User() {
    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.aboutMe = "Placeholder description.";
        latestCarbonScore = 0;
    }

    public User(Long id, String firstName, String username, String password, String email) {
        this.id = id;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
        this.email = email;
        latestCarbonScore = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public boolean hasFamily() {
        return family == null;
    }

    public List<CarbonRecord> getRecords() {
        return carbonEmissions;
    }

    public void setRecords(List<CarbonRecord> carbonEmissions) {
        this.carbonEmissions = carbonEmissions;
    }

    public Double getLatestRecord() {
            double result = 0;
            if (!carbonEmissions.isEmpty()) {
                result = carbonEmissions.get(carbonEmissions.size() - 1).getEmission();
                setLatestCarbonScore(result);
            }
        return result;
    }

    public double getLatestCarbonScore() {
        return latestCarbonScore;
    }

    public void setLatestCarbonScore(double latestCarbonScore) {
        this.latestCarbonScore = latestCarbonScore;
    }
}