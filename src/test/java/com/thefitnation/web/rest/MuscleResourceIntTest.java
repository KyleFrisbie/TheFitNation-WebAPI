package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.Muscle;
import com.thefitnation.domain.BodyPart;
import com.thefitnation.repository.MuscleRepository;
import com.thefitnation.service.MuscleService;
import com.thefitnation.service.dto.MuscleDTO;
import com.thefitnation.service.mapper.MuscleMapper;
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
 * Test class for the MuscleResource REST controller.
 *
 * @see MuscleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class MuscleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MuscleRepository muscleRepository;

    @Autowired
    private MuscleMapper muscleMapper;

    @Autowired
    private MuscleService muscleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMuscleMockMvc;

    private Muscle muscle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MuscleResource muscleResource = new MuscleResource(muscleService);
        this.restMuscleMockMvc = MockMvcBuilders.standaloneSetup(muscleResource)
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
    public static Muscle createEntity(EntityManager em) {
        Muscle muscle = new Muscle()
                .name(DEFAULT_NAME);
        // Add required entity
        BodyPart bodyPart = BodyPartResourceIntTest.createEntity(em);
        em.persist(bodyPart);
        em.flush();
        muscle.setBodyPart(bodyPart);
        return muscle;
    }

    @Before
    public void initTest() {
        muscle = createEntity(em);
    }

    @Test
    @Transactional
    public void createMuscle() throws Exception {
        int databaseSizeBeforeCreate = muscleRepository.findAll().size();

        // Create the Muscle
        MuscleDTO muscleDTO = muscleMapper.muscleToMuscleDTO(muscle);

        restMuscleMockMvc.perform(post("/api/muscles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(muscleDTO)))
            .andExpect(status().isCreated());

        // Validate the Muscle in the database
        List<Muscle> muscleList = muscleRepository.findAll();
        assertThat(muscleList).hasSize(databaseSizeBeforeCreate + 1);
        Muscle testMuscle = muscleList.get(muscleList.size() - 1);
        assertThat(testMuscle.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMuscleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = muscleRepository.findAll().size();

        // Create the Muscle with an existing ID
        Muscle existingMuscle = new Muscle();
        existingMuscle.setId(1L);
        MuscleDTO existingMuscleDTO = muscleMapper.muscleToMuscleDTO(existingMuscle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuscleMockMvc.perform(post("/api/muscles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMuscleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Muscle> muscleList = muscleRepository.findAll();
        assertThat(muscleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = muscleRepository.findAll().size();
        // set the field null
        muscle.setName(null);

        // Create the Muscle, which fails.
        MuscleDTO muscleDTO = muscleMapper.muscleToMuscleDTO(muscle);

        restMuscleMockMvc.perform(post("/api/muscles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(muscleDTO)))
            .andExpect(status().isBadRequest());

        List<Muscle> muscleList = muscleRepository.findAll();
        assertThat(muscleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMuscles() throws Exception {
        // Initialize the database
        muscleRepository.saveAndFlush(muscle);

        // Get all the muscleList
        restMuscleMockMvc.perform(get("/api/muscles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(muscle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMuscle() throws Exception {
        // Initialize the database
        muscleRepository.saveAndFlush(muscle);

        // Get the muscle
        restMuscleMockMvc.perform(get("/api/muscles/{id}", muscle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(muscle.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMuscle() throws Exception {
        // Get the muscle
        restMuscleMockMvc.perform(get("/api/muscles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMuscle() throws Exception {
        // Initialize the database
        muscleRepository.saveAndFlush(muscle);
        int databaseSizeBeforeUpdate = muscleRepository.findAll().size();

        // Update the muscle
        Muscle updatedMuscle = muscleRepository.findOne(muscle.getId());
        updatedMuscle
                .name(UPDATED_NAME);
        MuscleDTO muscleDTO = muscleMapper.muscleToMuscleDTO(updatedMuscle);

        restMuscleMockMvc.perform(put("/api/muscles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(muscleDTO)))
            .andExpect(status().isOk());

        // Validate the Muscle in the database
        List<Muscle> muscleList = muscleRepository.findAll();
        assertThat(muscleList).hasSize(databaseSizeBeforeUpdate);
        Muscle testMuscle = muscleList.get(muscleList.size() - 1);
        assertThat(testMuscle.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMuscle() throws Exception {
        int databaseSizeBeforeUpdate = muscleRepository.findAll().size();

        // Create the Muscle
        MuscleDTO muscleDTO = muscleMapper.muscleToMuscleDTO(muscle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMuscleMockMvc.perform(put("/api/muscles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(muscleDTO)))
            .andExpect(status().isCreated());

        // Validate the Muscle in the database
        List<Muscle> muscleList = muscleRepository.findAll();
        assertThat(muscleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMuscle() throws Exception {
        // Initialize the database
        muscleRepository.saveAndFlush(muscle);
        int databaseSizeBeforeDelete = muscleRepository.findAll().size();

        // Get the muscle
        restMuscleMockMvc.perform(delete("/api/muscles/{id}", muscle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Muscle> muscleList = muscleRepository.findAll();
        assertThat(muscleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Muscle.class);
    }
}
