package com.thefitnation.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.UnitOfMeasure;

/**
 * A DTO for the UserDemographic entity.
 */
public class UserDemographicDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate createdOn;

    @NotNull
    private LocalDate lastLogin;

    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;

    private Float height;

    @NotNull
    private UnitOfMeasure unitOfMeasure;

    private Long userId;

    private String userLogin;

    private Set<GymDTO> gyms = new HashSet<>();

    private Long skillLevelId;

    private String skillLevelLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<GymDTO> getGyms() {
        return gyms;
    }

    public void setGyms(Set<GymDTO> gyms) {
        this.gyms = gyms;
    }

    public Long getSkillLevelId() {
        return skillLevelId;
    }

    public void setSkillLevelId(Long skillLevelId) {
        this.skillLevelId = skillLevelId;
    }

    public String getSkillLevelLevel() {
        return skillLevelLevel;
    }

    public void setSkillLevelLevel(String skillLevelLevel) {
        this.skillLevelLevel = skillLevelLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDemographicDTO userDemographicDTO = (UserDemographicDTO) o;

        if ( ! Objects.equals(id, userDemographicDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserDemographicDTO{" +
            "id=" + id +
            ", createdOn='" + createdOn + "'" +
            ", lastLogin='" + lastLogin + "'" +
            ", gender='" + gender + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", height='" + height + "'" +
            ", unitOfMeasure='" + unitOfMeasure + "'" +
            '}';
    }
}
