package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.thefitnation.domain.enumeration.ExerciseType;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_type")
    private ExerciseType exercise_type;

    @ManyToMany(mappedBy = "exercises")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutInstance> workoutInstances = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "exercise_muscle",
               joinColumns = @JoinColumn(name="exercises_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="muscles_id", referencedColumnName="ID"))
    private Set<Muscle> muscles = new HashSet<>();

    @OneToMany(mappedBy = "exercise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExerciseSet> exerciseSets = new HashSet<>();

    @OneToMany(mappedBy = "exercise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExercise> userExercises = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Exercise name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExerciseType getExercise_type() {
        return exercise_type;
    }

    public Exercise exercise_type(ExerciseType exercise_type) {
        this.exercise_type = exercise_type;
        return this;
    }

    public void setExercise_type(ExerciseType exercise_type) {
        this.exercise_type = exercise_type;
    }

    public Set<WorkoutInstance> getWorkoutInstances() {
        return workoutInstances;
    }

    public Exercise workoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
        return this;
    }

    public Exercise addWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.add(workoutInstance);
        workoutInstance.getExercises().add(this);
        return this;
    }

    public Exercise removeWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.remove(workoutInstance);
        workoutInstance.getExercises().remove(this);
        return this;
    }

    public void setWorkoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
    }

    public Set<Muscle> getMuscles() {
        return muscles;
    }

    public Exercise muscles(Set<Muscle> muscles) {
        this.muscles = muscles;
        return this;
    }

    public Exercise addMuscle(Muscle muscle) {
        muscles.add(muscle);
        muscle.getExercises().add(this);
        return this;
    }

    public Exercise removeMuscle(Muscle muscle) {
        muscles.remove(muscle);
        muscle.getExercises().remove(this);
        return this;
    }

    public void setMuscles(Set<Muscle> muscles) {
        this.muscles = muscles;
    }

    public Set<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public Exercise exerciseSets(Set<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
        return this;
    }

    public Exercise addExerciseSet(ExerciseSet exerciseSet) {
        exerciseSets.add(exerciseSet);
        exerciseSet.setExercise(this);
        return this;
    }

    public Exercise removeExerciseSet(ExerciseSet exerciseSet) {
        exerciseSets.remove(exerciseSet);
        exerciseSet.setExercise(null);
        return this;
    }

    public void setExerciseSets(Set<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }

    public Set<UserExercise> getUserExercises() {
        return userExercises;
    }

    public Exercise userExercises(Set<UserExercise> userExercises) {
        this.userExercises = userExercises;
        return this;
    }

    public Exercise addUserExercise(UserExercise userExercise) {
        userExercises.add(userExercise);
        userExercise.setExercise(this);
        return this;
    }

    public Exercise removeUserExercise(UserExercise userExercise) {
        userExercises.remove(userExercise);
        userExercise.setExercise(null);
        return this;
    }

    public void setUserExercises(Set<UserExercise> userExercises) {
        this.userExercises = userExercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        if (exercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", exercise_type='" + exercise_type + "'" +
            '}';
    }
}
