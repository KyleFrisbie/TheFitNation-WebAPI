package com.thefitnation.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * <p></p>
 * Created by michael on 2/19/17.
 * @author michael menard
 * @version 0.1.0
 * @since 2/19/17
 */
@Entity
@Table(name = "gym")
public class Gym implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gym_id")
    private Long gymId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;





    /* JOINS */

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="User_Gym",
            joinColumns=  @JoinColumn(name="userId"),
            inverseJoinColumns= @JoinColumn(name="gymId"))
    private Set<Gym> users;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location gymLocation;

    /* Constructor */

    public Gym() { /* Required by Spring Boot */ }

    public Gym(String name){
        this.name = name;
    }


    /* Mutator */

    /**
     *
     * @return
     */
    public Set<Gym> getUsers() {
        return users;
    }

    /**
     *
     * @param users
     */
    public void setUsers(Set<Gym> users) {
        this.users = users;
    }

    public Location getGymLocation() {
        return gymLocation;
    }

    public void setGymLocation(Location gymLocation) {
        this.gymLocation = gymLocation;
    }

    /**
     *
     * @return
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     *
     * @return
     */
    public Long getGymId() {
        return gymId;
    }

    /**
     *
     * @param gymId
     */
    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
