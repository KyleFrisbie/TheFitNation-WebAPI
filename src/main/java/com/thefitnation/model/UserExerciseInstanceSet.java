package com.thefitnation.model;

import java.io.*;
import javax.persistence.*;

/**
 * <p></p>
 * Created by michael on 2/19/17.
 * @author michael menard
 * @version 0.1.0
 * @since 2/19/17
 */
@Entity
@Table(name = "exercise_instance_set")
public class UserExerciseInstanceSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "set_id")
    private Long id;

    @Column(name = "exercise_type")
    private String exerciseType;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "rep_quantity")
    private String repQuantity;

    @Column(name = "effort_quantity")
    private Long effortQuantity;

    @Column(name = "rest_amt")
    private Long rest;

    /* Joins */

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private UserExerciseInstance exerciseInstance;

    @ManyToOne
    @JoinColumn(name = "prescribed_set_id")
    private PrescribedExerciseSet exerciseSet;


    /* Constructors */

    public UserExerciseInstanceSet() { /* Required by Jpa */}





    /* Mutator */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRepQuantity() {
        return repQuantity;
    }

    public void setRepQuantity(String repQuantity) {
        this.repQuantity = repQuantity;
    }

    public Long getEffortQuantity() {
        return effortQuantity;
    }

    public void setEffortQuantity(Long effortQuantity) {
        this.effortQuantity = effortQuantity;
    }

    public Long getRest() {
        return rest;
    }

    public void setRest(Long rest) {
        this.rest = rest;
    }
}
