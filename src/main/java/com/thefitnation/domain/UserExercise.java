package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserExercise.
 */
@Entity
@Table(name = "user_exercise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userexercise")
public class UserExercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private UserWorkoutInstance userWorkoutInstance;

    @ManyToOne
    @NotNull
    private Exercise exercise;

    @OneToMany(mappedBy = "userExercise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExerciseSet> userExerciseSets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserWorkoutInstance getUserWorkoutInstance() {
        return userWorkoutInstance;
    }

    public UserExercise userWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        this.userWorkoutInstance = userWorkoutInstance;
        return this;
    }

    public void setUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        this.userWorkoutInstance = userWorkoutInstance;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public UserExercise exercise(Exercise exercise) {
        this.exercise = exercise;
        return this;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Set<UserExerciseSet> getUserExerciseSets() {
        return userExerciseSets;
    }

    public UserExercise userExerciseSets(Set<UserExerciseSet> userExerciseSets) {
        this.userExerciseSets = userExerciseSets;
        return this;
    }

    public UserExercise addUserExerciseSet(UserExerciseSet userExerciseSet) {
        userExerciseSets.add(userExerciseSet);
        userExerciseSet.setUserExercise(this);
        return this;
    }

    public UserExercise removeUserExerciseSet(UserExerciseSet userExerciseSet) {
        userExerciseSets.remove(userExerciseSet);
        userExerciseSet.setUserExercise(null);
        return this;
    }

    public void setUserExerciseSets(Set<UserExerciseSet> userExerciseSets) {
        this.userExerciseSets = userExerciseSets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExercise userExercise = (UserExercise) o;
        if (userExercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExercise{" +
            "id=" + id +
            '}';
    }
}
