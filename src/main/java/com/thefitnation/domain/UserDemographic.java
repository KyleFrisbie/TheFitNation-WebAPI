package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.thefitnation.domain.enumeration.Gender;

import com.thefitnation.domain.enumeration.UnitOfMeasure;

/**
 * A UserDemographic.
 */
@Entity
@Table(name = "user_demographic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDemographic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @NotNull
    @Column(name = "last_login", nullable = false)
    private LocalDate lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "height")
    private Float height;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", nullable = false)
    private UnitOfMeasure unitOfMeasure;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_demographic_gym",
               joinColumns = @JoinColumn(name="user_demographics_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="gyms_id", referencedColumnName="id"))
    private Set<Gym> gyms = new HashSet<>();

    @OneToMany(mappedBy = "userDemographic")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWeight> userWeights = new HashSet<>();

    @OneToMany(mappedBy = "userDemographic")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutTemplate> workoutTemplates = new HashSet<>();

    @OneToMany(mappedBy = "userDemographic")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWorkoutTemplate> userWorkoutTemplates = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private SkillLevel skillLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public UserDemographic createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public UserDemographic lastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Gender getGender() {
        return gender;
    }

    public UserDemographic gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public UserDemographic dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Float getHeight() {
        return height;
    }

    public UserDemographic height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public UserDemographic unitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public User getUser() {
        return user;
    }

    public UserDemographic user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Gym> getGyms() {
        return gyms;
    }

    public UserDemographic gyms(Set<Gym> gyms) {
        this.gyms = gyms;
        return this;
    }

    public UserDemographic addGym(Gym gym) {
        this.gyms.add(gym);
        gym.getUserDemographics().add(this);
        return this;
    }

    public UserDemographic removeGym(Gym gym) {
        this.gyms.remove(gym);
        gym.getUserDemographics().remove(this);
        return this;
    }

    public void setGyms(Set<Gym> gyms) {
        this.gyms = gyms;
    }

    public Set<UserWeight> getUserWeights() {
        return userWeights;
    }

    public UserDemographic userWeights(Set<UserWeight> userWeights) {
        this.userWeights = userWeights;
        return this;
    }

    public UserDemographic addUserWeight(UserWeight userWeight) {
        this.userWeights.add(userWeight);
        userWeight.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeUserWeight(UserWeight userWeight) {
        this.userWeights.remove(userWeight);
        userWeight.setUserDemographic(null);
        return this;
    }

    public void setUserWeights(Set<UserWeight> userWeights) {
        this.userWeights = userWeights;
    }

    public Set<WorkoutTemplate> getWorkoutTemplates() {
        return workoutTemplates;
    }

    public UserDemographic workoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
        return this;
    }

    public UserDemographic addWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplates.add(workoutTemplate);
        workoutTemplate.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplates.remove(workoutTemplate);
        workoutTemplate.setUserDemographic(null);
        return this;
    }

    public void setWorkoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
    }

    public Set<UserWorkoutTemplate> getUserWorkoutTemplates() {
        return userWorkoutTemplates;
    }

    public UserDemographic userWorkoutTemplates(Set<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
        return this;
    }

    public UserDemographic addUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        this.userWorkoutTemplates.add(userWorkoutTemplate);
        userWorkoutTemplate.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        this.userWorkoutTemplates.remove(userWorkoutTemplate);
        userWorkoutTemplate.setUserDemographic(null);
        return this;
    }

    public void setUserWorkoutTemplates(Set<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public UserDemographic skillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
        return this;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDemographic userDemographic = (UserDemographic) o;
        if (userDemographic.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userDemographic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserDemographic{" +
            "id=" + id +
            ", createdOn='" + createdOn + "'" +
            ", lastLogin='" + lastLogin + "'" +
            ", gender='" + gender + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", height='" + height + "'" +
            ", unitOfMeasure='" + unitOfMeasure + "'" +
            '}';
    }
}
