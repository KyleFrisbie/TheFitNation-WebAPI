package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BodyPart.
 */
@Entity
@Table(name = "body_part")
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

    @OneToOne(mappedBy = "bodyPart")
    @JsonIgnore
    private Muscle muscle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BodyPart name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public BodyPart muscle(Muscle muscle) {
        this.muscle = muscle;
        return this;
    }

    public void setMuscle(Muscle muscle) {
        this.muscle = muscle;
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
