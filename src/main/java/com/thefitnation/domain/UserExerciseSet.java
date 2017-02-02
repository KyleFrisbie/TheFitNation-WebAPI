package com.thefitnation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserExerciseSet.
 */
@Entity
@Table(name = "user_exercise_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userexerciseset")
public class UserExerciseSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "order_number", nullable = false)
    private Integer order_number;

    @NotNull
    @Min(value = 0)
    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "weight")
    private Float weight;

    @Min(value = 0)
    @Column(name = "rest")
    private Integer rest;

    @ManyToOne
    @NotNull
    private UserExercise userExercise;

    @ManyToOne
    private ExerciseSet exerciseSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public UserExerciseSet order_number(Integer order_number) {
        this.order_number = order_number;
        return this;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    public Integer getReps() {
        return reps;
    }

    public UserExerciseSet reps(Integer reps) {
        this.reps = reps;
        return this;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Float getWeight() {
        return weight;
    }

    public UserExerciseSet weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getRest() {
        return rest;
    }

    public UserExerciseSet rest(Integer rest) {
        this.rest = rest;
        return this;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public UserExercise getUserExercise() {
        return userExercise;
    }

    public UserExerciseSet userExercise(UserExercise userExercise) {
        this.userExercise = userExercise;
        return this;
    }

    public void setUserExercise(UserExercise userExercise) {
        this.userExercise = userExercise;
    }

    public ExerciseSet getExerciseSet() {
        return exerciseSet;
    }

    public UserExerciseSet exerciseSet(ExerciseSet exerciseSet) {
        this.exerciseSet = exerciseSet;
        return this;
    }

    public void setExerciseSet(ExerciseSet exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExerciseSet userExerciseSet = (UserExerciseSet) o;
        if (userExerciseSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExerciseSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExerciseSet{" +
            "id=" + id +
            ", order_number='" + order_number + "'" +
            ", reps='" + reps + "'" +
            ", weight='" + weight + "'" +
            ", rest='" + rest + "'" +
            '}';
    }
}
