package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.Exercise;
import com.thefitnation.domain.Muscle;
import com.thefitnation.repository.ExerciseRepository;
import com.thefitnation.service.ExerciseService;
import com.thefitnation.repository.search.ExerciseSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExerciseResource REST controller.
 *
 * @see ExerciseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class ExerciseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private ExerciseRepository exerciseRepository;

    @Inject
    private ExerciseService exerciseService;

    @Inject
    private ExerciseSearchRepository exerciseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExerciseMockMvc;

    private Exercise exercise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseResource exerciseResource = new ExerciseResource();
        ReflectionTestUtils.setField(exerciseResource, "exerciseService", exerciseService);
        this.restExerciseMockMvc = MockMvcBuilders.standaloneSetup(exerciseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exercise createEntity(EntityManager em) {
        Exercise exercise = new Exercise()
                .name(DEFAULT_NAME);
        // Add required entity
        Muscle muscle = MuscleResourceIntTest.createEntity(em);
        em.persist(muscle);
        em.flush();
        exercise.getMuscles().add(muscle);
        return exercise;
    }

    @Before
    public void initTest() {
        exerciseSearchRepository.deleteAll();
        exercise = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercise() throws Exception {
        int databaseSizeBeforeCreate = exerciseRepository.findAll().size();

        // Create the Exercise

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercise)))
            .andExpect(status().isCreated());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeCreate + 1);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Exercise in ElasticSearch
        Exercise exerciseEs = exerciseSearchRepository.findOne(testExercise.getId());
        assertThat(exerciseEs).isEqualToComparingFieldByField(testExercise);
    }

    @Test
    @Transactional
    public void createExerciseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseRepository.findAll().size();

        // Create the Exercise with an existing ID
        Exercise existingExercise = new Exercise();
        existingExercise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExercise)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setName(null);

        // Create the Exercise, which fails.

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercise)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExercises() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get all the exerciseList
        restExerciseMockMvc.perform(get("/api/exercises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercise.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get the exercise
        restExerciseMockMvc.perform(get("/api/exercises/{id}", exercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exercise.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExercise() throws Exception {
        // Get the exercise
        restExerciseMockMvc.perform(get("/api/exercises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercise() throws Exception {
        // Initialize the database
        exerciseService.save(exercise);

        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Update the exercise
        Exercise updatedExercise = exerciseRepository.findOne(exercise.getId());
        updatedExercise
                .name(UPDATED_NAME);

        restExerciseMockMvc.perform(put("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExercise)))
            .andExpect(status().isOk());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Exercise in ElasticSearch
        Exercise exerciseEs = exerciseSearchRepository.findOne(testExercise.getId());
        assertThat(exerciseEs).isEqualToComparingFieldByField(testExercise);
    }

    @Test
    @Transactional
    public void updateNonExistingExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Create the Exercise

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciseMockMvc.perform(put("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercise)))
            .andExpect(status().isCreated());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExercise() throws Exception {
        // Initialize the database
        exerciseService.save(exercise);

        int databaseSizeBeforeDelete = exerciseRepository.findAll().size();

        // Get the exercise
        restExerciseMockMvc.perform(delete("/api/exercises/{id}", exercise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean exerciseExistsInEs = exerciseSearchRepository.exists(exercise.getId());
        assertThat(exerciseExistsInEs).isFalse();

        // Validate the database is empty
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExercise() throws Exception {
        // Initialize the database
        exerciseService.save(exercise);

        // Search the exercise
        restExerciseMockMvc.perform(get("/api/_search/exercises?query=id:" + exercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercise.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
