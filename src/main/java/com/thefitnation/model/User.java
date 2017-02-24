package com.thefitnation.model;

import com.thefitnation.model.enumeration.*;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * <p></p>
 * Created by michael on 2/19/17.
 * @author michael menard
 * @version 0.1.0
 * @since 2/19/17
 */
@Entity
@Table(name = "user")
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate createDate;

    @NotNull
    @Column(name = "last_login", nullable = false)
    private LocalDate lastLogin;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "height")
    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevel skillLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", nullable = false)
    private UnitOfMeasure units;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;


    /* JOINS */

    @OneToOne
    @JoinColumn(name = "cred_id")
    private UserCredential userCredential;

    @OneToMany(mappedBy = "user")
    private List<UserWeight> userBodyWeight;


    @ManyToMany(mappedBy="users",fetch=FetchType.EAGER)
    private Set<Gym> gyms;

    @OneToMany(mappedBy = "user", targetEntity = PrescribedWorkoutTemplate.class)
    private List<PrescribedWorkoutTemplate> usersWorkoutTemplate;



    /* Constructors */

    private User() { /* Required by Spring Boot */ }




    /* Mutator */

    /**
     *
     * @return
     */
    public List<PrescribedWorkoutTemplate> getUsersWorkoutTemplate() {
        return usersWorkoutTemplate;
    }

    /**
     *
     * @param usersWorkoutTemplate
     */
    public void setUsersWorkoutTemplate(List<PrescribedWorkoutTemplate> usersWorkoutTemplate) {
        this.usersWorkoutTemplate = usersWorkoutTemplate;
    }

    /**
     *
     * @return
     */
    public List<UserWeight> getUserBodyWeight() {
        return userBodyWeight;
    }

    /**
     *
     * @param userBodyWeight
     */
    public void setUserBodyWeight(List<UserWeight> userBodyWeight) {
        this.userBodyWeight = userBodyWeight;
    }

    /**
     *
     * @return
     */
    public Set<Gym> getGyms() {
        return gyms;
    }

    /**
     *
     * @param gyms
     */
    public void setGyms(Set<Gym> gyms) {
        this.gyms = gyms;
    }

    /**
     *
     * @return
     */
    public UserCredential getUserCredential() {
        return userCredential;
    }

    /**
     *
     * @param userCredential
     */
    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
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
    public Long getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */
    public LocalDate getLastLogin() {
        return lastLogin;
    }

    /**
     *
     * @param lastLogin
     */
    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public Gender getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     *
     * @return
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @return
     */
    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    /**
     *
     * @param skillLevel
     */
    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    /**
     *
     * @return
     */
    public UnitOfMeasure getUnits() {
        return units;
    }

    /**
     *
     * @param units
     */
    public void setUnits(UnitOfMeasure units) {
        this.units = units;
    }

    /**
     *
     * @return
     */
    public Boolean getActive() {
        return isActive;
    }

    /**
     *
     * @param active
     */
    public void setActive(Boolean active) {
        isActive = active;
    }


}
