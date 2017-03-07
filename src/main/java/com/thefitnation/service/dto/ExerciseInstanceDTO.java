package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ExerciseInstance entity.
 */
public class ExerciseInstanceDTO implements Serializable {

    private Long id;

    private String notes;

    private Long workoutInstanceId;

    private Long exerciseId;

    private String exerciseName;

    private Long repUnitId;

    private String repUnitName;

    private Long effortUnitId;

    private String effortUnitName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getWorkoutInstanceId() {
        return workoutInstanceId;
    }

    public void setWorkoutInstanceId(Long workoutInstanceId) {
        this.workoutInstanceId = workoutInstanceId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Long getRepUnitId() {
        return repUnitId;
    }

    public void setRepUnitId(Long unitId) {
        this.repUnitId = unitId;
    }

    public String getRepUnitName() {
        return repUnitName;
    }

    public void setRepUnitName(String unitName) {
        this.repUnitName = unitName;
    }

    public Long getEffortUnitId() {
        return effortUnitId;
    }

    public void setEffortUnitId(Long unitId) {
        this.effortUnitId = unitId;
    }

    public String getEffortUnitName() {
        return effortUnitName;
    }

    public void setEffortUnitName(String unitName) {
        this.effortUnitName = unitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciseInstanceDTO exerciseInstanceDTO = (ExerciseInstanceDTO) o;

        if ( ! Objects.equals(id, exerciseInstanceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseInstanceDTO{" +
            "id=" + id +
            ", notes='" + notes + "'" +
            '}';
    }
}
