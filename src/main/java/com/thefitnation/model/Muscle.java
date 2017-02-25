package com.thefitnation.model;

import com.thefitnation.model.enumeration.*;
import java.io.*;
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
@Table(name = "muscle")
public class Muscle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "muscle_id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "body_part")
    private BodyPart bodyPart;

    /* Joins */

    @ManyToMany
    @JoinTable(name="exercise_muscle",
            joinColumns=  @JoinColumn(name="exercise_id"),
            inverseJoinColumns= @JoinColumn(name="muscle_id"))
    private List<PrescribedExercise> exerciseList;

    /* Constructors */

    /**
     *
     */
    public Muscle() { /* Required By Jpa */ }

    public Muscle(String name, BodyPart part) {
        this.name = name;
        this.bodyPart = part;
    }

    /* Mutator */

    /**
     *
     * @return
     */
    public List<PrescribedExercise> getExerciseList() {
        return exerciseList;
    }

    /**
     *
     * @param exerciseList
     */
    public void setExerciseList(List<PrescribedExercise> exerciseList) {
        this.exerciseList = exerciseList;
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
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    /**
     *
     * @param bodyPart
     */
    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }
}
