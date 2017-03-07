package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.repository.ExerciseInstanceSetRepository;
import com.thefitnation.service.ExerciseInstanceSetService;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.ExerciseInstanceSetMapper;
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
 * Test class for the ExerciseInstanceSetResource REST controller.
 *
 * @see ExerciseInstanceSetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class ExerciseInstanceSetResourceIntTest {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    private static final Float DEFAULT_REQ_QUANTITY = 1F;
    private static final Float UPDATED_REQ_QUANTITY = 2F;

    private static final Float DEFAULT_EFFORT_QUANTITY = 1F;
    private static final Float UPDATED_EFFORT_QUANTITY = 2F;

    private static final Float DEFAULT_REST_TIME = 1F;
    private static final Float UPDATED_REST_TIME = 2F;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    @Autowired
    private ExerciseInstanceSetMapper exerciseInstanceSetMapper;

    @Autowired
    private ExerciseInstanceSetService exerciseInstanceSetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExerciseInstanceSetMockMvc;

    private ExerciseInstanceSet exerciseInstanceSet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseInstanceSetResource exerciseInstanceSetResource = new ExerciseInstanceSetResource(exerciseInstanceSetService);
        this.restExerciseInstanceSetMockMvc = MockMvcBuilders.standaloneSetup(exerciseInstanceSetResource)
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
    public static ExerciseInstanceSet createEntity(EntityManager em) {
        ExerciseInstanceSet exerciseInstanceSet = new ExerciseInstanceSet()
                .orderNumber(DEFAULT_ORDER_NUMBER)
                .reqQuantity(DEFAULT_REQ_QUANTITY)
                .effortQuantity(DEFAULT_EFFORT_QUANTITY)
                .restTime(DEFAULT_REST_TIME)
                .notes(DEFAULT_NOTES);
        // Add required entity
        ExerciseInstance exerciseInstance = ExerciseInstanceResourceIntTest.createEntity(em);
        em.persist(exerciseInstance);
        em.flush();
        exerciseInstanceSet.setExerciseInstance(exerciseInstance);
        return exerciseInstanceSet;
    }

    @Before
    public void initTest() {
        exerciseInstanceSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createExerciseInstanceSet() throws Exception {
        int databaseSizeBeforeCreate = exerciseInstanceSetRepository.findAll().size();

        // Create the ExerciseInstanceSet
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);

        restExerciseInstanceSetMockMvc.perform(post("/api/exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseInstanceSet in the database
        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseInstanceSet testExerciseInstanceSet = exerciseInstanceSetList.get(exerciseInstanceSetList.size() - 1);
        assertThat(testExerciseInstanceSet.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testExerciseInstanceSet.getReqQuantity()).isEqualTo(DEFAULT_REQ_QUANTITY);
        assertThat(testExerciseInstanceSet.getEffortQuantity()).isEqualTo(DEFAULT_EFFORT_QUANTITY);
        assertThat(testExerciseInstanceSet.getRestTime()).isEqualTo(DEFAULT_REST_TIME);
        assertThat(testExerciseInstanceSet.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createExerciseInstanceSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseInstanceSetRepository.findAll().size();

        // Create the ExerciseInstanceSet with an existing ID
        ExerciseInstanceSet existingExerciseInstanceSet = new ExerciseInstanceSet();
        existingExerciseInstanceSet.setId(1L);
        ExerciseInstanceSetDTO existingExerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(existingExerciseInstanceSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseInstanceSetMockMvc.perform(post("/api/exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseInstanceSetRepository.findAll().size();
        // set the field null
        exerciseInstanceSet.setOrderNumber(null);

        // Create the ExerciseInstanceSet, which fails.
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);

        restExerciseInstanceSetMockMvc.perform(post("/api/exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReqQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseInstanceSetRepository.findAll().size();
        // set the field null
        exerciseInstanceSet.setReqQuantity(null);

        // Create the ExerciseInstanceSet, which fails.
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);

        restExerciseInstanceSetMockMvc.perform(post("/api/exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEffortQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseInstanceSetRepository.findAll().size();
        // set the field null
        exerciseInstanceSet.setEffortQuantity(null);

        // Create the ExerciseInstanceSet, which fails.
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);

        restExerciseInstanceSetMockMvc.perform(post("/api/exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExerciseInstanceSets() throws Exception {
        // Initialize the database
        exerciseInstanceSetRepository.saveAndFlush(exerciseInstanceSet);

        // Get all the exerciseInstanceSetList
        restExerciseInstanceSetMockMvc.perform(get("/api/exercise-instance-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseInstanceSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].reqQuantity").value(hasItem(DEFAULT_REQ_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].effortQuantity").value(hasItem(DEFAULT_EFFORT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].restTime").value(hasItem(DEFAULT_REST_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getExerciseInstanceSet() throws Exception {
        // Initialize the database
        exerciseInstanceSetRepository.saveAndFlush(exerciseInstanceSet);

        // Get the exerciseInstanceSet
        restExerciseInstanceSetMockMvc.perform(get("/api/exercise-instance-sets/{id}", exerciseInstanceSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseInstanceSet.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.reqQuantity").value(DEFAULT_REQ_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.effortQuantity").value(DEFAULT_EFFORT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.restTime").value(DEFAULT_REST_TIME.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExerciseInstanceSet() throws Exception {
        // Get the exerciseInstanceSet
        restExerciseInstanceSetMockMvc.perform(get("/api/exercise-instance-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseInstanceSet() throws Exception {
        // Initialize the database
        exerciseInstanceSetRepository.saveAndFlush(exerciseInstanceSet);
        int databaseSizeBeforeUpdate = exerciseInstanceSetRepository.findAll().size();

        // Update the exerciseInstanceSet
        ExerciseInstanceSet updatedExerciseInstanceSet = exerciseInstanceSetRepository.findOne(exerciseInstanceSet.getId());
        updatedExerciseInstanceSet
                .orderNumber(UPDATED_ORDER_NUMBER)
                .reqQuantity(UPDATED_REQ_QUANTITY)
                .effortQuantity(UPDATED_EFFORT_QUANTITY)
                .restTime(UPDATED_REST_TIME)
                .notes(UPDATED_NOTES);
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(updatedExerciseInstanceSet);

        restExerciseInstanceSetMockMvc.perform(put("/api/exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceSetDTO)))
            .andExpect(status().isOk());

        // Validate the ExerciseInstanceSet in the database
        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeUpdate);
        ExerciseInstanceSet testExerciseInstanceSet = exerciseInstanceSetList.get(exerciseInstanceSetList.size() - 1);
        assertThat(testExerciseInstanceSet.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testExerciseInstanceSet.getReqQuantity()).isEqualTo(UPDATED_REQ_QUANTITY);
        assertThat(testExerciseInstanceSet.getEffortQuantity()).isEqualTo(UPDATED_EFFORT_QUANTITY);
        assertThat(testExerciseInstanceSet.getRestTime()).isEqualTo(UPDATED_REST_TIME);
        assertThat(testExerciseInstanceSet.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingExerciseInstanceSet() throws Exception {
        int databaseSizeBeforeUpdate = exerciseInstanceSetRepository.findAll().size();

        // Create the ExerciseInstanceSet
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciseInstanceSetMockMvc.perform(put("/api/exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseInstanceSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseInstanceSet in the database
        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExerciseInstanceSet() throws Exception {
        // Initialize the database
        exerciseInstanceSetRepository.saveAndFlush(exerciseInstanceSet);
        int databaseSizeBeforeDelete = exerciseInstanceSetRepository.findAll().size();

        // Get the exerciseInstanceSet
        restExerciseInstanceSetMockMvc.perform(delete("/api/exercise-instance-sets/{id}", exerciseInstanceSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExerciseInstanceSet> exerciseInstanceSetList = exerciseInstanceSetRepository.findAll();
        assertThat(exerciseInstanceSetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExerciseInstanceSet.class);
    }
}
