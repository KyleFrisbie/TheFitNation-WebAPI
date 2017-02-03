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
 * A WorkoutLog.
 */
@Entity
@Table(name = "workout_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkoutLog implements Serializable {

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

    @OneToOne(mappedBy = "workoutLog")
    @JsonIgnore
    private UserDemographic userDemographic;

    @OneToMany(mappedBy = "workoutLog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWorkoutTemplate> userWorkoutTemplates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated_on() {
        return created_on;
    }

    public WorkoutLog created_on(ZonedDateTime created_on) {
        this.created_on = created_on;
        return this;
    }

    public void setCreated_on(ZonedDateTime created_on) {
        this.created_on = created_on;
    }

    public ZonedDateTime getLast_updated() {
        return last_updated;
    }

    public WorkoutLog last_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
        return this;
    }

    public void setLast_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
    }

    public UserDemographic getUserDemographic() {
        return userDemographic;
    }

    public WorkoutLog userDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
        return this;
    }

    public void setUserDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
    }

    public Set<UserWorkoutTemplate> getUserWorkoutTemplates() {
        return userWorkoutTemplates;
    }

    public WorkoutLog userWorkoutTemplates(Set<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
        return this;
    }

    public WorkoutLog addUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        userWorkoutTemplates.add(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutLog(this);
        return this;
    }

    public WorkoutLog removeUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        userWorkoutTemplates.remove(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutLog(null);
        return this;
    }

    public void setUserWorkoutTemplates(Set<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutLog workoutLog = (WorkoutLog) o;
        if (workoutLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutLog{" +
            "id=" + id +
            ", created_on='" + created_on + "'" +
            ", last_updated='" + last_updated + "'" +
            '}';
    }
}
