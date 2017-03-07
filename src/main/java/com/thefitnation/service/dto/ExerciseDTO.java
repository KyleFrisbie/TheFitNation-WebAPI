package com.thefitnation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Exercise entity.
 */
public class ExerciseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String imageUri;

    private String notes;

    private Long skillLevelId;

    private String skillLevelLevel;

    private Set<MuscleDTO> muscles = new HashSet<>();

    private Long exerciseFamilyId;

    private String exerciseFamilyName;

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
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Set<MuscleDTO> getMuscles() {
        return muscles;
    }

    public void setMuscles(Set<MuscleDTO> muscles) {
        this.muscles = muscles;
    }

    public Long getExerciseFamilyId() {
        return exerciseFamilyId;
    }

    public void setExerciseFamilyId(Long exerciseFamilyId) {
        this.exerciseFamilyId = exerciseFamilyId;
    }

    public String getExerciseFamilyName() {
        return exerciseFamilyName;
    }

    public void setExerciseFamilyName(String exerciseFamilyName) {
        this.exerciseFamilyName = exerciseFamilyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciseDTO exerciseDTO = (ExerciseDTO) o;

        if ( ! Objects.equals(id, exerciseDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", imageUri='" + imageUri + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
