package com.thefitnation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gym.
 */
@Entity
@Table(name = "gym")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gym implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "gym_user_demographic",
               joinColumns = @JoinColumn(name="gyms_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="user_demographics_id", referencedColumnName="ID"))
    private Set<UserDemographic> userDemographics = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Gym name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public Gym location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<UserDemographic> getUserDemographics() {
        return userDemographics;
    }

    public Gym userDemographics(Set<UserDemographic> userDemographics) {
        this.userDemographics = userDemographics;
        return this;
    }

    public Gym addUserDemographic(UserDemographic userDemographic) {
        userDemographics.add(userDemographic);
        userDemographic.getGyms().add(this);
        return this;
    }

    public Gym removeUserDemographic(UserDemographic userDemographic) {
        userDemographics.remove(userDemographic);
        userDemographic.getGyms().remove(this);
        return this;
    }

    public void setUserDemographics(Set<UserDemographic> userDemographics) {
        this.userDemographics = userDemographics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gym gym = (Gym) o;
        if (gym.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gym.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gym{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", location='" + location + "'" +
            '}';
    }
}
