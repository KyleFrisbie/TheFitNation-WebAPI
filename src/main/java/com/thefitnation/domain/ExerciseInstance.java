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

/**
 * A ExerciseInstance.
 */
@Entity
@Table(name = "exercise_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExerciseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private WorkoutInstance workoutInstance;

    @OneToMany(mappedBy = "exerciseInstance")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExerciseInstance> userExerciseInstances = new HashSet<>();

    @OneToMany(mappedBy = "exerciseInstance", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExerciseInstanceSet> exerciseInstanceSets = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Exercise exercise;

    @ManyToOne(optional = false)
    @NotNull
    private Unit repUnit;

    @ManyToOne(optional = false)
    @NotNull
    private Unit effortUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public ExerciseInstance notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public WorkoutInstance getWorkoutInstance() {
        return workoutInstance;
    }

    public ExerciseInstance workoutInstance(WorkoutInstance workoutInstance) {
        this.workoutInstance = workoutInstance;
        return this;
    }

    public void setWorkoutInstance(WorkoutInstance workoutInstance) {
        this.workoutInstance = workoutInstance;
    }

    public Set<UserExerciseInstance> getUserExerciseInstances() {
        return userExerciseInstances;
    }

    public ExerciseInstance userExerciseInstances(Set<UserExerciseInstance> userExerciseInstances) {
        this.userExerciseInstances = userExerciseInstances;
        return this;
    }

    public ExerciseInstance addUserExerciseInstance(UserExerciseInstance userExerciseInstance) {
        this.userExerciseInstances.add(userExerciseInstance);
        userExerciseInstance.setExerciseInstance(this);
        return this;
    }

    public ExerciseInstance removeUserExerciseInstance(UserExerciseInstance userExerciseInstance) {
        this.userExerciseInstances.remove(userExerciseInstance);
        userExerciseInstance.setExerciseInstance(null);
        return this;
    }

    public void setUserExerciseInstances(Set<UserExerciseInstance> userExerciseInstances) {
        this.userExerciseInstances = userExerciseInstances;
    }

    public Set<ExerciseInstanceSet> getExerciseInstanceSets() {
        return exerciseInstanceSets;
    }

    public ExerciseInstance exerciseInstanceSets(Set<ExerciseInstanceSet> exerciseInstanceSets) {
        this.exerciseInstanceSets = exerciseInstanceSets;
        return this;
    }

    public ExerciseInstance addExerciseInstanceSet(ExerciseInstanceSet exerciseInstanceSet) {
        this.exerciseInstanceSets.add(exerciseInstanceSet);
        exerciseInstanceSet.setExerciseInstance(this);
        return this;
    }

    public ExerciseInstance removeExerciseInstanceSet(ExerciseInstanceSet exerciseInstanceSet) {
        this.exerciseInstanceSets.remove(exerciseInstanceSet);
        exerciseInstanceSet.setExerciseInstance(null);
        return this;
    }

    public void setExerciseInstanceSets(Set<ExerciseInstanceSet> exerciseInstanceSets) {
        this.exerciseInstanceSets = exerciseInstanceSets;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public ExerciseInstance exercise(Exercise exercise) {
        this.exercise = exercise;
        return this;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Unit getRepUnit() {
        return repUnit;
    }

    public ExerciseInstance repUnit(Unit unit) {
        this.repUnit = unit;
        return this;
    }

    public void setRepUnit(Unit unit) {
        this.repUnit = unit;
    }

    public Unit getEffortUnit() {
        return effortUnit;
    }

    public ExerciseInstance effortUnit(Unit unit) {
        this.effortUnit = unit;
        return this;
    }

    public void setEffortUnit(Unit unit) {
        this.effortUnit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExerciseInstance exerciseInstance = (ExerciseInstance) o;
        if (exerciseInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exerciseInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseInstance{" +
            "id=" + id +
            ", notes='" + notes + "'" +
            '}';
    }
}
