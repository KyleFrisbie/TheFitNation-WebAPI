package com.thefitnation.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

/**
 * A SkillLevel.
 */
@Entity
@Table(name = "skill_level", uniqueConstraints = @UniqueConstraint(columnNames = {"level"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "level", nullable = false)
    private String level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public SkillLevel level(String level) {
        this.level = level;
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
        SkillLevel skillLevel = (SkillLevel) o;
        if (skillLevel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, skillLevel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SkillLevel{" +
            "id=" + id +
            ", level='" + level + "'" +
            '}';
    }
}
