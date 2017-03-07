package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ExerciseInstanceSet entity.
 */
public class ExerciseInstanceSetDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer orderNumber;

    @NotNull
    private Float reqQuantity;

    @NotNull
    private Float effortQuantity;

    private Float restTime;

    private String notes;

    private Long exerciseInstanceId;

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
    public Float getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(Float reqQuantity) {
        this.reqQuantity = reqQuantity;
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

    public Long getExerciseInstanceId() {
        return exerciseInstanceId;
    }

    public void setExerciseInstanceId(Long exerciseInstanceId) {
        this.exerciseInstanceId = exerciseInstanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciseInstanceSetDTO exerciseInstanceSetDTO = (ExerciseInstanceSetDTO) o;

        if ( ! Objects.equals(id, exerciseInstanceSetDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseInstanceSetDTO{" +
            "id=" + id +
            ", orderNumber='" + orderNumber + "'" +
            ", reqQuantity='" + reqQuantity + "'" +
            ", effortQuantity='" + effortQuantity + "'" +
            ", restTime='" + restTime + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
