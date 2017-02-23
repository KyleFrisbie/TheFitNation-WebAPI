package com.thefitnation.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by michael on 2/19/17.
 */
@Entity
@Table(name = "gym")
public class Gym implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gymId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    /* JOINS */

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="User_Gym",
            joinColumns=  @JoinColumn(name="userId"),
            inverseJoinColumns= @JoinColumn(name="gymId"))
    Set<Gym> users;

    /* Constructor */

    public Gym() { /* Required by Spring Boot */ }



    /* Mutator */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
