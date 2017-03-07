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
 * A UserWorkoutInstance.
 */
@Entity
@Table(name = "user_workout_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserWorkoutInstance implements Serializable {

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

    @NotNull
    @Column(name = "was_completed", nullable = false)
    private Boolean wasCompleted;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private UserWorkoutTemplate userWorkoutTemplate;

    @ManyToOne
    private WorkoutInstance workoutInstance;

    @OneToMany(mappedBy = "userWorkoutInstance")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExerciseInstance> userExerciseInstances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public UserWorkoutInstance createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public UserWorkoutInstance lastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean isWasCompleted() {
        return wasCompleted;
    }

    public UserWorkoutInstance wasCompleted(Boolean wasCompleted) {
        this.wasCompleted = wasCompleted;
        return this;
    }

    public void setWasCompleted(Boolean wasCompleted) {
        this.wasCompleted = wasCompleted;
    }

    public String getNotes() {
        return notes;
    }

    public UserWorkoutInstance notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Set<UserExerciseInstance> getUserExerciseInstances() {
        return userExerciseInstances;
    }

    public UserWorkoutInstance userExerciseInstances(Set<UserExerciseInstance> userExerciseInstances) {
        this.userExerciseInstances = userExerciseInstances;
        return this;
    }

    public UserWorkoutInstance addUserExerciseInstance(UserExerciseInstance userExerciseInstance) {
        this.userExerciseInstances.add(userExerciseInstance);
        userExerciseInstance.setUserWorkoutInstance(this);
        return this;
    }

    public UserWorkoutInstance removeUserExerciseInstance(UserExerciseInstance userExerciseInstance) {
        this.userExerciseInstances.remove(userExerciseInstance);
        userExerciseInstance.setUserWorkoutInstance(null);
        return this;
    }

    public void setUserExerciseInstances(Set<UserExerciseInstance> userExerciseInstances) {
        this.userExerciseInstances = userExerciseInstances;
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
            ", createdOn='" + createdOn + "'" +
            ", lastUpdated='" + lastUpdated + "'" +
            ", wasCompleted='" + wasCompleted + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
