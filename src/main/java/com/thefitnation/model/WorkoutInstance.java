package com.thefitnation.model;

import java.time.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by michael on 2/19/17.
 */
@Entity
@Table(name = "workout_instance")
public class WorkoutInstance {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdDate;

    @NotNull
    @Column(name = "prescribed_rest_period", nullable = false)
    private Long restPeriod;

    @NotNull
    @Column(name = "order_number", nullable = false)
    private Long orderNumber;

    /* Joins */



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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getRestPeriod() {
        return restPeriod;
    }

    public void setRestPeriod(Long restPeriod) {
        this.restPeriod = restPeriod;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
}
