package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.WorkoutTemplateRepository;
import com.thefitnation.service.WorkoutTemplateService;

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
 * Test class for the WorkoutTemplateResource REST controller.
 *
 * @see WorkoutTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class WorkoutTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final Boolean UPDATED_IS_PRIVATE = true;

    @Inject
    private WorkoutTemplateRepository workoutTemplateRepository;

    @Inject
    private WorkoutTemplateService workoutTemplateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWorkoutTemplateMockMvc;

    private WorkoutTemplate workoutTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkoutTemplateResource workoutTemplateResource = new WorkoutTemplateResource();
        ReflectionTestUtils.setField(workoutTemplateResource, "workoutTemplateService", workoutTemplateService);
        this.restWorkoutTemplateMockMvc = MockMvcBuilders.standaloneSetup(workoutTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkoutTemplate createEntity(EntityManager em) {
        WorkoutTemplate workoutTemplate = new WorkoutTemplate()
                .name(DEFAULT_NAME)
                .created_on(DEFAULT_CREATED_ON)
                .last_updated(DEFAULT_LAST_UPDATED)
                .is_private(DEFAULT_IS_PRIVATE);
        // Add required entity
        UserDemographic userDemographic = UserDemographicResourceIntTest.createEntity(em);
        em.persist(userDemographic);
        em.flush();
        workoutTemplate.setUserDemographic(userDemographic);
        return workoutTemplate;
    }

    @Before
    public void initTest() {
        workoutTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkoutTemplate() throws Exception {
        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        // Create the WorkoutTemplate

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplate)))
            .andExpect(status().isCreated());

        // Validate the WorkoutTemplate in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        WorkoutTemplate testWorkoutTemplate = workoutTemplateList.get(workoutTemplateList.size() - 1);
        assertThat(testWorkoutTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkoutTemplate.getCreated_on()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWorkoutTemplate.getLast_updated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testWorkoutTemplate.isIs_private()).isEqualTo(DEFAULT_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void createWorkoutTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        // Create the WorkoutTemplate with an existing ID
        WorkoutTemplate existingWorkoutTemplate = new WorkoutTemplate();
        existingWorkoutTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkoutTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setName(null);

        // Create the WorkoutTemplate, which fails.

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplate)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_onIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setCreated_on(null);

        // Create the WorkoutTemplate, which fails.

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplate)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_updatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setLast_updated(null);

        // Create the WorkoutTemplate, which fails.

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplate)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIs_privateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setIs_private(null);

        // Create the WorkoutTemplate, which fails.

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplate)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkoutTemplates() throws Exception {
        // Initialize the database
        workoutTemplateRepository.saveAndFlush(workoutTemplate);

        // Get all the workoutTemplateList
        restWorkoutTemplateMockMvc.perform(get("/api/workout-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].last_updated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))))
            .andExpect(jsonPath("$.[*].is_private").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())));
    }

    @Test
    @Transactional
    public void getWorkoutTemplate() throws Exception {
        // Initialize the database
        workoutTemplateRepository.saveAndFlush(workoutTemplate);

        // Get the workoutTemplate
        restWorkoutTemplateMockMvc.perform(get("/api/workout-templates/{id}", workoutTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workoutTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.created_on").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.last_updated").value(sameInstant(DEFAULT_LAST_UPDATED)))
            .andExpect(jsonPath("$.is_private").value(DEFAULT_IS_PRIVATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkoutTemplate() throws Exception {
        // Get the workoutTemplate
        restWorkoutTemplateMockMvc.perform(get("/api/workout-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkoutTemplate() throws Exception {
        // Initialize the database
        workoutTemplateService.save(workoutTemplate);

        int databaseSizeBeforeUpdate = workoutTemplateRepository.findAll().size();

        // Update the workoutTemplate
        WorkoutTemplate updatedWorkoutTemplate = workoutTemplateRepository.findOne(workoutTemplate.getId());
        updatedWorkoutTemplate
                .name(UPDATED_NAME)
                .created_on(UPDATED_CREATED_ON)
                .last_updated(UPDATED_LAST_UPDATED)
                .is_private(UPDATED_IS_PRIVATE);

        restWorkoutTemplateMockMvc.perform(put("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkoutTemplate)))
            .andExpect(status().isOk());

        // Validate the WorkoutTemplate in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeUpdate);
        WorkoutTemplate testWorkoutTemplate = workoutTemplateList.get(workoutTemplateList.size() - 1);
        assertThat(testWorkoutTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkoutTemplate.getCreated_on()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkoutTemplate.getLast_updated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testWorkoutTemplate.isIs_private()).isEqualTo(UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkoutTemplate() throws Exception {
        int databaseSizeBeforeUpdate = workoutTemplateRepository.findAll().size();

        // Create the WorkoutTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkoutTemplateMockMvc.perform(put("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplate)))
            .andExpect(status().isCreated());

        // Validate the WorkoutTemplate in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkoutTemplate() throws Exception {
        // Initialize the database
        workoutTemplateService.save(workoutTemplate);

        int databaseSizeBeforeDelete = workoutTemplateRepository.findAll().size();

        // Get the workoutTemplate
        restWorkoutTemplateMockMvc.perform(delete("/api/workout-templates/{id}", workoutTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
