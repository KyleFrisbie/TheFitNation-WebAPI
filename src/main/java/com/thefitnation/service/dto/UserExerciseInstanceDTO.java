package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserExerciseInstance entity.
 */
public class UserExerciseInstanceDTO implements Serializable {

    private Long id;

    private String notes;

    private Long userWorkoutInstanceId;

    private Long exerciseInstanceId;

    private List<UserExerciseInstanceSetDTO> userExerciseInstanceSets;

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

    public Long getUserWorkoutInstanceId() {
        return userWorkoutInstanceId;
    }

    public void setUserWorkoutInstanceId(Long userWorkoutInstanceId) {
        this.userWorkoutInstanceId = userWorkoutInstanceId;
    }

    public Long getExerciseInstanceId() {
        return exerciseInstanceId;
    }

    public void setExerciseInstanceId(Long exerciseInstanceId) {
        this.exerciseInstanceId = exerciseInstanceId;
    }

    public List<UserExerciseInstanceSetDTO> getUserExerciseInstanceSets() {
        return userExerciseInstanceSets;
    }

    public void setUserExerciseInstanceSets(List<UserExerciseInstanceSetDTO> userExerciseInstanceSets) {
        this.userExerciseInstanceSets = userExerciseInstanceSets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserExerciseInstanceDTO userExerciseInstanceDTO = (UserExerciseInstanceDTO) o;

        if ( ! Objects.equals(id, userExerciseInstanceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExerciseInstanceDTO{" +
            "id=" + id +
            ", notes='" + notes + "'" +
            '}';
    }
}
