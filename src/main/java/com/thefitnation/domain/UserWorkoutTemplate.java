package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserWorkoutTemplate.
 */
@Entity
@Table(name = "user_workout_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserWorkoutTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created_on;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private ZonedDateTime last_updated;

    @ManyToOne
    @NotNull
    private WorkoutLog workoutLog;

    @ManyToOne
    @NotNull
    private WorkoutTemplate workoutTemplate;

    @OneToMany(mappedBy = "userWorkoutTemplate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWorkoutInstance> userWorkoutInstances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated_on() {
        return created_on;
    }

    public UserWorkoutTemplate created_on(ZonedDateTime created_on) {
        this.created_on = created_on;
        return this;
    }

    public void setCreated_on(ZonedDateTime created_on) {
        this.created_on = created_on;
    }

    public ZonedDateTime getLast_updated() {
        return last_updated;
    }

    public UserWorkoutTemplate last_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
        return this;
    }

    public void setLast_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
    }

    public WorkoutLog getWorkoutLog() {
        return workoutLog;
    }

    public UserWorkoutTemplate workoutLog(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;
        return this;
    }

    public void setWorkoutLog(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;
    }

    public WorkoutTemplate getWorkoutTemplate() {
        return workoutTemplate;
    }

    public UserWorkoutTemplate workoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
        return this;
    }

    public void setWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
    }

    public Set<UserWorkoutInstance> getUserWorkoutInstances() {
        return userWorkoutInstances;
    }

    public UserWorkoutTemplate userWorkoutInstances(Set<UserWorkoutInstance> userWorkoutInstances) {
        this.userWorkoutInstances = userWorkoutInstances;
        return this;
    }

    public UserWorkoutTemplate addUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        userWorkoutInstances.add(userWorkoutInstance);
        userWorkoutInstance.setUserWorkoutTemplate(this);
        return this;
    }

    public UserWorkoutTemplate removeUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        userWorkoutInstances.remove(userWorkoutInstance);
        userWorkoutInstance.setUserWorkoutTemplate(null);
        return this;
    }

    public void setUserWorkoutInstances(Set<UserWorkoutInstance> userWorkoutInstances) {
        this.userWorkoutInstances = userWorkoutInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserWorkoutTemplate userWorkoutTemplate = (UserWorkoutTemplate) o;
        if (userWorkoutTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWorkoutTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWorkoutTemplate{" +
            "id=" + id +
            ", created_on='" + created_on + "'" +
            ", last_updated='" + last_updated + "'" +
            '}';
    }
}
