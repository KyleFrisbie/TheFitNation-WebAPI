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
 * A WorkoutInstance.
 */
@Entity
@Table(name = "workout_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "workoutinstance")
public class WorkoutInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created_on;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private ZonedDateTime last_updated;

    @Column(name = "rest_between_instances")
    private Integer rest_between_instances;

    @ManyToMany(mappedBy = "workoutInstances")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutTemplate> workoutTemplates = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workout_instance_muscle",
               joinColumns = @JoinColumn(name="workout_instances_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="muscles_id", referencedColumnName="ID"))
    private Set<Muscle> muscles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workout_instance_exercise",
               joinColumns = @JoinColumn(name="workout_instances_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="exercises_id", referencedColumnName="ID"))
    private Set<Exercise> exercises = new HashSet<>();

    @ManyToMany(mappedBy = "workoutInstances")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutLog> workoutLogs = new HashSet<>();

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

    public Set<WorkoutTemplate> getWorkoutTemplates() {
        return workoutTemplates;
    }

    public WorkoutInstance workoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
        return this;
    }

    public WorkoutInstance addWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.add(workoutTemplate);
        workoutTemplate.getWorkoutInstances().add(this);
        return this;
    }

    public WorkoutInstance removeWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.remove(workoutTemplate);
        workoutTemplate.getWorkoutInstances().remove(this);
        return this;
    }

    public void setWorkoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
    }

    public Set<Muscle> getMuscles() {
        return muscles;
    }

    public WorkoutInstance muscles(Set<Muscle> muscles) {
        this.muscles = muscles;
        return this;
    }

    public WorkoutInstance addMuscle(Muscle muscle) {
        muscles.add(muscle);
        muscle.getWorkoutInstances().add(this);
        return this;
    }

    public WorkoutInstance removeMuscle(Muscle muscle) {
        muscles.remove(muscle);
        muscle.getWorkoutInstances().remove(this);
        return this;
    }

    public void setMuscles(Set<Muscle> muscles) {
        this.muscles = muscles;
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

    public Set<WorkoutLog> getWorkoutLogs() {
        return workoutLogs;
    }

    public WorkoutInstance workoutLogs(Set<WorkoutLog> workoutLogs) {
        this.workoutLogs = workoutLogs;
        return this;
    }

    public WorkoutInstance addWorkoutLog(WorkoutLog workoutLog) {
        workoutLogs.add(workoutLog);
        workoutLog.getWorkoutInstances().add(this);
        return this;
    }

    public WorkoutInstance removeWorkoutLog(WorkoutLog workoutLog) {
        workoutLogs.remove(workoutLog);
        workoutLog.getWorkoutInstances().remove(this);
        return this;
    }

    public void setWorkoutLogs(Set<WorkoutLog> workoutLogs) {
        this.workoutLogs = workoutLogs;
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
            ", created_on='" + created_on + "'" +
            ", last_updated='" + last_updated + "'" +
            ", rest_between_instances='" + rest_between_instances + "'" +
            '}';
    }
}
