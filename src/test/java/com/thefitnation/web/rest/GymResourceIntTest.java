package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.Gym;
import com.thefitnation.repository.GymRepository;
import com.thefitnation.service.GymService;
import com.thefitnation.repository.search.GymSearchRepository;

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
 * Test class for the GymResource REST controller.
 *
 * @see GymResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class GymResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_VISITED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_VISITED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private GymRepository gymRepository;

    @Inject
    private GymService gymService;

    @Inject
    private GymSearchRepository gymSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGymMockMvc;

    private Gym gym;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GymResource gymResource = new GymResource();
        ReflectionTestUtils.setField(gymResource, "gymService", gymService);
        this.restGymMockMvc = MockMvcBuilders.standaloneSetup(gymResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
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
                .location(DEFAULT_LOCATION)
                .last_visited(DEFAULT_LAST_VISITED);
        return gym;
    }

    @Before
    public void initTest() {
        gymSearchRepository.deleteAll();
        gym = createEntity(em);
    }

    @Test
    @Transactional
    public void createGym() throws Exception {
        int databaseSizeBeforeCreate = gymRepository.findAll().size();

        // Create the Gym

        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gym)))
            .andExpect(status().isCreated());

        // Validate the Gym in the database
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeCreate + 1);
        Gym testGym = gymList.get(gymList.size() - 1);
        assertThat(testGym.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGym.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testGym.getLast_visited()).isEqualTo(DEFAULT_LAST_VISITED);

        // Validate the Gym in ElasticSearch
        Gym gymEs = gymSearchRepository.findOne(testGym.getId());
        assertThat(gymEs).isEqualToComparingFieldByField(testGym);
    }

    @Test
    @Transactional
    public void createGymWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gymRepository.findAll().size();

        // Create the Gym with an existing ID
        Gym existingGym = new Gym();
        existingGym.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGym)))
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

        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gym)))
            .andExpect(status().isBadRequest());

        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = gymRepository.findAll().size();
        // set the field null
        gym.setLocation(null);

        // Create the Gym, which fails.

        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gym)))
            .andExpect(status().isBadRequest());

        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_visitedIsRequired() throws Exception {
        int databaseSizeBeforeTest = gymRepository.findAll().size();
        // set the field null
        gym.setLast_visited(null);

        // Create the Gym, which fails.

        restGymMockMvc.perform(post("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gym)))
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
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].last_visited").value(hasItem(sameInstant(DEFAULT_LAST_VISITED))));
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
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.last_visited").value(sameInstant(DEFAULT_LAST_VISITED)));
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
        gymService.save(gym);

        int databaseSizeBeforeUpdate = gymRepository.findAll().size();

        // Update the gym
        Gym updatedGym = gymRepository.findOne(gym.getId());
        updatedGym
                .name(UPDATED_NAME)
                .location(UPDATED_LOCATION)
                .last_visited(UPDATED_LAST_VISITED);

        restGymMockMvc.perform(put("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGym)))
            .andExpect(status().isOk());

        // Validate the Gym in the database
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeUpdate);
        Gym testGym = gymList.get(gymList.size() - 1);
        assertThat(testGym.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGym.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testGym.getLast_visited()).isEqualTo(UPDATED_LAST_VISITED);

        // Validate the Gym in ElasticSearch
        Gym gymEs = gymSearchRepository.findOne(testGym.getId());
        assertThat(gymEs).isEqualToComparingFieldByField(testGym);
    }

    @Test
    @Transactional
    public void updateNonExistingGym() throws Exception {
        int databaseSizeBeforeUpdate = gymRepository.findAll().size();

        // Create the Gym

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGymMockMvc.perform(put("/api/gyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gym)))
            .andExpect(status().isCreated());

        // Validate the Gym in the database
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGym() throws Exception {
        // Initialize the database
        gymService.save(gym);

        int databaseSizeBeforeDelete = gymRepository.findAll().size();

        // Get the gym
        restGymMockMvc.perform(delete("/api/gyms/{id}", gym.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean gymExistsInEs = gymSearchRepository.exists(gym.getId());
        assertThat(gymExistsInEs).isFalse();

        // Validate the database is empty
        List<Gym> gymList = gymRepository.findAll();
        assertThat(gymList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGym() throws Exception {
        // Initialize the database
        gymService.save(gym);

        // Search the gym
        restGymMockMvc.perform(get("/api/_search/gyms?query=id:" + gym.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gym.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].last_visited").value(hasItem(sameInstant(DEFAULT_LAST_VISITED))));
    }
}
