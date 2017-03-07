package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.Gym;
import com.thefitnation.domain.Location;
import com.thefitnation.repository.GymRepository;
import com.thefitnation.service.GymService;
import com.thefitnation.service.dto.GymDTO;
import com.thefitnation.service.mapper.GymMapper;
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
 * Test class for the GymResource REST controller.
 *
 * @see GymResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class GymResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private GymMapper gymMapper;

    @Autowired
    private GymService gymService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGymMockMvc;

    private Gym gym;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GymResource gymResource = new GymResource(gymService);
        this.restGymMockMvc = MockMvcBuilders.standaloneSetup(gymResource)
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
    public static Gym createEntity(EntityManager em) {
        Gym gym = new Gym()
                .name(DEFAULT_NAME)
                .notes(DEFAULT_NOTES);
        // Add required entity
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        gym.setLocation(location);
        return gym;
    }

    @Before
    public void initTest() {
        gym = createEntity(em);
    }

    @Test
    @Transactional
    public void createGym() throws Exception {
        int databaseSizeBeforeCreate = gymRepository.findAll().size();

        // Create the Gym
        GymDTO gymDTO = gymMapper.gymToGymDTO(gym);

        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gymDTO)))
            .andExpect(status().isCreated());

        // Validate the Gym in the database
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeCreate + 1);
        Gym testGym = gymList.get(gymList.size() - 1);
        assertThat(testGym.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGym.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createGymWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gymRepository.findAll().size();

        // Create the Gym with an existing ID
        Gym existingGym = new Gym();
        existingGym.setId(1L);
        GymDTO existingGymDTO = gymMapper.gymToGymDTO(existingGym);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGymDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gymRepository.findAll().size();
        // set the field null
        gym.setName(null);

        // Create the Gym, which fails.
        GymDTO gymDTO = gymMapper.gymToGymDTO(gym);

        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gymDTO)))
            .andExpect(status().isBadRequest());

        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGyms() throws Exception {
        // Initialize the database
        gymRepository.saveAndFlush(gym);

        // Get all the gymList
        restGymMockMvc.perform(get("/api/gyms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gym.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getGym() throws Exception {
        // Initialize the database
        gymRepository.saveAndFlush(gym);

        // Get the gym
        restGymMockMvc.perform(get("/api/gyms/{id}", gym.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gym.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGym() throws Exception {
        // Get the gym
        restGymMockMvc.perform(get("/api/gyms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGym() throws Exception {
        // Initialize the database
        gymRepository.saveAndFlush(gym);
        int databaseSizeBeforeUpdate = gymRepository.findAll().size();

        // Update the gym
        Gym updatedGym = gymRepository.findOne(gym.getId());
        updatedGym
                .name(UPDATED_NAME)
                .notes(UPDATED_NOTES);
        GymDTO gymDTO = gymMapper.gymToGymDTO(updatedGym);

        restGymMockMvc.perform(put("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gymDTO)))
            .andExpect(status().isOk());

        // Validate the Gym in the database
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeUpdate);
        Gym testGym = gymList.get(gymList.size() - 1);
        assertThat(testGym.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGym.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingGym() throws Exception {
        int databaseSizeBeforeUpdate = gymRepository.findAll().size();

        // Create the Gym
        GymDTO gymDTO = gymMapper.gymToGymDTO(gym);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGymMockMvc.perform(put("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gymDTO)))
            .andExpect(status().isCreated());

        // Validate the Gym in the database
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGym() throws Exception {
        // Initialize the database
        gymRepository.saveAndFlush(gym);
        int databaseSizeBeforeDelete = gymRepository.findAll().size();

        // Get the gym
        restGymMockMvc.perform(delete("/api/gyms/{id}", gym.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gym.class);
    }
}
