package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.WorkoutLog;
import com.thefitnation.repository.WorkoutLogRepository;
import com.thefitnation.service.WorkoutLogService;
import com.thefitnation.repository.search.WorkoutLogSearchRepository;

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
 * Test class for the WorkoutLogResource REST controller.
 *
 * @see WorkoutLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class WorkoutLogResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private WorkoutLogRepository workoutLogRepository;

    @Inject
    private WorkoutLogService workoutLogService;

    @Inject
    private WorkoutLogSearchRepository workoutLogSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWorkoutLogMockMvc;

    private WorkoutLog workoutLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkoutLogResource workoutLogResource = new WorkoutLogResource();
        ReflectionTestUtils.setField(workoutLogResource, "workoutLogService", workoutLogService);
        this.restWorkoutLogMockMvc = MockMvcBuilders.standaloneSetup(workoutLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkoutLog createEntity(EntityManager em) {
        WorkoutLog workoutLog = new WorkoutLog()
                .created_on(DEFAULT_CREATED_ON)
                .last_updated(DEFAULT_LAST_UPDATED);
        return workoutLog;
    }

    @Before
    public void initTest() {
        workoutLogSearchRepository.deleteAll();
        workoutLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkoutLog() throws Exception {
        int databaseSizeBeforeCreate = workoutLogRepository.findAll().size();

        // Create the WorkoutLog

        restWorkoutLogMockMvc.perform(post("/api/workout-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutLog)))
            .andExpect(status().isCreated());

        // Validate the WorkoutLog in the database
        List<WorkoutLog> workoutLogList = workoutLogRepository.findAll();
        assertThat(workoutLogList).hasSize(databaseSizeBeforeCreate + 1);
        WorkoutLog testWorkoutLog = workoutLogList.get(workoutLogList.size() - 1);
        assertThat(testWorkoutLog.getCreated_on()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWorkoutLog.getLast_updated()).isEqualTo(DEFAULT_LAST_UPDATED);

        // Validate the WorkoutLog in ElasticSearch
        WorkoutLog workoutLogEs = workoutLogSearchRepository.findOne(testWorkoutLog.getId());
        assertThat(workoutLogEs).isEqualToComparingFieldByField(testWorkoutLog);
    }

    @Test
    @Transactional
    public void createWorkoutLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutLogRepository.findAll().size();

        // Create the WorkoutLog with an existing ID
        WorkoutLog existingWorkoutLog = new WorkoutLog();
        existingWorkoutLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutLogMockMvc.perform(post("/api/workout-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkoutLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkoutLog> workoutLogList = workoutLogRepository.findAll();
        assertThat(workoutLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreated_onIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutLogRepository.findAll().size();
        // set the field null
        workoutLog.setCreated_on(null);

        // Create the WorkoutLog, which fails.

        restWorkoutLogMockMvc.perform(post("/api/workout-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutLog)))
            .andExpect(status().isBadRequest());

        List<WorkoutLog> workoutLogList = workoutLogRepository.findAll();
        assertThat(workoutLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_updatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutLogRepository.findAll().size();
        // set the field null
        workoutLog.setLast_updated(null);

        // Create the WorkoutLog, which fails.

        restWorkoutLogMockMvc.perform(post("/api/workout-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutLog)))
            .andExpect(status().isBadRequest());

        List<WorkoutLog> workoutLogList = workoutLogRepository.findAll();
        assertThat(workoutLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkoutLogs() throws Exception {
        // Initialize the database
        workoutLogRepository.saveAndFlush(workoutLog);

        // Get all the workoutLogList
        restWorkoutLogMockMvc.perform(get("/api/workout-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].last_updated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }

    @Test
    @Transactional
    public void getWorkoutLog() throws Exception {
        // Initialize the database
        workoutLogRepository.saveAndFlush(workoutLog);

        // Get the workoutLog
        restWorkoutLogMockMvc.perform(get("/api/workout-logs/{id}", workoutLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workoutLog.getId().intValue()))
            .andExpect(jsonPath("$.created_on").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.last_updated").value(sameInstant(DEFAULT_LAST_UPDATED)));
    }

    @Test
    @Transactional
    public void getNonExistingWorkoutLog() throws Exception {
        // Get the workoutLog
        restWorkoutLogMockMvc.perform(get("/api/workout-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkoutLog() throws Exception {
        // Initialize the database
        workoutLogService.save(workoutLog);

        int databaseSizeBeforeUpdate = workoutLogRepository.findAll().size();

        // Update the workoutLog
        WorkoutLog updatedWorkoutLog = workoutLogRepository.findOne(workoutLog.getId());
        updatedWorkoutLog
                .created_on(UPDATED_CREATED_ON)
                .last_updated(UPDATED_LAST_UPDATED);

        restWorkoutLogMockMvc.perform(put("/api/workout-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkoutLog)))
            .andExpect(status().isOk());

        // Validate the WorkoutLog in the database
        List<WorkoutLog> workoutLogList = workoutLogRepository.findAll();
        assertThat(workoutLogList).hasSize(databaseSizeBeforeUpdate);
        WorkoutLog testWorkoutLog = workoutLogList.get(workoutLogList.size() - 1);
        assertThat(testWorkoutLog.getCreated_on()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkoutLog.getLast_updated()).isEqualTo(UPDATED_LAST_UPDATED);

        // Validate the WorkoutLog in ElasticSearch
        WorkoutLog workoutLogEs = workoutLogSearchRepository.findOne(testWorkoutLog.getId());
        assertThat(workoutLogEs).isEqualToComparingFieldByField(testWorkoutLog);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkoutLog() throws Exception {
        int databaseSizeBeforeUpdate = workoutLogRepository.findAll().size();

        // Create the WorkoutLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkoutLogMockMvc.perform(put("/api/workout-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutLog)))
            .andExpect(status().isCreated());

        // Validate the WorkoutLog in the database
        List<WorkoutLog> workoutLogList = workoutLogRepository.findAll();
        assertThat(workoutLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkoutLog() throws Exception {
        // Initialize the database
        workoutLogService.save(workoutLog);

        int databaseSizeBeforeDelete = workoutLogRepository.findAll().size();

        // Get the workoutLog
        restWorkoutLogMockMvc.perform(delete("/api/workout-logs/{id}", workoutLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean workoutLogExistsInEs = workoutLogSearchRepository.exists(workoutLog.getId());
        assertThat(workoutLogExistsInEs).isFalse();

        // Validate the database is empty
        List<WorkoutLog> workoutLogList = workoutLogRepository.findAll();
        assertThat(workoutLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWorkoutLog() throws Exception {
        // Initialize the database
        workoutLogService.save(workoutLog);

        // Search the workoutLog
        restWorkoutLogMockMvc.perform(get("/api/_search/workout-logs?query=id:" + workoutLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].last_updated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }
}
