package com.thefitnation.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserWorkoutInstance entity.
 */
public class UserWorkoutInstanceDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate createdOn;

    @NotNull
    private LocalDate lastUpdated;

    @NotNull
    private Boolean wasCompleted;

    private String notes;

    private Long userWorkoutTemplateId;

    private Long workoutInstanceId;

    private String workoutInstanceName;

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
    public Boolean getWasCompleted() {
        return wasCompleted;
    }

    public void setWasCompleted(Boolean wasCompleted) {
        this.wasCompleted = wasCompleted;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getUserWorkoutTemplateId() {
        return userWorkoutTemplateId;
    }

    public void setUserWorkoutTemplateId(Long userWorkoutTemplateId) {
        this.userWorkoutTemplateId = userWorkoutTemplateId;
    }

    public Long getWorkoutInstanceId() {
        return workoutInstanceId;
    }

    public void setWorkoutInstanceId(Long workoutInstanceId) {
        this.workoutInstanceId = workoutInstanceId;
    }

    public String getWorkoutInstanceName() {
        return workoutInstanceName;
    }

    public void setWorkoutInstanceName(String workoutInstanceName) {
        this.workoutInstanceName = workoutInstanceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserWorkoutInstanceDTO userWorkoutInstanceDTO = (UserWorkoutInstanceDTO) o;

        if ( ! Objects.equals(id, userWorkoutInstanceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWorkoutInstanceDTO{" +
            "id=" + id +
            ", createdOn='" + createdOn + "'" +
            ", lastUpdated='" + lastUpdated + "'" +
            ", wasCompleted='" + wasCompleted + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
