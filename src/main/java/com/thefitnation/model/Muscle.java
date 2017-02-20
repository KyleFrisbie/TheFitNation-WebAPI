package com.thefitnation.model;

import com.thefitnation.model.enumeration.*;
import java.io.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by michael on 2/19/17.
 */
@Entity
@Table(name = "exercise")
public class Muscle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "body_part")
    private BodyPart bodyPart;

    /* Joins */

    // TODO: 2/19/17 many-to-many with exercise


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

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }
}
