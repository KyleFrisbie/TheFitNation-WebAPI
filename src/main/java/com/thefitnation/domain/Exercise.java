package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_uri")
    private String imageUri;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private SkillLevel skillLevel;

    @OneToMany(mappedBy = "exercise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExerciseInstance> exerciseInstances = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "exercise_muscle",
               joinColumns = @JoinColumn(name="exercises_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="muscles_id", referencedColumnName="id"))
    private Set<Muscle> muscles = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private ExerciseFamily exerciseFamily;

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

    public Exercise name(String name) {
        this.name = name;
        return this;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Exercise imageUri(String imageUri) {
        this.imageUri = imageUri;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Exercise notes(String notes) {
        this.notes = notes;
        return this;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Exercise skillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
        return this;
    }

    public Set<ExerciseInstance> getExerciseInstances() {
        return exerciseInstances;
    }

    public void setExerciseInstances(Set<ExerciseInstance> exerciseInstances) {
        this.exerciseInstances = exerciseInstances;
    }

    public Exercise exerciseInstances(Set<ExerciseInstance> exerciseInstances) {
        this.exerciseInstances = exerciseInstances;
        return this;
    }

    public Exercise addExerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstances.add(exerciseInstance);
        exerciseInstance.setExercise(this);
        return this;
    }

    public Exercise removeExerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstances.remove(exerciseInstance);
        exerciseInstance.setExercise(null);
        return this;
    }

    public Set<Muscle> getMuscles() {
        return muscles;
    }

    public void setMuscles(Set<Muscle> muscles) {
        this.muscles = muscles;
    }

    public Exercise muscles(Set<Muscle> muscles) {
        this.muscles = muscles;
        return this;
    }

    public Exercise addMuscle(Muscle muscle) {
        this.muscles.add(muscle);
        muscle.getExercises().add(this);
        return this;
    }

    public Exercise removeMuscle(Muscle muscle) {
        this.muscles.remove(muscle);
        muscle.getExercises().remove(this);
        return this;
    }

    public ExerciseFamily getExerciseFamily() {
        return exerciseFamily;
    }

    public void setExerciseFamily(ExerciseFamily exerciseFamily) {
        this.exerciseFamily = exerciseFamily;
    }

    public Exercise exerciseFamily(ExerciseFamily exerciseFamily) {
        this.exerciseFamily = exerciseFamily;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        if (exercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", imageUri='" + imageUri + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
