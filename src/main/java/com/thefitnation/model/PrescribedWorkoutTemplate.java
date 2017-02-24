package com.thefitnation.model;

import com.thefitnation.model.enumeration.*;
import java.io.*;
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
@Table(name = "prescribed_workout_template")
public class PrescribedWorkoutTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "template_id")
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate created_on;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private LocalDate last_updated;

    @NotNull
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "skill_level")
    private SkillLevel skillLevel;


    /* Joins */

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "prescribedWorkoutTemplate")
    private List<PrescribedWorkoutInstance> prescribedWorkoutInstances;

    @OneToMany(mappedBy = "prescribedWorkoutTemplate")
    private List<UserWorkoutTemplate> userWorkoutTemplates;



    /* Constructors */

    /**
     *
     */
    public PrescribedWorkoutTemplate() { /* Required by Jpa */ }






    /* Mutator */

    /**
     *
     * @return
     */
    public List<UserWorkoutTemplate> getUserWorkoutTemplates() {
        return userWorkoutTemplates;
    }

    /**
     *
     * @param userWorkoutTemplates
     */
    public void setUserWorkoutTemplates(List<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
    }

    /**
     *
     * @return
     */
    public List<PrescribedWorkoutInstance> getPrescribedWorkoutInstances() {
        return prescribedWorkoutInstances;
    }

    /**
     *
     * @param prescribedWorkoutInstances
     */
    public void setPrescribedWorkoutInstances(List<PrescribedWorkoutInstance> prescribedWorkoutInstances) {
        this.prescribedWorkoutInstances = prescribedWorkoutInstances;
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
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public Boolean getPrivate() {
        return isPrivate;
    }

    /**
     *
     * @param aPrivate
     */
    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
    public LocalDate getCreated_on() {
        return created_on;
    }

    /**
     *
     * @param created_on
     */
    public void setCreated_on(LocalDate created_on) {
        this.created_on = created_on;
    }

    /**
     *
     * @return
     */
    public LocalDate getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(LocalDate last_updated) {
        this.last_updated = last_updated;
    }
}
