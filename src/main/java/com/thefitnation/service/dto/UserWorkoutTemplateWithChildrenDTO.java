package com.thefitnation.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the UserWorkoutTemplate entity.
 */
public class UserWorkoutTemplateWithChildrenDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate createdOn;

    @NotNull
    private LocalDate lastUpdated;

    private String notes;

    private Long userDemographicId;

    private Long workoutTemplateId;

    private String workoutTemplateName;

    private List<UserWorkoutInstanceDTO> userWorkoutInstances;

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

    public List<UserWorkoutInstanceDTO> getUserWorkoutInstances() {
        return userWorkoutInstances;
    }

    public void setUserWorkoutInstances(List<UserWorkoutInstanceDTO> userWorkoutInstances) {
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

        UserWorkoutTemplateWithChildrenDTO userWorkoutTemplateDTO = (UserWorkoutTemplateWithChildrenDTO) o;

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
