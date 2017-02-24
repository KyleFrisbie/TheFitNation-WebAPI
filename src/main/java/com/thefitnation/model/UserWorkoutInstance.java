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
@Table(name = "user_workout_instance")
public class UserWorkoutInstance implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workout_id")
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created_on;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean isCompleted;

    /* Joins */

    @ManyToOne
    @JoinColumn(name = "user_template_id")
    private UserWorkoutTemplate workoutTemplate;

    @OneToMany(mappedBy = "workoutInstance")
    private List<UserExerciseInstance> exercises;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private PrescribedWorkoutInstance workoutInstance;


    /* Constructors */

    public UserWorkoutInstance() { /*Required by Jpa */ }




    /* Mutator */

    public List<UserExerciseInstance> getExercises() {
        return exercises;
    }

    public void setExercises(List<UserExerciseInstance> exercises) {
        this.exercises = exercises;
    }

    public UserWorkoutTemplate getWorkoutTemplate() {
        return workoutTemplate;
    }

    public void setWorkoutTemplate(UserWorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
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
