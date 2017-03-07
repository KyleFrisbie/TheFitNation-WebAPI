package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.User;
import com.thefitnation.domain.SkillLevel;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.service.UserDemographicService;
import com.thefitnation.service.dto.UserDemographicDTO;
import com.thefitnation.service.mapper.UserDemographicMapper;
import com.thefitnation.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thefitnation.domain.enumeration.Gender;
import com.thefitnation.domain.enumeration.UnitOfMeasure;
/**
 * Test class for the UserDemographicResource REST controller.
 *
 * @see UserDemographicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserDemographicResourceIntTest {

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_LOGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_LOGIN = LocalDate.now(ZoneId.systemDefault());

    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final UnitOfMeasure DEFAULT_UNIT_OF_MEASURE = UnitOfMeasure.Imperial;
    private static final UnitOfMeasure UPDATED_UNIT_OF_MEASURE = UnitOfMeasure.Metric;

    @Autowired
    private UserDemographicRepository userDemographicRepository;

    @Autowired
    private UserDemographicMapper userDemographicMapper;

    @Autowired
    private UserDemographicService userDemographicService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserDemographicMockMvc;

    private UserDemographic userDemographic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserDemographicResource userDemographicResource = new UserDemographicResource(userDemographicService);
        this.restUserDemographicMockMvc = MockMvcBuilders.standaloneSetup(userDemographicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
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
                .createdOn(DEFAULT_CREATED_ON)
                .lastLogin(DEFAULT_LAST_LOGIN)
                .gender(DEFAULT_GENDER)
                .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
                .height(DEFAULT_HEIGHT)
                .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userDemographic.setUser(user);
        // Add required entity
        SkillLevel skillLevel = SkillLevelResourceIntTest.createEntity(em);
        em.persist(skillLevel);
        em.flush();
        userDemographic.setSkillLevel(skillLevel);
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
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographicDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDemographic in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeCreate + 1);
        UserDemographic testUserDemographic = userDemographicList.get(userDemographicList.size() - 1);
        assertThat(testUserDemographic.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUserDemographic.getLastLogin()).isEqualTo(DEFAULT_LAST_LOGIN);
        assertThat(testUserDemographic.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserDemographic.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testUserDemographic.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testUserDemographic.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    public void createUserDemographicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDemographicRepository.findAll().size();

        // Create the UserDemographic with an existing ID
        UserDemographic existingUserDemographic = new UserDemographic();
        existingUserDemographic.setId(1L);
        UserDemographicDTO existingUserDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(existingUserDemographic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserDemographicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setCreatedOn(null);

        // Create the UserDemographic, which fails.
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographicDTO)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setLastLogin(null);

        // Create the UserDemographic, which fails.
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographicDTO)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setDateOfBirth(null);

        // Create the UserDemographic, which fails.
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographicDTO)))
            .andExpect(status().isBadRequest());

        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitOfMeasureIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDemographicRepository.findAll().size();
        // set the field null
        userDemographic.setUnitOfMeasure(null);

        // Create the UserDemographic, which fails.
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);

        restUserDemographicMockMvc.perform(post("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographicDTO)))
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
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastLogin").value(hasItem(DEFAULT_LAST_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())));
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
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastLogin").value(DEFAULT_LAST_LOGIN.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE.toString()));
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
        userDemographicRepository.saveAndFlush(userDemographic);
        int databaseSizeBeforeUpdate = userDemographicRepository.findAll().size();

        // Update the userDemographic
        UserDemographic updatedUserDemographic = userDemographicRepository.findOne(userDemographic.getId());
        updatedUserDemographic
                .createdOn(UPDATED_CREATED_ON)
                .lastLogin(UPDATED_LAST_LOGIN)
                .gender(UPDATED_GENDER)
                .dateOfBirth(UPDATED_DATE_OF_BIRTH)
                .height(UPDATED_HEIGHT)
                .unitOfMeasure(UPDATED_UNIT_OF_MEASURE);
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(updatedUserDemographic);

        restUserDemographicMockMvc.perform(put("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographicDTO)))
            .andExpect(status().isOk());

        // Validate the UserDemographic in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeUpdate);
        UserDemographic testUserDemographic = userDemographicList.get(userDemographicList.size() - 1);
        assertThat(testUserDemographic.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUserDemographic.getLastLogin()).isEqualTo(UPDATED_LAST_LOGIN);
        assertThat(testUserDemographic.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserDemographic.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testUserDemographic.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testUserDemographic.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDemographic() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicRepository.findAll().size();

        // Create the UserDemographic
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserDemographicMockMvc.perform(put("/api/user-demographics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDemographicDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDemographic in the database
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserDemographic() throws Exception {
        // Initialize the database
        userDemographicRepository.saveAndFlush(userDemographic);
        int databaseSizeBeforeDelete = userDemographicRepository.findAll().size();

        // Get the userDemographic
        restUserDemographicMockMvc.perform(delete("/api/user-demographics/{id}", userDemographic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserDemographic> userDemographicList = userDemographicRepository.findAll();
        assertThat(userDemographicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDemographic.class);
    }
}
