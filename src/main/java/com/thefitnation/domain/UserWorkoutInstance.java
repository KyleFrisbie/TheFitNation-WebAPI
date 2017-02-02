package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserWorkoutInstance.
 */
@Entity
@Table(name = "user_workout_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userworkoutinstance")
public class UserWorkoutInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created_on;

    @NotNull
    @Column(name = "was_completed", nullable = false)
    private Boolean was_completed;

    @ManyToOne
    private UserWorkoutTemplate userWorkoutTemplate;

    @ManyToOne
    private WorkoutInstance workoutInstance;

    @OneToMany(mappedBy = "userWorkoutInstance")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExercise> userExercises = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated_on() {
        return created_on;
    }

    public UserWorkoutInstance created_on(ZonedDateTime created_on) {
        this.created_on = created_on;
        return this;
    }

    public void setCreated_on(ZonedDateTime created_on) {
        this.created_on = created_on;
    }

    public Boolean isWas_completed() {
        return was_completed;
    }

    public UserWorkoutInstance was_completed(Boolean was_completed) {
        this.was_completed = was_completed;
        return this;
    }

    public void setWas_completed(Boolean was_completed) {
        this.was_completed = was_completed;
    }

    public UserWorkoutTemplate getUserWorkoutTemplate() {
        return userWorkoutTemplate;
    }

    public UserWorkoutInstance userWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        this.userWorkoutTemplate = userWorkoutTemplate;
        return this;
    }

    public void setUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        this.userWorkoutTemplate = userWorkoutTemplate;
    }

    public WorkoutInstance getWorkoutInstance() {
        return workoutInstance;
    }

    public UserWorkoutInstance workoutInstance(WorkoutInstance workoutInstance) {
        this.workoutInstance = workoutInstance;
        return this;
    }

    public void setWorkoutInstance(WorkoutInstance workoutInstance) {
        this.workoutInstance = workoutInstance;
    }

    public Set<UserExercise> getUserExercises() {
        return userExercises;
    }

    public UserWorkoutInstance userExercises(Set<UserExercise> userExercises) {
        this.userExercises = userExercises;
        return this;
    }

    public UserWorkoutInstance addUserExercise(UserExercise userExercise) {
        userExercises.add(userExercise);
        userExercise.setUserWorkoutInstance(this);
        return this;
    }

    public UserWorkoutInstance removeUserExercise(UserExercise userExercise) {
        userExercises.remove(userExercise);
        userExercise.setUserWorkoutInstance(null);
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
        UserWorkoutInstance userWorkoutInstance = (UserWorkoutInstance) o;
        if (userWorkoutInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWorkoutInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWorkoutInstance{" +
            "id=" + id +
            ", created_on='" + created_on + "'" +
            ", was_completed='" + was_completed + "'" +
            '}';
    }
}
