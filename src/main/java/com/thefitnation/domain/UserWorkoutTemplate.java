package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private LocalDate lastUpdated;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private UserDemographic userDemographic;

    @ManyToOne
    private WorkoutTemplate workoutTemplate;

    @OneToMany(mappedBy = "userWorkoutTemplate", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWorkoutInstance> userWorkoutInstances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public UserWorkoutTemplate createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public UserWorkoutTemplate lastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getNotes() {
        return notes;
    }

    public UserWorkoutTemplate notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UserDemographic getUserDemographic() {
        return userDemographic;
    }

    public UserWorkoutTemplate userDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
        return this;
    }

    public void setUserDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
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
        this.userWorkoutInstances.add(userWorkoutInstance);
        userWorkoutInstance.setUserWorkoutTemplate(this);
        return this;
    }

    public UserWorkoutTemplate removeUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        this.userWorkoutInstances.remove(userWorkoutInstance);
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
            ", createdOn='" + createdOn + "'" +
            ", lastUpdated='" + lastUpdated + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
