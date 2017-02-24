package com.thefitnation.model;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * <p></p>
 * Created by michael on 2/19/17.
 * @author michael menard
 * @version 0.1.0
 * @since 2/19/17
 */
@Entity
@Table(name = "user_exercise_instance_set")
public class PrescribedExerciseSet {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prescribed_set_id")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "order_number", nullable = false)
    private Integer order_number;

    @NotNull
    @Min(value = 1)
    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "rest")
    private Integer rest;

    /* Joins */

    @ManyToOne(targetEntity = PrescribedExerciseInstance.class)
    @JoinColumn(name = "exercise_instance_id")
    private PrescribedExerciseInstance prescribedExerciseInstance;

    @OneToMany(mappedBy = "exerciseSet")
    private List<UserExerciseInstanceSet> userExerciseInstanceSets;

    /* Constructors */

    public PrescribedExerciseSet() { /* Required by Jpa */ }

    /* Mutator */

    /**
     *
     * @return
     */
    public PrescribedExerciseInstance getPrescribedExerciseInstance() {
        return prescribedExerciseInstance;
    }

    /**
     *
     * @param prescribedExerciseInstance
     */
    public void setPrescribedExerciseInstance(PrescribedExerciseInstance prescribedExerciseInstance) {
        this.prescribedExerciseInstance = prescribedExerciseInstance;
    }

    /**
     *
     * @return
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getOrder_number() {
        return order_number;
    }

    /**
     *
     * @param order_number
     */
    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    /**
     *
     * @return
     */
    public Integer getReps() {
        return reps;
    }

    /**
     *
     * @param reps
     */
    public void setReps(Integer reps) {
        this.reps = reps;
    }

    /**
     *
     * @return
     */
    public Float getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     */
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public Integer getRest() {
        return rest;
    }

    /**
     *
     * @param rest
     */
    public void setRest(Integer rest) {
        this.rest = rest;
    }
}
