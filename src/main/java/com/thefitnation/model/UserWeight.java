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
    private Long weightId;

    @NotNull
    @Column(name = "weight_date", nullable = false)
    private LocalDate weight_date;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Float weight;


    /* Joins */


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    /* Constructor */

    protected UserWeight() { /* Required by Spring Boot */ }

    /* Mutator */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getWeightId() {
        return weightId;
    }

    public void setWeightId(Long weightId) {
        this.weightId = weightId;
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

}
