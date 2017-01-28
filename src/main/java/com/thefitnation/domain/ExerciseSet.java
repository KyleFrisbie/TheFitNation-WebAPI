package com.thefitnation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ExerciseSet.
 */
@Entity
@Table(name = "exercise_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "exerciseset")
public class ExerciseSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "exercise_set_number", nullable = false)
    private Integer exercise_set_number;

    @NotNull
    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "rest")
    private Integer rest;

    @ManyToOne
    @NotNull
    private Exercise exercise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExercise_set_number() {
        return exercise_set_number;
    }

    public ExerciseSet exercise_set_number(Integer exercise_set_number) {
        this.exercise_set_number = exercise_set_number;
        return this;
    }

    public void setExercise_set_number(Integer exercise_set_number) {
        this.exercise_set_number = exercise_set_number;
    }

    public Integer getReps() {
        return reps;
    }

    public ExerciseSet reps(Integer reps) {
        this.reps = reps;
        return this;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getWeight() {
        return weight;
    }

    public ExerciseSet weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getRest() {
        return rest;
    }

    public ExerciseSet rest(Integer rest) {
        this.rest = rest;
        return this;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public ExerciseSet exercise(Exercise exercise) {
        this.exercise = exercise;
        return this;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExerciseSet exerciseSet = (ExerciseSet) o;
        if (exerciseSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exerciseSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
            "id=" + id +
            ", exercise_set_number='" + exercise_set_number + "'" +
            ", reps='" + reps + "'" +
            ", weight='" + weight + "'" +
            ", rest='" + rest + "'" +
            '}';
    }
}
