package com.thefitnation.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;

/**
 * <p></p>
 * Created by michael on 2/19/17.
 * @author michael menard
 * @version 0.1.0
 * @since 2/19/17
 */
@Entity
@Table(name = "user_workout_instance")
public class UserExerciseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long id;

    /* Joins */

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private UserWorkoutInstance workoutInstance;

    @OneToMany(mappedBy = "exerciseInstance")
    private List<UserExerciseInstanceSet> exerciseSet;



    /* Constructors */

    /**
     *
     */
    public UserExerciseInstance() { /* Required by Jpa */ }



    /* Mutators */

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
    public UserWorkoutInstance getWorkoutInstance() {
        return workoutInstance;
    }

    /**
     *
     * @param workoutInstance
     */
    public void setWorkoutInstance(UserWorkoutInstance workoutInstance) {
        this.workoutInstance = workoutInstance;
    }

    /**
     *
     * @return
     */
    public List<UserExerciseInstanceSet> getExerciseSet() {
        return exerciseSet;
    }

    /**
     *
     * @param exerciseSet
     */
    public void setExerciseSet(List<UserExerciseInstanceSet> exerciseSet) {
        this.exerciseSet = exerciseSet;
    }
}
