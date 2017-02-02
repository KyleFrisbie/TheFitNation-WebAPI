package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.domain.Exercise;
import com.thefitnation.repository.WorkoutInstanceRepository;
import com.thefitnation.service.WorkoutInstanceService;
import com.thefitnation.repository.search.WorkoutInstanceSearchRepository;

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
 * Test class for the WorkoutInstanceResource REST controller.
 *
 * @see WorkoutInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class WorkoutInstanceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_REST_BETWEEN_INSTANCES = 1;
    private static final Integer UPDATED_REST_BETWEEN_INSTANCES = 2;

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    @Inject
    private WorkoutInstanceRepository workoutInstanceRepository;

    @Inject
    private WorkoutInstanceService workoutInstanceService;

    @Inject
    private WorkoutInstanceSearchRepository workoutInstanceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWorkoutInstanceMockMvc;

    private WorkoutInstance workoutInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkoutInstanceResource workoutInstanceResource = new WorkoutInstanceResource();
        ReflectionTestUtils.setField(workoutInstanceResource, "workoutInstanceService", workoutInstanceService);
        this.restWorkoutInstanceMockMvc = MockMvcBuilders.standaloneSetup(workoutInstanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkoutInstance createEntity(EntityManager em) {
        WorkoutInstance workoutInstance = new WorkoutInstance()
                .name(DEFAULT_NAME)
                .created_on(DEFAULT_CREATED_ON)
                .last_updated(DEFAULT_LAST_UPDATED)
                .rest_between_instances(DEFAULT_REST_BETWEEN_INSTANCES)
                .order_number(DEFAULT_ORDER_NUMBER);
        // Add required entity
        Exercise exercise = ExerciseResourceIntTest.createEntity(em);
        em.persist(exercise);
        em.flush();
        workoutInstance.getExercises().add(exercise);
        return workoutInstance;
    }

    @Before
    public void initTest() {
        workoutInstanceSearchRepository.deleteAll();
        workoutInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkoutInstance() throws Exception {
        int databaseSizeBeforeCreate = workoutInstanceRepository.findAll().size();

        // Create the WorkoutInstance

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstance)))
            .andExpect(status().isCreated());

        // Validate the WorkoutInstance in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkoutInstance testWorkoutInstance = workoutInstanceList.get(workoutInstanceList.size() - 1);
        assertThat(testWorkoutInstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkoutInstance.getCreated_on()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWorkoutInstance.getLast_updated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testWorkoutInstance.getRest_between_instances()).isEqualTo(DEFAULT_REST_BETWEEN_INSTANCES);
        assertThat(testWorkoutInstance.getOrder_number()).isEqualTo(DEFAULT_ORDER_NUMBER);

        // Validate the WorkoutInstance in ElasticSearch
        WorkoutInstance workoutInstanceEs = workoutInstanceSearchRepository.findOne(testWorkoutInstance.getId());
        assertThat(workoutInstanceEs).isEqualToComparingFieldByField(testWorkoutInstance);
    }

    @Test
    @Transactional
    public void createWorkoutInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutInstanceRepository.findAll().size();

        // Create the WorkoutInstance with an existing ID
        WorkoutInstance existingWorkoutInstance = new WorkoutInstance();
        existingWorkoutInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkoutInstance)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreated_onIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutInstanceRepository.findAll().size();
        // set the field null
        workoutInstance.setCreated_on(null);

        // Create the WorkoutInstance, which fails.

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstance)))
            .andExpect(status().isBadRequest());

        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_updatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutInstanceRepository.findAll().size();
        // set the field null
        workoutInstance.setLast_updated(null);

        // Create the WorkoutInstance, which fails.

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstance)))
            .andExpect(status().isBadRequest());

        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrder_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutInstanceRepository.findAll().size();
        // set the field null
        workoutInstance.setOrder_number(null);

        // Create the WorkoutInstance, which fails.

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstance)))
            .andExpect(status().isBadRequest());

        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkoutInstances() throws Exception {
        // Initialize the database
        workoutInstanceRepository.saveAndFlush(workoutInstance);

        // Get all the workoutInstanceList
        restWorkoutInstanceMockMvc.perform(get("/api/workout-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].last_updated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))))
            .andExpect(jsonPath("$.[*].rest_between_instances").value(hasItem(DEFAULT_REST_BETWEEN_INSTANCES)))
            .andExpect(jsonPath("$.[*].order_number").value(hasItem(DEFAULT_ORDER_NUMBER)));
    }

    @Test
    @Transactional
    public void getWorkoutInstance() throws Exception {
        // Initialize the database
        workoutInstanceRepository.saveAndFlush(workoutInstance);

        // Get the workoutInstance
        restWorkoutInstanceMockMvc.perform(get("/api/workout-instances/{id}", workoutInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workoutInstance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.created_on").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.last_updated").value(sameInstant(DEFAULT_LAST_UPDATED)))
            .andExpect(jsonPath("$.rest_between_instances").value(DEFAULT_REST_BETWEEN_INSTANCES))
            .andExpect(jsonPath("$.order_number").value(DEFAULT_ORDER_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingWorkoutInstance() throws Exception {
        // Get the workoutInstance
        restWorkoutInstanceMockMvc.perform(get("/api/workout-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkoutInstance() throws Exception {
        // Initialize the database
        workoutInstanceService.save(workoutInstance);

        int databaseSizeBeforeUpdate = workoutInstanceRepository.findAll().size();

        // Update the workoutInstance
        WorkoutInstance updatedWorkoutInstance = workoutInstanceRepository.findOne(workoutInstance.getId());
        updatedWorkoutInstance
                .name(UPDATED_NAME)
                .created_on(UPDATED_CREATED_ON)
                .last_updated(UPDATED_LAST_UPDATED)
                .rest_between_instances(UPDATED_REST_BETWEEN_INSTANCES)
                .order_number(UPDATED_ORDER_NUMBER);

        restWorkoutInstanceMockMvc.perform(put("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkoutInstance)))
            .andExpect(status().isOk());

        // Validate the WorkoutInstance in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeUpdate);
        WorkoutInstance testWorkoutInstance = workoutInstanceList.get(workoutInstanceList.size() - 1);
        assertThat(testWorkoutInstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkoutInstance.getCreated_on()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkoutInstance.getLast_updated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testWorkoutInstance.getRest_between_instances()).isEqualTo(UPDATED_REST_BETWEEN_INSTANCES);
        assertThat(testWorkoutInstance.getOrder_number()).isEqualTo(UPDATED_ORDER_NUMBER);

        // Validate the WorkoutInstance in ElasticSearch
        WorkoutInstance workoutInstanceEs = workoutInstanceSearchRepository.findOne(testWorkoutInstance.getId());
        assertThat(workoutInstanceEs).isEqualToComparingFieldByField(testWorkoutInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkoutInstance() throws Exception {
        int databaseSizeBeforeUpdate = workoutInstanceRepository.findAll().size();

        // Create the WorkoutInstance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkoutInstanceMockMvc.perform(put("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstance)))
            .andExpect(status().isCreated());

        // Validate the WorkoutInstance in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkoutInstance() throws Exception {
        // Initialize the database
        workoutInstanceService.save(workoutInstance);

        int databaseSizeBeforeDelete = workoutInstanceRepository.findAll().size();

        // Get the workoutInstance
        restWorkoutInstanceMockMvc.perform(delete("/api/workout-instances/{id}", workoutInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean workoutInstanceExistsInEs = workoutInstanceSearchRepository.exists(workoutInstance.getId());
        assertThat(workoutInstanceExistsInEs).isFalse();

        // Validate the database is empty
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWorkoutInstance() throws Exception {
        // Initialize the database
        workoutInstanceService.save(workoutInstance);

        // Search the workoutInstance
        restWorkoutInstanceMockMvc.perform(get("/api/_search/workout-instances?query=id:" + workoutInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].last_updated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))))
            .andExpect(jsonPath("$.[*].rest_between_instances").value(hasItem(DEFAULT_REST_BETWEEN_INSTANCES)))
            .andExpect(jsonPath("$.[*].order_number").value(hasItem(DEFAULT_ORDER_NUMBER)));
    }
}
