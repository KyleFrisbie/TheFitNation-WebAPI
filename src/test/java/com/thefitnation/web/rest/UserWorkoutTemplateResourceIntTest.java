package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.domain.WorkoutLog;
import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.service.UserWorkoutTemplateService;

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
 * Test class for the UserWorkoutTemplateResource REST controller.
 *
 * @see UserWorkoutTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserWorkoutTemplateResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    @Inject
    private UserWorkoutTemplateService userWorkoutTemplateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserWorkoutTemplateMockMvc;

    private UserWorkoutTemplate userWorkoutTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserWorkoutTemplateResource userWorkoutTemplateResource = new UserWorkoutTemplateResource();
        ReflectionTestUtils.setField(userWorkoutTemplateResource, "userWorkoutTemplateService", userWorkoutTemplateService);
        this.restUserWorkoutTemplateMockMvc = MockMvcBuilders.standaloneSetup(userWorkoutTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWorkoutTemplate createEntity(EntityManager em) {
        UserWorkoutTemplate userWorkoutTemplate = new UserWorkoutTemplate()
                .created_on(DEFAULT_CREATED_ON)
                .last_updated(DEFAULT_LAST_UPDATED);
        // Add required entity
        WorkoutLog workoutLog = WorkoutLogResourceIntTest.createEntity(em);
        em.persist(workoutLog);
        em.flush();
        userWorkoutTemplate.setWorkoutLog(workoutLog);
        // Add required entity
        WorkoutTemplate workoutTemplate = WorkoutTemplateResourceIntTest.createEntity(em);
        em.persist(workoutTemplate);
        em.flush();
        userWorkoutTemplate.setWorkoutTemplate(workoutTemplate);
        return userWorkoutTemplate;
    }

    @Before
    public void initTest() {
        userWorkoutTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWorkoutTemplate() throws Exception {
        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        // Create the UserWorkoutTemplate

        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplate)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutTemplate in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        UserWorkoutTemplate testUserWorkoutTemplate = userWorkoutTemplateList.get(userWorkoutTemplateList.size() - 1);
        assertThat(testUserWorkoutTemplate.getCreated_on()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUserWorkoutTemplate.getLast_updated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void createUserWorkoutTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        // Create the UserWorkoutTemplate with an existing ID
        UserWorkoutTemplate existingUserWorkoutTemplate = new UserWorkoutTemplate();
        existingUserWorkoutTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserWorkoutTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreated_onIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutTemplateRepository.findAll().size();
        // set the field null
        userWorkoutTemplate.setCreated_on(null);

        // Create the UserWorkoutTemplate, which fails.

        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplate)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_updatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutTemplateRepository.findAll().size();
        // set the field null
        userWorkoutTemplate.setLast_updated(null);

        // Create the UserWorkoutTemplate, which fails.

        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplate)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserWorkoutTemplates() throws Exception {
        // Initialize the database
        userWorkoutTemplateRepository.saveAndFlush(userWorkoutTemplate);

        // Get all the userWorkoutTemplateList
        restUserWorkoutTemplateMockMvc.perform(get("/api/user-workout-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWorkoutTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].created_on").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].last_updated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }

    @Test
    @Transactional
    public void getUserWorkoutTemplate() throws Exception {
        // Initialize the database
        userWorkoutTemplateRepository.saveAndFlush(userWorkoutTemplate);

        // Get the userWorkoutTemplate
        restUserWorkoutTemplateMockMvc.perform(get("/api/user-workout-templates/{id}", userWorkoutTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userWorkoutTemplate.getId().intValue()))
            .andExpect(jsonPath("$.created_on").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.last_updated").value(sameInstant(DEFAULT_LAST_UPDATED)));
    }

    @Test
    @Transactional
    public void getNonExistingUserWorkoutTemplate() throws Exception {
        // Get the userWorkoutTemplate
        restUserWorkoutTemplateMockMvc.perform(get("/api/user-workout-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserWorkoutTemplate() throws Exception {
        // Initialize the database
        userWorkoutTemplateService.save(userWorkoutTemplate);

        int databaseSizeBeforeUpdate = userWorkoutTemplateRepository.findAll().size();

        // Update the userWorkoutTemplate
        UserWorkoutTemplate updatedUserWorkoutTemplate = userWorkoutTemplateRepository.findOne(userWorkoutTemplate.getId());
        updatedUserWorkoutTemplate
                .created_on(UPDATED_CREATED_ON)
                .last_updated(UPDATED_LAST_UPDATED);

        restUserWorkoutTemplateMockMvc.perform(put("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserWorkoutTemplate)))
            .andExpect(status().isOk());

        // Validate the UserWorkoutTemplate in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeUpdate);
        UserWorkoutTemplate testUserWorkoutTemplate = userWorkoutTemplateList.get(userWorkoutTemplateList.size() - 1);
        assertThat(testUserWorkoutTemplate.getCreated_on()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUserWorkoutTemplate.getLast_updated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWorkoutTemplate() throws Exception {
        int databaseSizeBeforeUpdate = userWorkoutTemplateRepository.findAll().size();

        // Create the UserWorkoutTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserWorkoutTemplateMockMvc.perform(put("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplate)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutTemplate in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserWorkoutTemplate() throws Exception {
        // Initialize the database
        userWorkoutTemplateService.save(userWorkoutTemplate);

        int databaseSizeBeforeDelete = userWorkoutTemplateRepository.findAll().size();

        // Get the userWorkoutTemplate
        restUserWorkoutTemplateMockMvc.perform(delete("/api/user-workout-templates/{id}", userWorkoutTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
