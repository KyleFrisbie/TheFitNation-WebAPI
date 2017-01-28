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
 * A WorkoutTemplate.
 */
@Entity
@Table(name = "workout_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "workouttemplate")
public class WorkoutTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 0)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created_on;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private ZonedDateTime last_updated;

    @NotNull
    @Column(name = "is_private", nullable = false)
    private Boolean is_private;

    @ManyToOne
    @NotNull
    private UserDemographic userDemographic;

    @ManyToMany(mappedBy = "workoutTemplates")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutLog> workoutLogs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "workout_template_workout_instance",
               joinColumns = @JoinColumn(name="workout_templates_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="workout_instances_id", referencedColumnName="ID"))
    private Set<WorkoutInstance> workoutInstances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WorkoutTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreated_on() {
        return created_on;
    }

    public WorkoutTemplate created_on(ZonedDateTime created_on) {
        this.created_on = created_on;
        return this;
    }

    public void setCreated_on(ZonedDateTime created_on) {
        this.created_on = created_on;
    }

    public ZonedDateTime getLast_updated() {
        return last_updated;
    }

    public WorkoutTemplate last_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
        return this;
    }

    public void setLast_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
    }

    public Boolean isIs_private() {
        return is_private;
    }

    public WorkoutTemplate is_private(Boolean is_private) {
        this.is_private = is_private;
        return this;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }

    public UserDemographic getUserDemographic() {
        return userDemographic;
    }

    public WorkoutTemplate userDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
        return this;
    }

    public void setUserDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
    }

    public Set<WorkoutLog> getWorkoutLogs() {
        return workoutLogs;
    }

    public WorkoutTemplate workoutLogs(Set<WorkoutLog> workoutLogs) {
        this.workoutLogs = workoutLogs;
        return this;
    }

    public WorkoutTemplate addWorkoutLog(WorkoutLog workoutLog) {
        workoutLogs.add(workoutLog);
        workoutLog.getWorkoutTemplates().add(this);
        return this;
    }

    public WorkoutTemplate removeWorkoutLog(WorkoutLog workoutLog) {
        workoutLogs.remove(workoutLog);
        workoutLog.getWorkoutTemplates().remove(this);
        return this;
    }

    public void setWorkoutLogs(Set<WorkoutLog> workoutLogs) {
        this.workoutLogs = workoutLogs;
    }

    public Set<WorkoutInstance> getWorkoutInstances() {
        return workoutInstances;
    }

    public WorkoutTemplate workoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
        return this;
    }

    public WorkoutTemplate addWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.add(workoutInstance);
        workoutInstance.getWorkoutTemplates().add(this);
        return this;
    }

    public WorkoutTemplate removeWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.remove(workoutInstance);
        workoutInstance.getWorkoutTemplates().remove(this);
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
        WorkoutTemplate workoutTemplate = (WorkoutTemplate) o;
        if (workoutTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutTemplate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_on='" + created_on + "'" +
            ", last_updated='" + last_updated + "'" +
            ", is_private='" + is_private + "'" +
            '}';
    }
}
