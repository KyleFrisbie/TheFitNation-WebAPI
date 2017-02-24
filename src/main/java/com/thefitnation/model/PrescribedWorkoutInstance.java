package com.thefitnation.model;

import java.io.*;
import java.time.*;
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
@Table(name = "prescribed_workout_instance")
public class PrescribedWorkoutInstance implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "instance_id")
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate created_on;

    private LocalDate last_updated;

    private Integer prescribedRestAmount;

    private Integer instanceOrderNumber;

    private String name;






    /* Joins */

    @ManyToOne(targetEntity = PrescribedWorkoutTemplate.class)
    @JoinColumn(name = "template_id")
    private PrescribedWorkoutTemplate prescribedWorkoutTemplate;

    @OneToMany(mappedBy = "prescribedWorkoutInstance", targetEntity = PrescribedExerciseInstance.class)
    private List<PrescribedExerciseInstance> prescribedExerciseInstance;

    @ManyToMany(mappedBy = "workoutInstance")
    private List<UserWorkoutInstance> userWorkoutInstances;

    /* Constructors */

    public PrescribedWorkoutInstance() { /* Required by Jpa */ }






    /* Mutator */

    /**
     *
     * @return
     */
    public PrescribedWorkoutTemplate getPrescribedWorkoutTemplate() {
        return prescribedWorkoutTemplate;
    }

    /**
     *
     * @param prescribedWorkoutTemplate
     */
    public void setPrescribedWorkoutTemplate(PrescribedWorkoutTemplate prescribedWorkoutTemplate) {
        this.prescribedWorkoutTemplate = prescribedWorkoutTemplate;
    }

}
