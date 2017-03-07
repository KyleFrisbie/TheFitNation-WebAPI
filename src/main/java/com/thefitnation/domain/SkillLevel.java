package com.thefitnation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SkillLevel.
 */
@Entity
@Table(name = "skill_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private com.thefitnation.domain.enumeration.SkillLevel level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.thefitnation.domain.enumeration.SkillLevel getLevel() {
        return level;
    }

    public SkillLevel level(com.thefitnation.domain.enumeration.SkillLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(com.thefitnation.domain.enumeration.SkillLevel level) {
        this.level = level;
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
