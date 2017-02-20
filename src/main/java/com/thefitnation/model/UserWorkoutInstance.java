package com.thefitnation.model;

import java.io.*;
import java.time.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by michael on 2/19/17.
 */
@Entity
@Table(name = "user_workout_instance")
public class UserWorkoutInstance implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created_on;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean isCompleted;

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

    public ZonedDateTime getCreated_on() {
        return created_on;
    }

    public void setCreated_on(ZonedDateTime created_on) {
        this.created_on = created_on;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
