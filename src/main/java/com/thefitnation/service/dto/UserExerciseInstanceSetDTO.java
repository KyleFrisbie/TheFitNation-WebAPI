package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserExerciseInstanceSet entity.
 */
public class UserExerciseInstanceSetDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer orderNumber;

    @NotNull
    private Float repQuantity;

    @NotNull
    private Float effortQuantity;

    private Float restTime;

    private String notes;

    private Long userExerciseInstanceId;

    private Long exerciseInstanceSetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
    public Float getRepQuantity() {
        return repQuantity;
    }

    public void setRepQuantity(Float repQuantity) {
        this.repQuantity = repQuantity;
    }
    public Float getEffortQuantity() {
        return effortQuantity;
    }

    public void setEffortQuantity(Float effortQuantity) {
        this.effortQuantity = effortQuantity;
    }
    public Float getRestTime() {
        return restTime;
    }

    public void setRestTime(Float restTime) {
        this.restTime = restTime;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getUserExerciseInstanceId() {
        return userExerciseInstanceId;
    }

    public void setUserExerciseInstanceId(Long userExerciseInstanceId) {
        this.userExerciseInstanceId = userExerciseInstanceId;
    }

    public Long getExerciseInstanceSetId() {
        return exerciseInstanceSetId;
    }

    public void setExerciseInstanceSetId(Long exerciseInstanceSetId) {
        this.exerciseInstanceSetId = exerciseInstanceSetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = (UserExerciseInstanceSetDTO) o;

        if ( ! Objects.equals(id, userExerciseInstanceSetDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExerciseInstanceSetDTO{" +
            "id=" + id +
            ", orderNumber='" + orderNumber + "'" +
            ", repQuantity='" + repQuantity + "'" +
            ", effortQuantity='" + effortQuantity + "'" +
            ", restTime='" + restTime + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
