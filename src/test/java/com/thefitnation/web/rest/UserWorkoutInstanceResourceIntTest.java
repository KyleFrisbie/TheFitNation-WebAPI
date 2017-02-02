package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.repository.UserWorkoutInstanceRepository;
import com.thefitnation.service.UserWorkoutInstanceService;
import com.thefitnation.repository.search.UserWorkoutInstanceSearchRepository;

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

/**
 * Test class for the UserWorkoutInstanceResource REST controller.
 *
 * @see UserWorkoutInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserWorkoutInstanceResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_WAS_COMPLETED = false;
    private static final Boolean UPDATED_WAS_COMPLETED = true;

    @Inject
    private UserWorkoutInstanceRepository userWorkoutInstanceRepository;

    @Inject
    private UserWorkoutInstanceService userWorkoutInstanceService;

    @Inject
    private UserWorkoutInstanceSearchRepository userWorkoutInstanceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserWorkoutInstanceMockMvc;

    private UserWorkoutInstance userWorkoutInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserWorkoutInstanceResource userWorkoutInstanceResource = new UserWorkoutInstanceResource();
        ReflectionTestUtils.setField(userWorkoutInstanceResource, "userWorkoutInstanceService", userWorkoutInstanceService);
        this.restUserWorkoutInstanceMockMvc = MockMvcBuilders.standaloneSetup(userWorkoutInstanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
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
                .created_on(DEFAULT_CREATED_ON)
                .was_completed(DEFAULT_WAS_COMPLETED);
        return userWorkoutInstance;
    }

    @Before
    public void initTest() {
        userWorkoutInstanceSearchRepository.deleteAll();
        userWorkoutInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWorkoutInstance() throws Exception {
        int databaseSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        // Create the UserWorkoutInstance

        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstance)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutInstance in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        UserWorkoutInstance testUserWorkoutInstance = userWorkoutInstanceList.get(userWorkoutInstanceList.size() - 1);
        assertThat(testUserWorkoutInstance.getCreated_on()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUserWorkoutInstance.isWas_completed()).isEqualTo(DEFAULT_WAS_COMPLETED);

        // Validate the UserWorkoutInstance in ElasticSearch
        UserWorkoutInstance userWorkoutInstanceEs = userWorkoutInstanceSearchRepository.findOne(testUserWorkoutInstance.getId());
        assertThat(userWorkoutInstanceEs).isEqualToComparingFieldByField(testUserWorkoutInstance);
    }

    @Test
    @Transactional
    public void createUserWorkoutInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWorkoutInstanceRepository.findAll().size();

        // Create the UserWorkoutInstance with an existing ID
        UserWorkoutInstance existingUserWorkoutInstance = new UserWorkoutInstance();
        existingUserWorkoutInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserWorkoutInstance)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreated_onIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutInstanceRepository.findAll().size();
        // set the field null
        userWorkoutInstance.setCreated_on(null);

        // Create the UserWorkoutInstance, which fails.

        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstance)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWas_completedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutInstanceRepository.findAll().size();
        // set the field null
        userWorkoutInstance.setWas_completed(null);

        // Create the UserWorkoutInstance, which fails.

        restUserWorkoutInstanceMockMvc.perform(post("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstance)))
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
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].was_completed").value(hasItem(DEFAULT_WAS_COMPLETED.booleanValue())));
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
            .andExpect(jsonPath("$.created_on").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.was_completed").value(DEFAULT_WAS_COMPLETED.booleanValue()));
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
        userWorkoutInstanceService.save(userWorkoutInstance);

        int databaseSizeBeforeUpdate = userWorkoutInstanceRepository.findAll().size();

        // Update the userWorkoutInstance
        UserWorkoutInstance updatedUserWorkoutInstance = userWorkoutInstanceRepository.findOne(userWorkoutInstance.getId());
        updatedUserWorkoutInstance
                .created_on(UPDATED_CREATED_ON)
                .was_completed(UPDATED_WAS_COMPLETED);

        restUserWorkoutInstanceMockMvc.perform(put("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserWorkoutInstance)))
            .andExpect(status().isOk());

        // Validate the UserWorkoutInstance in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeUpdate);
        UserWorkoutInstance testUserWorkoutInstance = userWorkoutInstanceList.get(userWorkoutInstanceList.size() - 1);
        assertThat(testUserWorkoutInstance.getCreated_on()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUserWorkoutInstance.isWas_completed()).isEqualTo(UPDATED_WAS_COMPLETED);

        // Validate the UserWorkoutInstance in ElasticSearch
        UserWorkoutInstance userWorkoutInstanceEs = userWorkoutInstanceSearchRepository.findOne(testUserWorkoutInstance.getId());
        assertThat(userWorkoutInstanceEs).isEqualToComparingFieldByField(testUserWorkoutInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWorkoutInstance() throws Exception {
        int databaseSizeBeforeUpdate = userWorkoutInstanceRepository.findAll().size();

        // Create the UserWorkoutInstance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserWorkoutInstanceMockMvc.perform(put("/api/user-workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutInstance)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutInstance in the database
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserWorkoutInstance() throws Exception {
        // Initialize the database
        userWorkoutInstanceService.save(userWorkoutInstance);

        int databaseSizeBeforeDelete = userWorkoutInstanceRepository.findAll().size();

        // Get the userWorkoutInstance
        restUserWorkoutInstanceMockMvc.perform(delete("/api/user-workout-instances/{id}", userWorkoutInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean userWorkoutInstanceExistsInEs = userWorkoutInstanceSearchRepository.exists(userWorkoutInstance.getId());
        assertThat(userWorkoutInstanceExistsInEs).isFalse();

        // Validate the database is empty
        List<UserWorkoutInstance> userWorkoutInstanceList = userWorkoutInstanceRepository.findAll();
        assertThat(userWorkoutInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserWorkoutInstance() throws Exception {
        // Initialize the database
        userWorkoutInstanceService.save(userWorkoutInstance);

        // Search the userWorkoutInstance
        restUserWorkoutInstanceMockMvc.perform(get("/api/_search/user-workout-instances?query=id:" + userWorkoutInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWorkoutInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].was_completed").value(hasItem(DEFAULT_WAS_COMPLETED.booleanValue())));
    }
}
