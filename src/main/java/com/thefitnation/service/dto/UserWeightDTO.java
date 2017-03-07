package com.thefitnation.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserWeight entity.
 */
public class UserWeightDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate weightDate;

    @NotNull
    @DecimalMin(value = "1")
    private Float weight;

    private Long userDemographicId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(LocalDate weightDate) {
        this.weightDate = weightDate;
    }
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Long getUserDemographicId() {
        return userDemographicId;
    }

    public void setUserDemographicId(Long userDemographicId) {
        this.userDemographicId = userDemographicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserWeightDTO userWeightDTO = (UserWeightDTO) o;

        if ( ! Objects.equals(id, userWeightDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWeightDTO{" +
            "id=" + id +
            ", weightDate='" + weightDate + "'" +
            ", weight='" + weight + "'" +
            '}';
    }
}
