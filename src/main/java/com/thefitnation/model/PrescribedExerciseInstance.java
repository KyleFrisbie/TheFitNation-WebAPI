package com.thefitnation.model;

import com.thefitnation.model.enumeration.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;

/**
 * <p></p>
 * Created by michael on 2/19/17.
 * @author michael menard
 * @version 0.1.0
 * @since 2/19/17
 */
@Entity
@Table(name = "prescribed_exercise_instance")
public class PrescribedExerciseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_instance_id")
    private Long id;

    @Column(name = "rep_param")
    private RepParam repParam;

    @Column(name = "effort_param")
    private EffortParam effortParam;


    /* Joins */

    @ManyToOne(targetEntity = PrescribedWorkoutInstance.class)
    @JoinColumn(name = "instance_id")
    private PrescribedWorkoutInstance prescribedWorkoutInstance;

    @OneToMany(mappedBy = "prescribedExerciseInstance", targetEntity = PrescribedExerciseSet.class)
    private List<PrescribedExerciseSet> prescribedExerciseSets;

    @OneToMany(mappedBy = "prescribedExerciseInstance")
    private List<PrescribedExercise> prescribedExercises;

    /* Constructors */

    public PrescribedExerciseInstance() { /* Required by Jpa */ }




    /* Mutator */

    /**
     *
     * @return
     */
    public List<PrescribedExercise> getPrescribedExercises() {
        return prescribedExercises;
    }

    /**
     *
     * @param prescribedExercises
     */
    public void setPrescribedExercises(List<PrescribedExercise> prescribedExercises) {
        this.prescribedExercises = prescribedExercises;
    }

    /**
     *
     * @return
     */
    public List<PrescribedExerciseSet> getPrescribedExerciseSets() {
        return prescribedExerciseSets;
    }

    /**
     *
     * @param prescribedExerciseSets
     */
    public void setPrescribedExerciseSets(List<PrescribedExerciseSet> prescribedExerciseSets) {
        this.prescribedExerciseSets = prescribedExerciseSets;
    }

    /**
     *
     * @return
     */
    public PrescribedWorkoutInstance getPrescribedWorkoutInstance() {
        return prescribedWorkoutInstance;
    }

    /**
     *
     * @param prescribedWorkoutInstance
     */
    public void setPrescribedWorkoutInstance(PrescribedWorkoutInstance prescribedWorkoutInstance) {
        this.prescribedWorkoutInstance = prescribedWorkoutInstance;
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
    public RepParam getRepParam() {
        return repParam;
    }

    /**
     *
     * @param repParam
     */
    public void setRepParam(RepParam repParam) {
        this.repParam = repParam;
    }

    /**
     *
     * @return
     */
    public EffortParam getEffortParam() {
        return effortParam;
    }

    /**
     *
     * @param effortParam
     */
    public void setEffortParam(EffortParam effortParam) {
        this.effortParam = effortParam;
    }
}
