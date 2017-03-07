package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Muscle entity.
 */
public class MuscleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long bodyPartId;

    private String bodyPartName;

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

    public Long getBodyPartId() {
        return bodyPartId;
    }

    public void setBodyPartId(Long bodyPartId) {
        this.bodyPartId = bodyPartId;
    }

    public String getBodyPartName() {
        return bodyPartName;
    }

    public void setBodyPartName(String bodyPartName) {
        this.bodyPartName = bodyPartName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MuscleDTO muscleDTO = (MuscleDTO) o;

        if ( ! Objects.equals(id, muscleDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MuscleDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
