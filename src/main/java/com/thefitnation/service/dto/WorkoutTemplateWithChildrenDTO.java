package com.thefitnation.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the WorkoutTemplate entity with embedded WorkoutInstances.
 */
public class WorkoutTemplateWithChildrenDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private LocalDate createdOn;

    @NotNull
    private LocalDate lastUpdated;

    @NotNull
    private Boolean isPrivate;

    private String notes;

    private Long userDemographicId;

    private Long skillLevelId;

    private String skillLevelLevel;

    private List<WorkoutInstanceDTO> workoutInstances;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getUserDemographicId() {
        return userDemographicId;
    }

    public void setUserDemographicId(Long userDemographicId) {
        this.userDemographicId = userDemographicId;
    }

    public Long getSkillLevelId() {
        return skillLevelId;
    }

    public void setSkillLevelId(Long skillLevelId) {
        this.skillLevelId = skillLevelId;
    }

    public String getSkillLevelLevel() {
        return skillLevelLevel;
    }

    public void setSkillLevelLevel(String skillLevelLevel) {
        this.skillLevelLevel = skillLevelLevel;
    }

    public List<WorkoutInstanceDTO> getWorkoutInstances() {
        return workoutInstances;
    }

    public void setWorkoutInstances(List<WorkoutInstanceDTO> workoutInstances) {
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

        WorkoutTemplateWithChildrenDTO workoutTemplateDTO = (WorkoutTemplateWithChildrenDTO) o;

        if ( ! Objects.equals(id, workoutTemplateDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutTemplateWithChildrenDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", createdOn='" + createdOn + "'" +
            ", lastUpdated='" + lastUpdated + "'" +
            ", isPrivate='" + isPrivate + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
