package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.repository.UserWorkoutInstanceRepository;
import com.thefitnation.service.UserWorkoutInstanceService;
import com.thefitnation.service.dto.UserWorkoutInstanceDTO;
import com.thefitnation.service.mapper.UserWorkoutInstanceMapper;
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

/**
 * Test class for the UserWorkoutInstanceResource REST controller.
 *
 * @see UserWorkoutInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserWorkoutInstanceResourceIntTest {

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_WAS_COMPLETED = false;
    private static final Boolean UPDATED_WAS_COMPLETED = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private UserWorkoutInstanceRepository userWorkoutInstanceRepository;

    @Autowired
    private UserWorkoutInstanceMapper userWorkoutInstanceMapper;

    @Autowired
    private UserWorkoutInstanceService userWorkoutInstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserWorkoutInstanceMockMvc;

    private UserWorkoutInstance userWorkoutInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserWorkoutInstanceResource userWorkoutInstanceResource = new UserWorkoutInstanceResource(userWorkoutInstanceService);
        this.restUserWorkoutInstanceMockMvc = MockMvcBuilders.standaloneSetup(userWorkoutInstanceResource)
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
    public static UserWorkoutInstance createEntity(EntityManager em) {
        UserWorkoutInstance userWorkoutInstance = new UserWorkoutInstance()
                .createdOn(DEFAULT_CREATED_ON)
                .lastUpdated(DEFAULT_LAST_UPDATED)
                .wasCompleted(DEFAULT_WAS_COMPLETED)
                .notes(DEFAULT_NOTES);
        // Add required entity
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateResourceIntTest.createEntity(em);
        em.persist(userWorkoutTemplate);
        em.flush();
        userWorkoutInstance.setUserWorkoutTemplate(userWorkoutTemplate);
        return userWorkoutInstance;
    }

    @Before
    public void initTest() {
        userWorkoutInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWorkoutInstance() throws Exception {
        int databaseSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        // Create the UserWorkoutInstance
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);

        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutInstance in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        UserWorkoutInstance testUserWorkoutInstance = userWorkoutInstanceList.get(userWorkoutInstanceList.size() - 1);
        assertThat(testUserWorkoutInstance.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUserWorkoutInstance.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testUserWorkoutInstance.isWasCompleted()).isEqualTo(DEFAULT_WAS_COMPLETED);
        assertThat(testUserWorkoutInstance.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createUserWorkoutInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        // Create the UserWorkoutInstance with an existing ID
        UserWorkoutInstance existingUserWorkoutInstance = new UserWorkoutInstance();
        existingUserWorkoutInstance.setId(1L);
        UserWorkoutInstanceDTO existingUserWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(existingUserWorkoutInstance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserWorkoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutInstanceRepository.findAll().size();
        // set the field null
        userWorkoutInstance.setCreatedOn(null);

        // Create the UserWorkoutInstance, which fails.
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);

        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutInstanceRepository.findAll().size();
        // set the field null
        userWorkoutInstance.setLastUpdated(null);

        // Create the UserWorkoutInstance, which fails.
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);

        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWasCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutInstanceRepository.findAll().size();
        // set the field null
        userWorkoutInstance.setWasCompleted(null);

        // Create the UserWorkoutInstance, which fails.
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);

        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserWorkoutInstances() throws Exception {
        // Initialize the database
        userWorkoutInstanceRepository.saveAndFlush(userWorkoutInstance);

        // Get all the userWorkoutInstanceList
        restUserWorkoutInstanceMockMvc.perform(get("/api/user-workout-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWorkoutInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].wasCompleted").value(hasItem(DEFAULT_WAS_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getUserWorkoutInstance() throws Exception {
        // Initialize the database
        userWorkoutInstanceRepository.saveAndFlush(userWorkoutInstance);

        // Get the userWorkoutInstance
        restUserWorkoutInstanceMockMvc.perform(get("/api/user-workout-instances/{id}", userWorkoutInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userWorkoutInstance.getId().intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.wasCompleted").value(DEFAULT_WAS_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserWorkoutInstance() throws Exception {
        // Get the userWorkoutInstance
        restUserWorkoutInstanceMockMvc.perform(get("/api/user-workout-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserWorkoutInstance() throws Exception {
        // Initialize the database
        userWorkoutInstanceRepository.saveAndFlush(userWorkoutInstance);
        int databaseSizeBeforeUpdate = userWorkoutInstanceRepository.findAll().size();

        // Update the userWorkoutInstance
        UserWorkoutInstance updatedUserWorkoutInstance = userWorkoutInstanceRepository.findOne(userWorkoutInstance.getId());
        updatedUserWorkoutInstance
                .createdOn(UPDATED_CREATED_ON)
                .lastUpdated(UPDATED_LAST_UPDATED)
                .wasCompleted(UPDATED_WAS_COMPLETED)
                .notes(UPDATED_NOTES);
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(updatedUserWorkoutInstance);

        restUserWorkoutInstanceMockMvc.perform(put("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstanceDTO)))
            .andExpect(status().isOk());

        // Validate the UserWorkoutInstance in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeUpdate);
        UserWorkoutInstance testUserWorkoutInstance = userWorkoutInstanceList.get(userWorkoutInstanceList.size() - 1);
        assertThat(testUserWorkoutInstance.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUserWorkoutInstance.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testUserWorkoutInstance.isWasCompleted()).isEqualTo(UPDATED_WAS_COMPLETED);
        assertThat(testUserWorkoutInstance.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWorkoutInstance() throws Exception {
        int databaseSizeBeforeUpdate = userWorkoutInstanceRepository.findAll().size();

        // Create the UserWorkoutInstance
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserWorkoutInstanceMockMvc.perform(put("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutInstance in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserWorkoutInstance() throws Exception {
        // Initialize the database
        userWorkoutInstanceRepository.saveAndFlush(userWorkoutInstance);
        int databaseSizeBeforeDelete = userWorkoutInstanceRepository.findAll().size();

        // Get the userWorkoutInstance
        restUserWorkoutInstanceMockMvc.perform(delete("/api/user-workout-instances/{id}", userWorkoutInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWorkoutInstance.class);
    }
}
