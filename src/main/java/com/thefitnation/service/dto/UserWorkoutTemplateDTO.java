package com.thefitnation.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserWorkoutTemplate entity.
 */
public class UserWorkoutTemplateDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate createdOn;

    @NotNull
    private LocalDate lastUpdated;

    private String notes;

    private Long userDemographicId;

    private Long workoutTemplateId;

    private String workoutTemplateName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getWorkoutTemplateId() {
        return workoutTemplateId;
    }

    public void setWorkoutTemplateId(Long workoutTemplateId) {
        this.workoutTemplateId = workoutTemplateId;
    }

    public String getWorkoutTemplateName() {
        return workoutTemplateName;
    }

    public void setWorkoutTemplateName(String workoutTemplateName) {
        this.workoutTemplateName = workoutTemplateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserWorkoutTemplateDTO userWorkoutTemplateDTO = (UserWorkoutTemplateDTO) o;

        if ( ! Objects.equals(id, userWorkoutTemplateDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWorkoutTemplateDTO{" +
            "id=" + id +
            ", createdOn='" + createdOn + "'" +
            ", lastUpdated='" + lastUpdated + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
