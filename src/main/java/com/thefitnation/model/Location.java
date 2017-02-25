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
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    /* Joins */

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gymId")
    private Gym gym;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;






    /* Constructor */

    public Location() { /* Required by Jpa */ }

    public Location(Long latitude, Long longitude, Address address) {

    }



    /* Mutator */

    /**
     *
     * @return
     */
    public Gym getGym() {
        return gym;
    }

    /**
     *
     * @param gym
     */
    public void setGym(Gym gym) {
        this.gym = gym;
    }

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
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
