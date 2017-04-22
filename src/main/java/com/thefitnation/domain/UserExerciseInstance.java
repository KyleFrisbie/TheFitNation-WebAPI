package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserExerciseInstance.
 */
@Entity
@Table(name = "user_exercise_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExerciseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private UserWorkoutInstance userWorkoutInstance;

    @ManyToOne
    private ExerciseInstance exerciseInstance;

    @OneToMany(mappedBy = "userExerciseInstance", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExerciseInstanceSet> userExerciseInstanceSets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public UserExerciseInstance notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UserWorkoutInstance getUserWorkoutInstance() {
        return userWorkoutInstance;
    }

    public UserExerciseInstance userWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        this.userWorkoutInstance = userWorkoutInstance;
        return this;
    }

    public void setUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        this.userWorkoutInstance = userWorkoutInstance;
    }

    public ExerciseInstance getExerciseInstance() {
        return exerciseInstance;
    }

    public UserExerciseInstance exerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstance = exerciseInstance;
        return this;
    }

    public void setExerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstance = exerciseInstance;
    }

    public Set<UserExerciseInstanceSet> getUserExerciseInstanceSets() {
        return userExerciseInstanceSets;
    }

    public UserExerciseInstance userExerciseInstanceSets(Set<UserExerciseInstanceSet> userExerciseInstanceSets) {
        this.userExerciseInstanceSets = userExerciseInstanceSets;
        return this;
    }

    public UserExerciseInstance addUserExerciseInstanceSet(UserExerciseInstanceSet userExerciseInstanceSet) {
        this.userExerciseInstanceSets.add(userExerciseInstanceSet);
        userExerciseInstanceSet.setUserExerciseInstance(this);
        return this;
    }

    public UserExerciseInstance removeUserExerciseInstanceSet(UserExerciseInstanceSet userExerciseInstanceSet) {
        this.userExerciseInstanceSets.remove(userExerciseInstanceSet);
        userExerciseInstanceSet.setUserExerciseInstance(null);
        return this;
    }

    public void setUserExerciseInstanceSets(Set<UserExerciseInstanceSet> userExerciseInstanceSets) {
        this.userExerciseInstanceSets = userExerciseInstanceSets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExerciseInstance userExerciseInstance = (UserExerciseInstance) o;
        if (userExerciseInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExerciseInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExerciseInstance{" +
            "id=" + id +
            ", notes='" + notes + "'" +
            '}';
    }
}
