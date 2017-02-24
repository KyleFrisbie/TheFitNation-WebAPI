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
@Table(name = "user_workout_log")
public class UserWorkoutLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id")
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate created_on;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private LocalDate last_updated;

    /* Joins */

    @OneToOne(mappedBy = "workoutLog")
    private User user;

    @OneToMany(mappedBy = "workoutLog")
    private List<UserWorkoutTemplate> workoutTemplates;






    /* Constructors */

    public UserWorkoutLog() { /* Required by Jpa */ }



    /* Mutator */

    public List<UserWorkoutTemplate> getWorkoutTemplates() {
        return workoutTemplates;
    }

    public void setWorkoutTemplates(List<UserWorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreated_on() {
        return created_on;
    }

    public void setCreated_on(LocalDate created_on) {
        this.created_on = created_on;
    }

    public LocalDate getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(LocalDate last_updated) {
        this.last_updated = last_updated;
    }
}
