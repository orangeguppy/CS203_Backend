package com.climatechange.carbon;


import java.sql.Timestamp;
import javax.persistence.*;
import com.climatechange.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "carbon_records")
public class CarbonRecord implements Comparable<CarbonRecord>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp datetime;
    private Double emission;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @Override
    public int compareTo(CarbonRecord o) {
        return (int)(this.emission - o.getEmission());
    }

    // public CarbonRecord(Timestamp datetime, Double carbonEmission, User user) {
    //     this.datetime = datetime;
    //     this.carbonEmission = carbonEmission;
    //     this.user = user;
    // }

    // public String toString() {
    //     return user + " has value - " + emission;
    // }

}