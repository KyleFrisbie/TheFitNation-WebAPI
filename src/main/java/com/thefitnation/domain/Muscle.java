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
 * A Muscle.
 */
@Entity
@Table(name = "muscle",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Muscle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "muscles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exercise> exercises = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private BodyPart bodyPart;

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

    public Muscle name(String name) {
        this.name = name;
        return this;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Muscle exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public Muscle addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.getMuscles().add(this);
        return this;
    }

    public Muscle removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.getMuscles().remove(this);
        return this;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Muscle bodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
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
        Muscle muscle = (Muscle) o;
        if (muscle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, muscle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Muscle{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
