package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.ExerciseFamily;
import com.thefitnation.repository.ExerciseFamilyRepository;
import com.thefitnation.service.ExerciseFamilyService;
import com.thefitnation.service.dto.ExerciseFamilyDTO;
import com.thefitnation.service.mapper.ExerciseFamilyMapper;
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
 * Test class for the ExerciseFamilyResource REST controller.
 *
 * @see ExerciseFamilyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class ExerciseFamilyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ExerciseFamilyRepository exerciseFamilyRepository;

    @Autowired
    private ExerciseFamilyMapper exerciseFamilyMapper;

    @Autowired
    private ExerciseFamilyService exerciseFamilyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExerciseFamilyMockMvc;

    private ExerciseFamily exerciseFamily;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseFamilyResource exerciseFamilyResource = new ExerciseFamilyResource(exerciseFamilyService);
        this.restExerciseFamilyMockMvc = MockMvcBuilders.standaloneSetup(exerciseFamilyResource)
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
    public static ExerciseFamily createEntity(EntityManager em) {
        ExerciseFamily exerciseFamily = new ExerciseFamily()
                .name(DEFAULT_NAME);
        return exerciseFamily;
    }

    @Before
    public void initTest() {
        exerciseFamily = createEntity(em);
    }

    @Test
    @Transactional
    public void createExerciseFamily() throws Exception {
        int databaseSizeBeforeCreate = exerciseFamilyRepository.findAll().size();

        // Create the ExerciseFamily
        ExerciseFamilyDTO exerciseFamilyDTO = exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(exerciseFamily);

        restExerciseFamilyMockMvc.perform(post("/api/exercise-families")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseFamilyDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseFamily in the database
        List<ExerciseFamily> exerciseFamilyList = exerciseFamilyRepository.findAll();
        assertThat(exerciseFamilyList).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseFamily testExerciseFamily = exerciseFamilyList.get(exerciseFamilyList.size() - 1);
        assertThat(testExerciseFamily.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createExerciseFamilyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseFamilyRepository.findAll().size();

        // Create the ExerciseFamily with an existing ID
        ExerciseFamily existingExerciseFamily = new ExerciseFamily();
        existingExerciseFamily.setId(1L);
        ExerciseFamilyDTO existingExerciseFamilyDTO = exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(existingExerciseFamily);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseFamilyMockMvc.perform(post("/api/exercise-families")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExerciseFamilyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExerciseFamily> exerciseFamilyList = exerciseFamilyRepository.findAll();
        assertThat(exerciseFamilyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseFamilyRepository.findAll().size();
        // set the field null
        exerciseFamily.setName(null);

        // Create the ExerciseFamily, which fails.
        ExerciseFamilyDTO exerciseFamilyDTO = exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(exerciseFamily);

        restExerciseFamilyMockMvc.perform(post("/api/exercise-families")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseFamilyDTO)))
            .andExpect(status().isBadRequest());

        List<ExerciseFamily> exerciseFamilyList = exerciseFamilyRepository.findAll();
        assertThat(exerciseFamilyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExerciseFamilies() throws Exception {
        // Initialize the database
        exerciseFamilyRepository.saveAndFlush(exerciseFamily);

        // Get all the exerciseFamilyList
        restExerciseFamilyMockMvc.perform(get("/api/exercise-families?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseFamily.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getExerciseFamily() throws Exception {
        // Initialize the database
        exerciseFamilyRepository.saveAndFlush(exerciseFamily);

        // Get the exerciseFamily
        restExerciseFamilyMockMvc.perform(get("/api/exercise-families/{id}", exerciseFamily.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseFamily.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExerciseFamily() throws Exception {
        // Get the exerciseFamily
        restExerciseFamilyMockMvc.perform(get("/api/exercise-families/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseFamily() throws Exception {
        // Initialize the database
        exerciseFamilyRepository.saveAndFlush(exerciseFamily);
        int databaseSizeBeforeUpdate = exerciseFamilyRepository.findAll().size();

        // Update the exerciseFamily
        ExerciseFamily updatedExerciseFamily = exerciseFamilyRepository.findOne(exerciseFamily.getId());
        updatedExerciseFamily
                .name(UPDATED_NAME);
        ExerciseFamilyDTO exerciseFamilyDTO = exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(updatedExerciseFamily);

        restExerciseFamilyMockMvc.perform(put("/api/exercise-families")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseFamilyDTO)))
            .andExpect(status().isOk());

        // Validate the ExerciseFamily in the database
        List<ExerciseFamily> exerciseFamilyList = exerciseFamilyRepository.findAll();
        assertThat(exerciseFamilyList).hasSize(databaseSizeBeforeUpdate);
        ExerciseFamily testExerciseFamily = exerciseFamilyList.get(exerciseFamilyList.size() - 1);
        assertThat(testExerciseFamily.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingExerciseFamily() throws Exception {
        int databaseSizeBeforeUpdate = exerciseFamilyRepository.findAll().size();

        // Create the ExerciseFamily
        ExerciseFamilyDTO exerciseFamilyDTO = exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(exerciseFamily);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciseFamilyMockMvc.perform(put("/api/exercise-families")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseFamilyDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseFamily in the database
        List<ExerciseFamily> exerciseFamilyList = exerciseFamilyRepository.findAll();
        assertThat(exerciseFamilyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExerciseFamily() throws Exception {
        // Initialize the database
        exerciseFamilyRepository.saveAndFlush(exerciseFamily);
        int databaseSizeBeforeDelete = exerciseFamilyRepository.findAll().size();

        // Get the exerciseFamily
        restExerciseFamilyMockMvc.perform(delete("/api/exercise-families/{id}", exerciseFamily.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExerciseFamily> exerciseFamilyList = exerciseFamilyRepository.findAll();
        assertThat(exerciseFamilyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExerciseFamily.class);
    }
}
