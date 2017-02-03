package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.service.UserDemographicService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.thefitnation.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.SkillLevel;
import com.thefitnation.domain.enumeration.UnitOfMeasure;
/**
 * Test class for the UserDemographicResource REST controller.
 *
 * @see UserDemographicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserDemographicResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_LOGIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_LOGIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final ZonedDateTime DEFAULT_DOB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final SkillLevel DEFAULT_SKILL_LEVEL = SkillLevel.Beginner;
    private static final SkillLevel UPDATED_SKILL_LEVEL = SkillLevel.Intermediate;

    private static final UnitOfMeasure DEFAULT_UNIT_OF_MEASURE = UnitOfMeasure.Imperial;
    private static final UnitOfMeasure UPDATED_UNIT_OF_MEASURE = UnitOfMeasure.Metric;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private UserDemographicRepository userDemographicRepository;

    @Inject
    private UserDemographicService userDemographicService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserDemographicMockMvc;

    private UserDemographic userDemographic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserDemographicResource userDemographicResource = new UserDemographicResource();
        ReflectionTestUtils.setField(userDemographicResource, "userDemographicService", userDemographicService);
        this.restUserDemographicMockMvc = MockMvcBuilders.standaloneSetup(userDemographicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDemographic createEntity(EntityManager em) {
        UserDemographic userDemographic = new UserDemographic()
                .created_on(DEFAULT_CREATED_ON)
                .last_login(DEFAULT_LAST_LOGIN)
                .first_name(DEFAULT_FIRST_NAME)
                .last_name(DEFAULT_LAST_NAME)
                .gender(DEFAULT_GENDER)
                .dob(DEFAULT_DOB)
                .height(DEFAULT_HEIGHT)
                .skill_level(DEFAULT_SKILL_LEVEL)
                .unit_of_measure(DEFAULT_UNIT_OF_MEASURE)
                .is_active(DEFAULT_IS_ACTIVE);
        return userDemographic;
    }

    @Before
    public void initTest() {
        userDemographic = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDemographic() throws Exception {
        int databaseSizeBeforeCreate = userDemographicRepository.findAll().size();

        // Create the UserDemographic

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isCreated());

        // Validate the UserDemographic in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeCreate + 1);
        UserDemographic testUserDemographic = userDemographicList.get(userDemographicList.size() - 1);
        assertThat(testUserDemographic.getCreated_on()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUserDemographic.getLast_login()).isEqualTo(DEFAULT_LAST_LOGIN);
        assertThat(testUserDemographic.getFirst_name()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserDemographic.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserDemographic.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserDemographic.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testUserDemographic.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testUserDemographic.getSkill_level()).isEqualTo(DEFAULT_SKILL_LEVEL);
        assertThat(testUserDemographic.getUnit_of_measure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
        assertThat(testUserDemographic.isIs_active()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createUserDemographicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDemographicRepository.findAll().size();

        // Create the UserDemographic with an existing ID
        UserDemographic existingUserDemographic = new UserDemographic();
        existingUserDemographic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserDemographic)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreated_onIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setCreated_on(null);

        // Create the UserDemographic, which fails.

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_loginIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setLast_login(null);

        // Create the UserDemographic, which fails.

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirst_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setFirst_name(null);

        // Create the UserDemographic, which fails.

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setLast_name(null);

        // Create the UserDemographic, which fails.

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setDob(null);

        // Create the UserDemographic, which fails.

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnit_of_measureIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setUnit_of_measure(null);

        // Create the UserDemographic, which fails.

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIs_activeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setIs_active(null);

        // Create the UserDemographic, which fails.

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserDemographics() throws Exception {
        // Initialize the database
        userDemographicRepository.saveAndFlush(userDemographic);

        // Get all the userDemographicList
        restUserDemographicMockMvc.perform(get("/api/user-demographics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDemographic.getId().intValue())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].last_login").value(hasItem(sameInstant(DEFAULT_LAST_LOGIN))))
            .andExpect(jsonPath("$.[*].first_name").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(sameInstant(DEFAULT_DOB))))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].skill_level").value(hasItem(DEFAULT_SKILL_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].unit_of_measure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].is_active").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserDemographic() throws Exception {
        // Initialize the database
        userDemographicRepository.saveAndFlush(userDemographic);

        // Get the userDemographic
        restUserDemographicMockMvc.perform(get("/api/user-demographics/{id}", userDemographic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDemographic.getId().intValue()))
            .andExpect(jsonPath("$.created_on").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.last_login").value(sameInstant(DEFAULT_LAST_LOGIN)))
            .andExpect(jsonPath("$.first_name").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dob").value(sameInstant(DEFAULT_DOB)))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.skill_level").value(DEFAULT_SKILL_LEVEL.toString()))
            .andExpect(jsonPath("$.unit_of_measure").value(DEFAULT_UNIT_OF_MEASURE.toString()))
            .andExpect(jsonPath("$.is_active").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserDemographic() throws Exception {
        // Get the userDemographic
        restUserDemographicMockMvc.perform(get("/api/user-demographics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDemographic() throws Exception {
        // Initialize the database
        userDemographicService.save(userDemographic);

        int databaseSizeBeforeUpdate = userDemographicRepository.findAll().size();

        // Update the userDemographic
        UserDemographic updatedUserDemographic = userDemographicRepository.findOne(userDemographic.getId());
        updatedUserDemographic
                .created_on(UPDATED_CREATED_ON)
                .last_login(UPDATED_LAST_LOGIN)
                .first_name(UPDATED_FIRST_NAME)
                .last_name(UPDATED_LAST_NAME)
                .gender(UPDATED_GENDER)
                .dob(UPDATED_DOB)
                .height(UPDATED_HEIGHT)
                .skill_level(UPDATED_SKILL_LEVEL)
                .unit_of_measure(UPDATED_UNIT_OF_MEASURE)
                .is_active(UPDATED_IS_ACTIVE);

        restUserDemographicMockMvc.perform(put("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserDemographic)))
            .andExpect(status().isOk());

        // Validate the UserDemographic in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeUpdate);
        UserDemographic testUserDemographic = userDemographicList.get(userDemographicList.size() - 1);
        assertThat(testUserDemographic.getCreated_on()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUserDemographic.getLast_login()).isEqualTo(UPDATED_LAST_LOGIN);
        assertThat(testUserDemographic.getFirst_name()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserDemographic.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserDemographic.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserDemographic.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserDemographic.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testUserDemographic.getSkill_level()).isEqualTo(UPDATED_SKILL_LEVEL);
        assertThat(testUserDemographic.getUnit_of_measure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testUserDemographic.isIs_active()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDemographic() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicRepository.findAll().size();

        // Create the UserDemographic

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserDemographicMockMvc.perform(put("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographic)))
            .andExpect(status().isCreated());

        // Validate the UserDemographic in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserDemographic() throws Exception {
        // Initialize the database
        userDemographicService.save(userDemographic);

        int databaseSizeBeforeDelete = userDemographicRepository.findAll().size();

        // Get the userDemographic
        restUserDemographicMockMvc.perform(delete("/api/user-demographics/{id}", userDemographic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
