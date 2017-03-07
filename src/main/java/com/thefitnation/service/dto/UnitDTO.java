package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.thefitnation.domain.enumeration.Units;

/**
 * A DTO for the Unit entity.
 */
public class UnitDTO implements Serializable {

    private Long id;

    @NotNull
    private Units name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Units getName() {
        return name;
    }

    public void setName(Units name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnitDTO unitDTO = (UnitDTO) o;

        if ( ! Objects.equals(id, unitDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UnitDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
