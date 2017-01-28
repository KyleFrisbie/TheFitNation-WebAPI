package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.ExerciseSet;
import com.thefitnation.domain.Exercise;
import com.thefitnation.repository.ExerciseSetRepository;
import com.thefitnation.repository.search.ExerciseSetSearchRepository;

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
 * Test class for the ExerciseSetResource REST controller.
 *
 * @see ExerciseSetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class ExerciseSetResourceIntTest {

    private static final Integer DEFAULT_EXERCISE_SET_NUMBER = 1;
    private static final Integer UPDATED_EXERCISE_SET_NUMBER = 2;

    private static final Integer DEFAULT_REPS = 1;
    private static final Integer UPDATED_REPS = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final Integer DEFAULT_REST = 1;
    private static final Integer UPDATED_REST = 2;

    @Inject
    private ExerciseSetRepository exerciseSetRepository;

    @Inject
    private ExerciseSetSearchRepository exerciseSetSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExerciseSetMockMvc;

    private ExerciseSet exerciseSet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseSetResource exerciseSetResource = new ExerciseSetResource();
        ReflectionTestUtils.setField(exerciseSetResource, "exerciseSetSearchRepository", exerciseSetSearchRepository);
        ReflectionTestUtils.setField(exerciseSetResource, "exerciseSetRepository", exerciseSetRepository);
        this.restExerciseSetMockMvc = MockMvcBuilders.standaloneSetup(exerciseSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExerciseSet createEntity(EntityManager em) {
        ExerciseSet exerciseSet = new ExerciseSet()
                .exercise_set_number(DEFAULT_EXERCISE_SET_NUMBER)
                .reps(DEFAULT_REPS)
                .weight(DEFAULT_WEIGHT)
                .rest(DEFAULT_REST);
        // Add required entity
        Exercise exercise = ExerciseResourceIntTest.createEntity(em);
        em.persist(exercise);
        em.flush();
        exerciseSet.setExercise(exercise);
        return exerciseSet;
    }

    @Before
    public void initTest() {
        exerciseSetSearchRepository.deleteAll();
        exerciseSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createExerciseSet() throws Exception {
        int databaseSizeBeforeCreate = exerciseSetRepository.findAll().size();

        // Create the ExerciseSet

        restExerciseSetMockMvc.perform(post("/api/exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseSet)))
            .andExpect(status().isCreated());

        // Validate the ExerciseSet in the database
        List<ExerciseSet> exerciseSetList = exerciseSetRepository.findAll();
        assertThat(exerciseSetList).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseSet testExerciseSet = exerciseSetList.get(exerciseSetList.size() - 1);
        assertThat(testExerciseSet.getExercise_set_number()).isEqualTo(DEFAULT_EXERCISE_SET_NUMBER);
        assertThat(testExerciseSet.getReps()).isEqualTo(DEFAULT_REPS);
        assertThat(testExerciseSet.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testExerciseSet.getRest()).isEqualTo(DEFAULT_REST);

        // Validate the ExerciseSet in ElasticSearch
        ExerciseSet exerciseSetEs = exerciseSetSearchRepository.findOne(testExerciseSet.getId());
        assertThat(exerciseSetEs).isEqualToComparingFieldByField(testExerciseSet);
    }

    @Test
    @Transactional
    public void createExerciseSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseSetRepository.findAll().size();

        // Create the ExerciseSet with an existing ID
        ExerciseSet existingExerciseSet = new ExerciseSet();
        existingExerciseSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseSetMockMvc.perform(post("/api/exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExerciseSet)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExerciseSet> exerciseSetList = exerciseSetRepository.findAll();
        assertThat(exerciseSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkExercise_set_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseSetRepository.findAll().size();
        // set the field null
        exerciseSet.setExercise_set_number(null);

        // Create the ExerciseSet, which fails.

        restExerciseSetMockMvc.perform(post("/api/exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseSet)))
            .andExpect(status().isBadRequest());

        List<ExerciseSet> exerciseSetList = exerciseSetRepository.findAll();
        assertThat(exerciseSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepsIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseSetRepository.findAll().size();
        // set the field null
        exerciseSet.setReps(null);

        // Create the ExerciseSet, which fails.

        restExerciseSetMockMvc.perform(post("/api/exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseSet)))
            .andExpect(status().isBadRequest());

        List<ExerciseSet> exerciseSetList = exerciseSetRepository.findAll();
        assertThat(exerciseSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExerciseSets() throws Exception {
        // Initialize the database
        exerciseSetRepository.saveAndFlush(exerciseSet);

        // Get all the exerciseSetList
        restExerciseSetMockMvc.perform(get("/api/exercise-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].exercise_set_number").value(hasItem(DEFAULT_EXERCISE_SET_NUMBER)))
            .andExpect(jsonPath("$.[*].reps").value(hasItem(DEFAULT_REPS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].rest").value(hasItem(DEFAULT_REST)));
    }

    @Test
    @Transactional
    public void getExerciseSet() throws Exception {
        // Initialize the database
        exerciseSetRepository.saveAndFlush(exerciseSet);

        // Get the exerciseSet
        restExerciseSetMockMvc.perform(get("/api/exercise-sets/{id}", exerciseSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseSet.getId().intValue()))
            .andExpect(jsonPath("$.exercise_set_number").value(DEFAULT_EXERCISE_SET_NUMBER))
            .andExpect(jsonPath("$.reps").value(DEFAULT_REPS))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.rest").value(DEFAULT_REST));
    }

    @Test
    @Transactional
    public void getNonExistingExerciseSet() throws Exception {
        // Get the exerciseSet
        restExerciseSetMockMvc.perform(get("/api/exercise-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseSet() throws Exception {
        // Initialize the database
        exerciseSetRepository.saveAndFlush(exerciseSet);
        exerciseSetSearchRepository.save(exerciseSet);
        int databaseSizeBeforeUpdate = exerciseSetRepository.findAll().size();

        // Update the exerciseSet
        ExerciseSet updatedExerciseSet = exerciseSetRepository.findOne(exerciseSet.getId());
        updatedExerciseSet
                .exercise_set_number(UPDATED_EXERCISE_SET_NUMBER)
                .reps(UPDATED_REPS)
                .weight(UPDATED_WEIGHT)
                .rest(UPDATED_REST);

        restExerciseSetMockMvc.perform(put("/api/exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExerciseSet)))
            .andExpect(status().isOk());

        // Validate the ExerciseSet in the database
        List<ExerciseSet> exerciseSetList = exerciseSetRepository.findAll();
        assertThat(exerciseSetList).hasSize(databaseSizeBeforeUpdate);
        ExerciseSet testExerciseSet = exerciseSetList.get(exerciseSetList.size() - 1);
        assertThat(testExerciseSet.getExercise_set_number()).isEqualTo(UPDATED_EXERCISE_SET_NUMBER);
        assertThat(testExerciseSet.getReps()).isEqualTo(UPDATED_REPS);
        assertThat(testExerciseSet.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testExerciseSet.getRest()).isEqualTo(UPDATED_REST);

        // Validate the ExerciseSet in ElasticSearch
        ExerciseSet exerciseSetEs = exerciseSetSearchRepository.findOne(testExerciseSet.getId());
        assertThat(exerciseSetEs).isEqualToComparingFieldByField(testExerciseSet);
    }

    @Test
    @Transactional
    public void updateNonExistingExerciseSet() throws Exception {
        int databaseSizeBeforeUpdate = exerciseSetRepository.findAll().size();

        // Create the ExerciseSet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciseSetMockMvc.perform(put("/api/exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseSet)))
            .andExpect(status().isCreated());

        // Validate the ExerciseSet in the database
        List<ExerciseSet> exerciseSetList = exerciseSetRepository.findAll();
        assertThat(exerciseSetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExerciseSet() throws Exception {
        // Initialize the database
        exerciseSetRepository.saveAndFlush(exerciseSet);
        exerciseSetSearchRepository.save(exerciseSet);
        int databaseSizeBeforeDelete = exerciseSetRepository.findAll().size();

        // Get the exerciseSet
        restExerciseSetMockMvc.perform(delete("/api/exercise-sets/{id}", exerciseSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean exerciseSetExistsInEs = exerciseSetSearchRepository.exists(exerciseSet.getId());
        assertThat(exerciseSetExistsInEs).isFalse();

        // Validate the database is empty
        List<ExerciseSet> exerciseSetList = exerciseSetRepository.findAll();
        assertThat(exerciseSetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExerciseSet() throws Exception {
        // Initialize the database
        exerciseSetRepository.saveAndFlush(exerciseSet);
        exerciseSetSearchRepository.save(exerciseSet);

        // Search the exerciseSet
        restExerciseSetMockMvc.perform(get("/api/_search/exercise-sets?query=id:" + exerciseSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].exercise_set_number").value(hasItem(DEFAULT_EXERCISE_SET_NUMBER)))
            .andExpect(jsonPath("$.[*].reps").value(hasItem(DEFAULT_REPS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].rest").value(hasItem(DEFAULT_REST)));
    }
}
