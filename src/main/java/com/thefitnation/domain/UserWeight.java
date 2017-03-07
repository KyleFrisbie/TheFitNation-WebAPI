package com.thefitnation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A UserWeight.
 */
@Entity
@Table(name = "user_weight")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserWeight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "weight_date", nullable = false)
    private LocalDate weightDate;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "weight", nullable = false)
    private Float weight;

    @ManyToOne(optional = false)
    @NotNull
    private UserDemographic userDemographic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWeightDate() {
        return weightDate;
    }

    public UserWeight weightDate(LocalDate weightDate) {
        this.weightDate = weightDate;
        return this;
    }

    public void setWeightDate(LocalDate weightDate) {
        this.weightDate = weightDate;
    }

    public Float getWeight() {
        return weight;
    }

    public UserWeight weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public UserDemographic getUserDemographic() {
        return userDemographic;
    }

    public UserWeight userDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
        return this;
    }

    public void setUserDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserWeight userWeight = (UserWeight) o;
        if (userWeight.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWeight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWeight{" +
            "id=" + id +
            ", weightDate='" + weightDate + "'" +
            ", weight='" + weight + "'" +
            '}';
    }
}
