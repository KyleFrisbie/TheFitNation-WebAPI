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
 * A WorkoutInstance.
 */
@Entity
@Table(name = "workout_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkoutInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private ZonedDateTime last_updated;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created_on;

    @Column(name = "rest_between_instances")
    private Integer rest_between_instances;

    @NotNull
    @Min(value = 1)
    @Column(name = "order_number", nullable = false)
    private Integer order_number;

    @ManyToOne
    @NotNull
    private WorkoutTemplate workoutTemplate;

    @OneToMany(mappedBy = "workoutInstance")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWorkoutInstance> userWorkoutInstances = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "workout_instance_exercise",
               joinColumns = @JoinColumn(name="workout_instances_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="exercises_id", referencedColumnName="ID"))
    private Set<Exercise> exercises = new HashSet<>();

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

    public ZonedDateTime getLast_updated() {
        return last_updated;
    }

    public WorkoutInstance last_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
        return this;
    }

    public void setLast_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
    }

    public ZonedDateTime getCreated_on() {
        return created_on;
    }

    public WorkoutInstance created_on(ZonedDateTime created_on) {
        this.created_on = created_on;
        return this;
    }

    public void setCreated_on(ZonedDateTime created_on) {
        this.created_on = created_on;
    }

    public Integer getRest_between_instances() {
        return rest_between_instances;
    }

    public WorkoutInstance rest_between_instances(Integer rest_between_instances) {
        this.rest_between_instances = rest_between_instances;
        return this;
    }

    public void setRest_between_instances(Integer rest_between_instances) {
        this.rest_between_instances = rest_between_instances;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public WorkoutInstance order_number(Integer order_number) {
        this.order_number = order_number;
        return this;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
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
        userWorkoutInstances.add(userWorkoutInstance);
        userWorkoutInstance.setWorkoutInstance(this);
        return this;
    }

    public WorkoutInstance removeUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        userWorkoutInstances.remove(userWorkoutInstance);
        userWorkoutInstance.setWorkoutInstance(null);
        return this;
    }

    public void setUserWorkoutInstances(Set<UserWorkoutInstance> userWorkoutInstances) {
        this.userWorkoutInstances = userWorkoutInstances;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public WorkoutInstance exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public WorkoutInstance addExercise(Exercise exercise) {
        exercises.add(exercise);
        exercise.getWorkoutInstances().add(this);
        return this;
    }

    public WorkoutInstance removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        exercise.getWorkoutInstances().remove(this);
        return this;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
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
            ", last_updated='" + last_updated + "'" +
            ", created_on='" + created_on + "'" +
            ", rest_between_instances='" + rest_between_instances + "'" +
            ", order_number='" + order_number + "'" +
            '}';
    }
}
