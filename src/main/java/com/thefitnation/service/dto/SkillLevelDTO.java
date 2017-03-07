package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.thefitnation.domain.enumeration.SkillLevel;

/**
 * A DTO for the SkillLevel entity.
 */
public class SkillLevelDTO implements Serializable {

    private Long id;

    @NotNull
    private SkillLevel level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillLevelDTO skillLevelDTO = (SkillLevelDTO) o;

        if ( ! Objects.equals(id, skillLevelDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SkillLevelDTO{" +
            "id=" + id +
            ", level='" + level + "'" +
            '}';
    }
}
