package com.climatechange.family;

import javax.persistence.*;
import java.util.*;
import com.climatechange.user.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Family {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<User> familyMembers;


    // Gets total emission of family members
    public Double getFamilyEmissions() {
        double carbonVal = 0;
        for (User user : familyMembers) {
            carbonVal +=  user.getLatestRecord();
        }
        return carbonVal;
    }

}
