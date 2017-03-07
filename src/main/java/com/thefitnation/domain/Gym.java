package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "notes")
    private String notes;

    @ManyToMany(mappedBy = "gyms")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserDemographic> userDemographics = new HashSet<>();

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Location location;

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

    public String getNotes() {
        return notes;
    }

    public Gym notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<UserDemographic> getUserDemographics() {
        return userDemographics;
    }

    public Gym userDemographics(Set<UserDemographic> userDemographics) {
        this.userDemographics = userDemographics;
        return this;
    }

    public Gym addUserDemographic(UserDemographic userDemographic) {
        this.userDemographics.add(userDemographic);
        userDemographic.getGyms().add(this);
        return this;
    }

    public Gym removeUserDemographic(UserDemographic userDemographic) {
        this.userDemographics.remove(userDemographic);
        userDemographic.getGyms().remove(this);
        return this;
    }

    public void setUserDemographics(Set<UserDemographic> userDemographics) {
        this.userDemographics = userDemographics;
    }

    public Location getLocation() {
        return location;
    }

    public Gym location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
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
            ", notes='" + notes + "'" +
            '}';
    }
}
