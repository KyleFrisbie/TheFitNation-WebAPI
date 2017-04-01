package com.thefitnation.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the WorkoutInstance entity.
 */
public class WorkoutInstanceDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private LocalDate createdOn;

    @NotNull
    private LocalDate lastUpdated;

    private Float restBetweenInstances;

    @NotNull
    @Min(value = 1)
    private Integer orderNumber;

    private String notes;

    private Long workoutTemplateId;

    private String workoutTemplateName;

    private List<ExerciseInstanceDTO> exerciseInstances;

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
    public Float getRestBetweenInstances() {
        return restBetweenInstances;
    }

    public void setRestBetweenInstances(Float restBetweenInstances) {
        this.restBetweenInstances = restBetweenInstances;
    }
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public List<ExerciseInstanceDTO> getExerciseInstances() {
        return exerciseInstances;
    }

    public void setExerciseInstances(List<ExerciseInstanceDTO> exerciseInstances) {
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

        WorkoutInstanceDTO workoutInstanceDTO = (WorkoutInstanceDTO) o;

        if ( ! Objects.equals(id, workoutInstanceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutInstanceDTO{" +
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
