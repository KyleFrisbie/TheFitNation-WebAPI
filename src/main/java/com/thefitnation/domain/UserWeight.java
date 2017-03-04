package com.thefitnation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "weight_date", nullable = false)
    private ZonedDateTime weight_date;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Float weight;

    @ManyToOne
    @NotNull
    private UserDemographic userDemographic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getWeight_date() {
        return weight_date;
    }

    public UserWeight weight_date(ZonedDateTime weight_date) {
        this.weight_date = weight_date;
        return this;
    }

    public void setWeight_date(ZonedDateTime weight_date) {
        this.weight_date = weight_date;
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
            ", weight_date='" + weight_date + "'" +
            ", weight='" + weight + "'" +
            '}';
    }
}
