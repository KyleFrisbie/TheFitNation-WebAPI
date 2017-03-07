package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.thefitnation.domain.enumeration.BodyPart;

/**
 * A DTO for the Muscle entity.
 */
public class MuscleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BodyPart bodyPart;

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
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
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
            ", bodyPart='" + bodyPart + "'" +
            '}';
    }
}
