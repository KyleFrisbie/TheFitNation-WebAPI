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
 * A WorkoutInstance.
 */
@Entity
@Table(name = "workout_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkoutInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private LocalDate lastUpdated;

    @Column(name = "rest_between_instances")
    private Float restBetweenInstances;

    @NotNull
    @Min(value = 1)
    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private WorkoutTemplate workoutTemplate;

    @OneToMany(mappedBy = "workoutInstance")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWorkoutInstance> userWorkoutInstances = new HashSet<>();

    @OneToMany(mappedBy = "workoutInstance")

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExerciseInstance> exerciseInstances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WorkoutInstance name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public WorkoutInstance createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public WorkoutInstance lastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Float getRestBetweenInstances() {
        return restBetweenInstances;
    }

    public WorkoutInstance restBetweenInstances(Float restBetweenInstances) {
        this.restBetweenInstances = restBetweenInstances;
        return this;
    }

    public void setRestBetweenInstances(Float restBetweenInstances) {
        this.restBetweenInstances = restBetweenInstances;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public WorkoutInstance orderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getNotes() {
        return notes;
    }

    public WorkoutInstance notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public WorkoutTemplate getWorkoutTemplate() {
        return workoutTemplate;
    }

    public WorkoutInstance workoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
        return this;
    }

    public void setWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
    }

    public Set<UserWorkoutInstance> getUserWorkoutInstances() {
        return userWorkoutInstances;
    }

    public WorkoutInstance userWorkoutInstances(Set<UserWorkoutInstance> userWorkoutInstances) {
        this.userWorkoutInstances = userWorkoutInstances;
        return this;
    }

    public WorkoutInstance addUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        this.userWorkoutInstances.add(userWorkoutInstance);
        userWorkoutInstance.setWorkoutInstance(this);
        return this;
    }

    public WorkoutInstance removeUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        this.userWorkoutInstances.remove(userWorkoutInstance);
        userWorkoutInstance.setWorkoutInstance(null);
        return this;
    }

    public void setUserWorkoutInstances(Set<UserWorkoutInstance> userWorkoutInstances) {
        this.userWorkoutInstances = userWorkoutInstances;
    }

    public Set<ExerciseInstance> getExerciseInstances() {
        return exerciseInstances;
    }

    public WorkoutInstance exerciseInstances(Set<ExerciseInstance> exerciseInstances) {
        this.exerciseInstances = exerciseInstances;
        return this;
    }

    public WorkoutInstance addExerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstances.add(exerciseInstance);
        exerciseInstance.setWorkoutInstance(this);
        return this;
    }

    public WorkoutInstance removeExerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstances.remove(exerciseInstance);
        exerciseInstance.setWorkoutInstance(null);
        return this;
    }

    public void setExerciseInstances(Set<ExerciseInstance> exerciseInstances) {
        this.exerciseInstances = exerciseInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutInstance workoutInstance = (WorkoutInstance) o;
        if (workoutInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutInstance{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", createdOn='" + createdOn + "'" +
            ", lastUpdated='" + lastUpdated + "'" +
            ", restBetweenInstances='" + restBetweenInstances + "'" +
            ", orderNumber='" + orderNumber + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
