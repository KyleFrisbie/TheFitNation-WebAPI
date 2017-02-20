package com.thefitnation.model;

import com.thefitnation.model.enumeration.*;
import java.io.*;
import javax.persistence.*;

/**
 * Created by michael on 2/19/17.
 */
@Entity
@Table(name = "exercise_instance")
public class ExerciseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rep_param")
    private RepParam repParam;

    @Column(name = "effort_param")
    private EffortParam effortParam;


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

    public RepParam getRepParam() {
        return repParam;
    }

    public void setRepParam(RepParam repParam) {
        this.repParam = repParam;
    }

    public EffortParam getEffortParam() {
        return effortParam;
    }

    public void setEffortParam(EffortParam effortParam) {
        this.effortParam = effortParam;
    }
}
