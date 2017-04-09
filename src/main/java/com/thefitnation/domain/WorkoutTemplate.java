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
 * A WorkoutTemplate.
 */
@Entity
@Table(name = "workout_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkoutTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private LocalDate lastUpdated;

    @NotNull
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private UserDemographic userDemographic;

    @OneToMany(mappedBy = "workoutTemplate", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutInstance> workoutInstances = new HashSet<>();

    @OneToMany(mappedBy = "workoutTemplate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWorkoutTemplate> userWorkoutTemplates = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private SkillLevel skillLevel;

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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public WorkoutTemplate createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public WorkoutTemplate lastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean isIsPrivate() {
        return isPrivate;
    }

    public WorkoutTemplate isPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getNotes() {
        return notes;
    }

    public WorkoutTemplate notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Set<WorkoutInstance> getWorkoutInstances() {
        return workoutInstances;
    }

    public WorkoutTemplate workoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
        return this;
    }

    public WorkoutTemplate addWorkoutInstance(WorkoutInstance workoutInstance) {
        this.workoutInstances.add(workoutInstance);
        workoutInstance.setWorkoutTemplate(this);
        return this;
    }

    public WorkoutTemplate removeWorkoutInstance(WorkoutInstance workoutInstance) {
        this.workoutInstances.remove(workoutInstance);
        workoutInstance.setWorkoutTemplate(null);
        return this;
    }

    public void setWorkoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
    }

    public Set<UserWorkoutTemplate> getUserWorkoutTemplates() {
        return userWorkoutTemplates;
    }

    public WorkoutTemplate userWorkoutTemplates(Set<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
        return this;
    }

    public WorkoutTemplate addUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        this.userWorkoutTemplates.add(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutTemplate(this);
        return this;
    }

    public WorkoutTemplate removeUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        this.userWorkoutTemplates.remove(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutTemplate(null);
        return this;
    }

    public void setUserWorkoutTemplates(Set<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public WorkoutTemplate skillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
        return this;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
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
            ", createdOn='" + createdOn + "'" +
            ", lastUpdated='" + lastUpdated + "'" +
            ", isPrivate='" + isPrivate + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
