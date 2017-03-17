package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.domain.Exercise;
import com.thefitnation.domain.Unit;
import com.thefitnation.domain.Unit;
import com.thefitnation.repository.ExerciseInstanceRepository;
import com.thefitnation.service.ExerciseInstanceService;
import com.thefitnation.service.dto.ExerciseInstanceDTO;
import com.thefitnation.service.mapper.ExerciseInstanceMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExerciseInstanceResource REST controller.
 *
 * @see ExerciseInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class ExerciseInstanceResourceIntTest {

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private ExerciseInstanceRepository exerciseInstanceRepository;

    @Autowired
    private ExerciseInstanceMapper exerciseInstanceMapper;

    @Autowired
    private ExerciseInstanceService exerciseInstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExerciseInstanceMockMvc;

    private ExerciseInstance exerciseInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseInstanceResource exerciseInstanceResource = new ExerciseInstanceResource(exerciseInstanceService);
        this.restExerciseInstanceMockMvc = MockMvcBuilders.standaloneSetup(exerciseInstanceResource)
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
    public static ExerciseInstance createEntity(EntityManager em) {
        ExerciseInstance exerciseInstance = new ExerciseInstance()
                .notes(DEFAULT_NOTES);
        // Add required entity
        WorkoutInstance workoutInstance = WorkoutInstanceResourceIntTest.createEntity(em);
        em.persist(workoutInstance);
        em.flush();
        exerciseInstance.setWorkoutInstance(workoutInstance);
        // Add required entity
        Exercise exercise = ExerciseResourceIntTest.createEntity(em);
        em.persist(exercise);
        em.flush();
        exerciseInstance.setExercise(exercise);
        // Add required entity
        Unit repUnit = UnitResourceIntTest.createEntity(em);
        em.persist(repUnit);
        em.flush();
        exerciseInstance.setRepUnit(repUnit);
        // Add required entity
        Unit effortUnit = UnitResourceIntTest.createEntity(em);
        em.persist(effortUnit);
        em.flush();
        exerciseInstance.setEffortUnit(effortUnit);
        return exerciseInstance;
    }

    @Before
    public void initTest() {
        exerciseInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createExerciseInstance() throws Exception {
        int databaseSizeBeforeCreate = exerciseInstanceRepository.findAll().size();

        // Create the ExerciseInstance
        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);

        restExerciseInstanceMockMvc.perform(post("/api/exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseInstance in the database
        List<ExerciseInstance> exerciseInstanceList = exerciseInstanceRepository.findAll();
        assertThat(exerciseInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseInstance testExerciseInstance = exerciseInstanceList.get(exerciseInstanceList.size() - 1);
        assertThat(testExerciseInstance.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createExerciseInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseInstanceRepository.findAll().size();

        // Create the ExerciseInstance with an existing ID
        ExerciseInstance existingExerciseInstance = new ExerciseInstance();
        existingExerciseInstance.setId(1L);
        ExerciseInstanceDTO existingExerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(existingExerciseInstance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseInstanceMockMvc.perform(post("/api/exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExerciseInstanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExerciseInstance> exerciseInstanceList = exerciseInstanceRepository.findAll();
        assertThat(exerciseInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExerciseInstances() throws Exception {
        // Initialize the database
        exerciseInstanceRepository.saveAndFlush(exerciseInstance);

        // Get all the exerciseInstanceList
        restExerciseInstanceMockMvc.perform(get("/api/exercise-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getExerciseInstance() throws Exception {
        // Initialize the database
        exerciseInstanceRepository.saveAndFlush(exerciseInstance);

        // Get the exerciseInstance
        restExerciseInstanceMockMvc.perform(get("/api/exercise-instances/{id}", exerciseInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseInstance.getId().intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExerciseInstance() throws Exception {
        // Get the exerciseInstance
        restExerciseInstanceMockMvc.perform(get("/api/exercise-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseInstance() throws Exception {
        // Initialize the database
        exerciseInstanceRepository.saveAndFlush(exerciseInstance);
        int databaseSizeBeforeUpdate = exerciseInstanceRepository.findAll().size();

        // Update the exerciseInstance
        ExerciseInstance updatedExerciseInstance = exerciseInstanceRepository.findOne(exerciseInstance.getId());
        updatedExerciseInstance
                .notes(UPDATED_NOTES);
        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(updatedExerciseInstance);

        restExerciseInstanceMockMvc.perform(put("/api/exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceDTO)))
            .andExpect(status().isOk());

        // Validate the ExerciseInstance in the database
        List<ExerciseInstance> exerciseInstanceList = exerciseInstanceRepository.findAll();
        assertThat(exerciseInstanceList).hasSize(databaseSizeBeforeUpdate);
        ExerciseInstance testExerciseInstance = exerciseInstanceList.get(exerciseInstanceList.size() - 1);
        assertThat(testExerciseInstance.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingExerciseInstance() throws Exception {
        int databaseSizeBeforeUpdate = exerciseInstanceRepository.findAll().size();

        // Create the ExerciseInstance
        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciseInstanceMockMvc.perform(put("/api/exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseInstance in the database
        List<ExerciseInstance> exerciseInstanceList = exerciseInstanceRepository.findAll();
        assertThat(exerciseInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExerciseInstance() throws Exception {
        // Initialize the database
        exerciseInstanceRepository.saveAndFlush(exerciseInstance);
        int databaseSizeBeforeDelete = exerciseInstanceRepository.findAll().size();

        // Get the exerciseInstance
        restExerciseInstanceMockMvc.perform(delete("/api/exercise-instances/{id}", exerciseInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExerciseInstance> exerciseInstanceList = exerciseInstanceRepository.findAll();
        assertThat(exerciseInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExerciseInstance.class);
    }
}
