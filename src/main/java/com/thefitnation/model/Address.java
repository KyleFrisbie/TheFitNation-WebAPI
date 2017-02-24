package com.thefitnation.model;

import java.io.*;
import javax.persistence.*;

/**
 * <p></p>
 * Created by michael on 2/19/17.
 * @author michael menard
 * @version 0.1.0
 * @since 2/19/17
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "streetAddress1")
    private String streetAddress1;

    @Column(name = "streetAddress2")
    private String streetAddress2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;




    /* Joins */

    @OneToOne(mappedBy = "address")
    private Location location;




    /* Constructor */

    public Address() { /* Required by Jpa */ }




    /* Mutator */

    /**
     *
     * @return
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     *
     * @return
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     *
     * @param addressId
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @return
     */
    public String getStreetAddress1() {
        return streetAddress1;
    }

    /**
     *
     * @param streetAddress1
     */
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    /**
     *
     * @return
     */
    public String getStreetAddress2() {
        return streetAddress2;
    }

    /**
     *
     * @param streetAddress2
     */
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
