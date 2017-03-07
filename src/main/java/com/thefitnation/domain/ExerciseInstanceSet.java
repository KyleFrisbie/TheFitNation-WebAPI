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
 * A ExerciseInstanceSet.
 */
@Entity
@Table(name = "exercise_instance_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExerciseInstanceSet implements Serializable {

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
    @Column(name = "req_quantity", nullable = false)
    private Float reqQuantity;

    @NotNull
    @Column(name = "effort_quantity", nullable = false)
    private Float effortQuantity;

    @Column(name = "rest_time")
    private Float restTime;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private ExerciseInstance exerciseInstance;

    @OneToMany(mappedBy = "exerciseInstanceSet")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExerciseInstanceSet> userExerciseInstanceSets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public ExerciseInstanceSet orderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Float getReqQuantity() {
        return reqQuantity;
    }

    public ExerciseInstanceSet reqQuantity(Float reqQuantity) {
        this.reqQuantity = reqQuantity;
        return this;
    }

    public void setReqQuantity(Float reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public Float getEffortQuantity() {
        return effortQuantity;
    }

    public ExerciseInstanceSet effortQuantity(Float effortQuantity) {
        this.effortQuantity = effortQuantity;
        return this;
    }

    public void setEffortQuantity(Float effortQuantity) {
        this.effortQuantity = effortQuantity;
    }

    public Float getRestTime() {
        return restTime;
    }

    public ExerciseInstanceSet restTime(Float restTime) {
        this.restTime = restTime;
        return this;
    }

    public void setRestTime(Float restTime) {
        this.restTime = restTime;
    }

    public String getNotes() {
        return notes;
    }

    public ExerciseInstanceSet notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ExerciseInstance getExerciseInstance() {
        return exerciseInstance;
    }

    public ExerciseInstanceSet exerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstance = exerciseInstance;
        return this;
    }

    public void setExerciseInstance(ExerciseInstance exerciseInstance) {
        this.exerciseInstance = exerciseInstance;
    }

    public Set<UserExerciseInstanceSet> getUserExerciseInstanceSets() {
        return userExerciseInstanceSets;
    }

    public ExerciseInstanceSet userExerciseInstanceSets(Set<UserExerciseInstanceSet> userExerciseInstanceSets) {
        this.userExerciseInstanceSets = userExerciseInstanceSets;
        return this;
    }

    public ExerciseInstanceSet addUserExerciseInstanceSet(UserExerciseInstanceSet userExerciseInstanceSet) {
        this.userExerciseInstanceSets.add(userExerciseInstanceSet);
        userExerciseInstanceSet.setExerciseInstanceSet(this);
        return this;
    }

    public ExerciseInstanceSet removeUserExerciseInstanceSet(UserExerciseInstanceSet userExerciseInstanceSet) {
        this.userExerciseInstanceSets.remove(userExerciseInstanceSet);
        userExerciseInstanceSet.setExerciseInstanceSet(null);
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
        ExerciseInstanceSet exerciseInstanceSet = (ExerciseInstanceSet) o;
        if (exerciseInstanceSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exerciseInstanceSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseInstanceSet{" +
            "id=" + id +
            ", orderNumber='" + orderNumber + "'" +
            ", reqQuantity='" + reqQuantity + "'" +
            ", effortQuantity='" + effortQuantity + "'" +
            ", restTime='" + restTime + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
