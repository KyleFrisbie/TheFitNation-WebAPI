package com.thefitnation.model;

import java.io.*;
import java.time.*;
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
@Table(name = "workout_template")
public class UserWorkoutTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "template_id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdDate;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private ZonedDateTime lastUpdated;

    /* Joins */

    @ManyToOne
    @JoinColumn(name = "log_id")
    private UserWorkoutLog workoutLog;

    @OneToMany(mappedBy = "workoutTemplate")
    private List<UserWorkoutInstance> workouts;


    /* Constructors */

    public UserWorkoutTemplate() { /* Required By Jpa */}





    /* Mutator */

    /**
     *
     * @return
     */
    public List<UserWorkoutInstance> getWorkouts() {
        return workouts;
    }

    /**
     *
     * @param workouts
     */
    public void setWorkouts(List<UserWorkoutInstance> workouts) {
        this.workouts = workouts;
    }

    /**
     *
     * @return
     */
    public UserWorkoutLog getWorkoutLog() {
        return workoutLog;
    }

    /**
     *
     * @param workoutLog
     */
    public void setWorkoutLog(UserWorkoutLog workoutLog) {
        this.workoutLog = workoutLog;
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
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
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

    /**
     *
     * @return
     */
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     */
    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     */
    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     *
     * @param lastUpdated
     */
    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
