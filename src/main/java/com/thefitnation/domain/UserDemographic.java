package com.thefitnation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.thefitnation.domain.enumeration.Gender;

import com.thefitnation.domain.enumeration.SkillLevel;

import com.thefitnation.domain.enumeration.UnitOfMeasure;

/**
 * A UserDemographic.
 */
@Entity
@Table(name = "user_demographic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userdemographic")
public class UserDemographic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @NotNull
    @Column(name = "dob", nullable = false)
    private ZonedDateTime dob;

    @Column(name = "height")
    private Float height;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level", nullable = false)
    private SkillLevel skill_level;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", nullable = false)
    private UnitOfMeasure unit_of_measure;

    @NotNull
    @Column(name = "last_login", nullable = false)
    private ZonedDateTime last_login;

    @NotNull
    @Column(name = "join_date", nullable = false)
    private ZonedDateTime join_date;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean is_active;

    @OneToMany(mappedBy = "userDemographic")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserWeight> userWeights = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_demographic_gym",
               joinColumns = @JoinColumn(name="user_demographics_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="gyms_id", referencedColumnName="ID"))
    private Set<Gym> gyms = new HashSet<>();

    @OneToMany(mappedBy = "userDemographic")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutInstance> workoutInstances = new HashSet<>();

    @OneToMany(mappedBy = "userDemographic")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkoutTemplate> workoutTemplates = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private WorkoutLog workoutLog;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public UserDemographic first_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public UserDemographic last_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public ZonedDateTime getDob() {
        return dob;
    }

    public UserDemographic dob(ZonedDateTime dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
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

    public SkillLevel getSkill_level() {
        return skill_level;
    }

    public UserDemographic skill_level(SkillLevel skill_level) {
        this.skill_level = skill_level;
        return this;
    }

    public void setSkill_level(SkillLevel skill_level) {
        this.skill_level = skill_level;
    }

    public UnitOfMeasure getUnit_of_measure() {
        return unit_of_measure;
    }

    public UserDemographic unit_of_measure(UnitOfMeasure unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
        return this;
    }

    public void setUnit_of_measure(UnitOfMeasure unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }

    public ZonedDateTime getLast_login() {
        return last_login;
    }

    public UserDemographic last_login(ZonedDateTime last_login) {
        this.last_login = last_login;
        return this;
    }

    public void setLast_login(ZonedDateTime last_login) {
        this.last_login = last_login;
    }

    public ZonedDateTime getJoin_date() {
        return join_date;
    }

    public UserDemographic join_date(ZonedDateTime join_date) {
        this.join_date = join_date;
        return this;
    }

    public void setJoin_date(ZonedDateTime join_date) {
        this.join_date = join_date;
    }

    public Boolean isIs_active() {
        return is_active;
    }

    public UserDemographic is_active(Boolean is_active) {
        this.is_active = is_active;
        return this;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Set<UserWeight> getUserWeights() {
        return userWeights;
    }

    public UserDemographic userWeights(Set<UserWeight> userWeights) {
        this.userWeights = userWeights;
        return this;
    }

    public UserDemographic addUserWeight(UserWeight userWeight) {
        userWeights.add(userWeight);
        userWeight.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeUserWeight(UserWeight userWeight) {
        userWeights.remove(userWeight);
        userWeight.setUserDemographic(null);
        return this;
    }

    public void setUserWeights(Set<UserWeight> userWeights) {
        this.userWeights = userWeights;
    }

    public Set<Gym> getGyms() {
        return gyms;
    }

    public UserDemographic gyms(Set<Gym> gyms) {
        this.gyms = gyms;
        return this;
    }

    public UserDemographic addGym(Gym gym) {
        gyms.add(gym);
        gym.getUserDemographics().add(this);
        return this;
    }

    public UserDemographic removeGym(Gym gym) {
        gyms.remove(gym);
        gym.getUserDemographics().remove(this);
        return this;
    }

    public void setGyms(Set<Gym> gyms) {
        this.gyms = gyms;
    }

    public Set<WorkoutInstance> getWorkoutInstances() {
        return workoutInstances;
    }

    public UserDemographic workoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
        return this;
    }

    public UserDemographic addWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.add(workoutInstance);
        workoutInstance.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.remove(workoutInstance);
        workoutInstance.setUserDemographic(null);
        return this;
    }

    public void setWorkoutInstances(Set<WorkoutInstance> workoutInstances) {
        this.workoutInstances = workoutInstances;
    }

    public Set<WorkoutTemplate> getWorkoutTemplates() {
        return workoutTemplates;
    }

    public UserDemographic workoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
        return this;
    }

    public UserDemographic addWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.add(workoutTemplate);
        workoutTemplate.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.remove(workoutTemplate);
        workoutTemplate.setUserDemographic(null);
        return this;
    }

    public void setWorkoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
    }

    public WorkoutLog getWorkoutLog() {
        return workoutLog;
    }

    public UserDemographic workoutLog(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;
        return this;
    }

    public void setWorkoutLog(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;
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
            ", first_name='" + first_name + "'" +
            ", last_name='" + last_name + "'" +
            ", gender='" + gender + "'" +
            ", dob='" + dob + "'" +
            ", height='" + height + "'" +
            ", skill_level='" + skill_level + "'" +
            ", unit_of_measure='" + unit_of_measure + "'" +
            ", last_login='" + last_login + "'" +
            ", join_date='" + join_date + "'" +
            ", is_active='" + is_active + "'" +
            '}';
    }
}
