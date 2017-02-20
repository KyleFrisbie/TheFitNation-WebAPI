package com.thefitnation.model;

import java.io.*;
import java.time.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by michael on 2/19/17.
 */
@Entity
@Table(name = "user_weight")
public class UserWeight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "weight_date", nullable = false)
    private LocalDate weight_date;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Float weight;

    @ManyToOne
    @NotNull
    private UserDemographic userDemographic;


    /* Joins */


    /* Mutator */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWeight_date() {
        return weight_date;
    }

    public void setWeight_date(LocalDate weight_date) {
        this.weight_date = weight_date;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public UserDemographic getUserDemographic() {
        return userDemographic;
    }

    public void setUserDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
    }
}
