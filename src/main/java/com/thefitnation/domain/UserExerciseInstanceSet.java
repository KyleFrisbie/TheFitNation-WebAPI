package com.thefitnation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserExerciseInstanceSet.
 */
@Entity
@Table(name = "user_exercise_instance_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExerciseInstanceSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @NotNull
    @Column(name = "rep_quantity", nullable = false)
    private Float repQuantity;

    @NotNull
    @Column(name = "effort_quantity", nullable = false)
    private Float effortQuantity;

    @Column(name = "rest_time")
    private Float restTime;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private UserExerciseInstance userExerciseInstance;

    @ManyToOne
    private ExerciseInstanceSet exerciseInstanceSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public UserExerciseInstanceSet orderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Float getRepQuantity() {
        return repQuantity;
    }

    public UserExerciseInstanceSet repQuantity(Float repQuantity) {
        this.repQuantity = repQuantity;
        return this;
    }

    public void setRepQuantity(Float repQuantity) {
        this.repQuantity = repQuantity;
    }

    public Float getEffortQuantity() {
        return effortQuantity;
    }

    public UserExerciseInstanceSet effortQuantity(Float effortQuantity) {
        this.effortQuantity = effortQuantity;
        return this;
    }

    public void setEffortQuantity(Float effortQuantity) {
        this.effortQuantity = effortQuantity;
    }

    public Float getRestTime() {
        return restTime;
    }

    public UserExerciseInstanceSet restTime(Float restTime) {
        this.restTime = restTime;
        return this;
    }

    public void setRestTime(Float restTime) {
        this.restTime = restTime;
    }

    public String getNotes() {
        return notes;
    }

    public UserExerciseInstanceSet notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UserExerciseInstance getUserExerciseInstance() {
        return userExerciseInstance;
    }

    public UserExerciseInstanceSet userExerciseInstance(UserExerciseInstance userExerciseInstance) {
        this.userExerciseInstance = userExerciseInstance;
        return this;
    }

    public void setUserExerciseInstance(UserExerciseInstance userExerciseInstance) {
        this.userExerciseInstance = userExerciseInstance;
    }

    public ExerciseInstanceSet getExerciseInstanceSet() {
        return exerciseInstanceSet;
    }

    public UserExerciseInstanceSet exerciseInstanceSet(ExerciseInstanceSet exerciseInstanceSet) {
        this.exerciseInstanceSet = exerciseInstanceSet;
        return this;
    }

    public void setExerciseInstanceSet(ExerciseInstanceSet exerciseInstanceSet) {
        this.exerciseInstanceSet = exerciseInstanceSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExerciseInstanceSet userExerciseInstanceSet = (UserExerciseInstanceSet) o;
        if (userExerciseInstanceSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExerciseInstanceSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExerciseInstanceSet{" +
            "id=" + id +
            ", orderNumber='" + orderNumber + "'" +
            ", repQuantity='" + repQuantity + "'" +
            ", effortQuantity='" + effortQuantity + "'" +
            ", restTime='" + restTime + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
