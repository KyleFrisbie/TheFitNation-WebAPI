package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

/**
 * A BodyPart.
 */
@Entity
@Table(name = "body_part", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BodyPart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "bodyPart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Muscle> muscles = new HashSet<>();

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

    public BodyPart name(String name) {
        this.name = name;
        return this;
    }

    public Set<Muscle> getMuscles() {
        return muscles;
    }

    public void setMuscles(Set<Muscle> muscles) {
        this.muscles = muscles;
    }

    public BodyPart muscles(Set<Muscle> muscles) {
        this.muscles = muscles;
        return this;
    }

    public BodyPart addMuscle(Muscle muscle) {
        this.muscles.add(muscle);
        muscle.setBodyPart(this);
        return this;
    }

    public BodyPart removeMuscle(Muscle muscle) {
        this.muscles.remove(muscle);
        muscle.setBodyPart(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyPart bodyPart = (BodyPart) o;
        if (bodyPart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bodyPart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BodyPart{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
