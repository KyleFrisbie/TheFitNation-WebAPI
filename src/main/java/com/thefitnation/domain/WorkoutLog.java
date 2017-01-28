package com.thefitnation.domain;

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
 * A WorkoutLog.
 */
@Entity
@Table(name = "workout_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "workoutlog")
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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workout_log_workout_template",
               joinColumns = @JoinColumn(name="workout_logs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="workout_templates_id", referencedColumnName="ID"))
    private Set<WorkoutTemplate> workoutTemplates = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workout_log_workout_instance",
               joinColumns = @JoinColumn(name="workout_logs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="workout_instances_id", referencedColumnName="ID"))
    private Set<WorkoutInstance> workoutInstances = new HashSet<>();

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

    public Set<WorkoutTemplate> getWorkoutTemplates() {
        return workoutTemplates;
    }

    public WorkoutLog workoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
        return this;
    }

    public WorkoutLog addWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.add(workoutTemplate);
        workoutTemplate.getWorkoutLogs().add(this);
        return this;
    }

    public WorkoutLog removeWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.remove(workoutTemplate);
        workoutTemplate.getWorkoutLogs().remove(this);
        return this;
    }

    public void setWorkoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
    }

    public Set<WorkoutInstance> getWorkoutInstances() {
        return workoutInstances;
    }

    public WorkoutLog workoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
        return this;
    }

    public WorkoutLog addWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.add(workoutInstance);
        workoutInstance.getWorkoutLogs().add(this);
        return this;
    }

    public WorkoutLog removeWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.remove(workoutInstance);
        workoutInstance.getWorkoutLogs().remove(this);
        return this;
    }

    public void setWorkoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
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
