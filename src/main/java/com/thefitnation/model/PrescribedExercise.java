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
@Table(name = "prescribed_exercise")
public class PrescribedExercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "skill_level", nullable = false)
    private SkillLevel skillLevel;


    /* Joins */

    @ManyToOne(targetEntity = PrescribedExerciseInstance.class)
    @JoinColumn(name = "exercise_instance_id")
    private PrescribedExerciseInstance prescribedExerciseInstance;

    @ManyToMany(mappedBy = "exerciseList")
    private Set<Muscle> muscles;




    /* Constructors */

    /**
     *
     */
    public PrescribedExercise() { /* Constructors */ }

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
    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    /**
     *
     * @param skillLevel
     */
    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }
}
