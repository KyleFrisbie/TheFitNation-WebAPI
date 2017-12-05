package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

/**
 * A ExerciseFamily.
 */
@Entity
@Table(name = "exercise_family", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExerciseFamily implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "exerciseFamily")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exercise> exercises = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExerciseFamily name(String name) {
        this.name = name;
        return this;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public ExerciseFamily exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public ExerciseFamily addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.setExerciseFamily(this);
        return this;
    }

    public ExerciseFamily removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.setExerciseFamily(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExerciseFamily exerciseFamily = (ExerciseFamily) o;
        if (exerciseFamily.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exerciseFamily.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseFamily{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
