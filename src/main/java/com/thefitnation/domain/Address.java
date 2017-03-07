package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "street_address_one", nullable = false)
    private String streetAddressOne;

    @Column(name = "street_address_two")
    private String streetAddressTwo;

    @NotNull
    @Size(min = 1)
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Size(min = 1)
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddressOne() {
        return streetAddressOne;
    }

    public Address streetAddressOne(String streetAddressOne) {
        this.streetAddressOne = streetAddressOne;
        return this;
    }

    public void setStreetAddressOne(String streetAddressOne) {
        this.streetAddressOne = streetAddressOne;
    }

    public String getStreetAddressTwo() {
        return streetAddressTwo;
    }

    public Address streetAddressTwo(String streetAddressTwo) {
        this.streetAddressTwo = streetAddressTwo;
        return this;
    }

    public void setStreetAddressTwo(String streetAddressTwo) {
        this.streetAddressTwo = streetAddressTwo;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Location getLocation() {
        return location;
    }

    public Address location(Location location) {
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
        Address address = (Address) o;
        if (address.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", streetAddressOne='" + streetAddressOne + "'" +
            ", streetAddressTwo='" + streetAddressTwo + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipCode='" + zipCode + "'" +
            '}';
    }
}
