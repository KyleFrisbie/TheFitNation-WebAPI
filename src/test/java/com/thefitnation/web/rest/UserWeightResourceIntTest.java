package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserWeight;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserWeightRepository;
import com.thefitnation.service.UserWeightService;

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
 * Test class for the UserWeightResource REST controller.
 *
 * @see UserWeightResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserWeightResourceIntTest {

    private static final ZonedDateTime DEFAULT_WEIGHT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WEIGHT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    @Inject
    private UserWeightRepository userWeightRepository;

    @Inject
    private UserWeightService userWeightService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserWeightMockMvc;

    private UserWeight userWeight;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserWeightResource userWeightResource = new UserWeightResource();
        ReflectionTestUtils.setField(userWeightResource, "userWeightService", userWeightService);
        this.restUserWeightMockMvc = MockMvcBuilders.standaloneSetup(userWeightResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWeight createEntity(EntityManager em) {
        UserWeight userWeight = new UserWeight()
                .weight_date(DEFAULT_WEIGHT_DATE)
                .weight(DEFAULT_WEIGHT);
        // Add required entity
        UserDemographic userDemographic = UserDemographicResourceIntTest.createEntity(em);
        em.persist(userDemographic);
        em.flush();
        userWeight.setUserDemographic(userDemographic);
        return userWeight;
    }

    @Before
    public void initTest() {
        userWeight = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWeight() throws Exception {
        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        // Create the UserWeight

        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeight)))
            .andExpect(status().isCreated());

        // Validate the UserWeight in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeCreate + 1);
        UserWeight testUserWeight = userWeightList.get(userWeightList.size() - 1);
        assertThat(testUserWeight.getWeight_date()).isEqualTo(DEFAULT_WEIGHT_DATE);
        assertThat(testUserWeight.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createUserWeightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        // Create the UserWeight with an existing ID
        UserWeight existingUserWeight = new UserWeight();
        existingUserWeight.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserWeight)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWeight_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWeightRepository.findAll().size();
        // set the field null
        userWeight.setWeight_date(null);

        // Create the UserWeight, which fails.

        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeight)))
            .andExpect(status().isBadRequest());

        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWeightRepository.findAll().size();
        // set the field null
        userWeight.setWeight(null);

        // Create the UserWeight, which fails.

        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeight)))
            .andExpect(status().isBadRequest());

        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserWeights() throws Exception {
        // Initialize the database
        userWeightRepository.saveAndFlush(userWeight);

        // Get all the userWeightList
        restUserWeightMockMvc.perform(get("/api/user-weights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWeight.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight_date").value(hasItem(sameInstant(DEFAULT_WEIGHT_DATE))))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())));
    }

    @Test
    @Transactional
    public void getUserWeight() throws Exception {
        // Initialize the database
        userWeightRepository.saveAndFlush(userWeight);

        // Get the userWeight
        restUserWeightMockMvc.perform(get("/api/user-weights/{id}", userWeight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userWeight.getId().intValue()))
            .andExpect(jsonPath("$.weight_date").value(sameInstant(DEFAULT_WEIGHT_DATE)))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserWeight() throws Exception {
        // Get the userWeight
        restUserWeightMockMvc.perform(get("/api/user-weights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserWeight() throws Exception {
        // Initialize the database
        userWeightService.save(userWeight);

        int databaseSizeBeforeUpdate = userWeightRepository.findAll().size();

        // Update the userWeight
        UserWeight updatedUserWeight = userWeightRepository.findOne(userWeight.getId());
        updatedUserWeight
                .weight_date(UPDATED_WEIGHT_DATE)
                .weight(UPDATED_WEIGHT);

        restUserWeightMockMvc.perform(put("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserWeight)))
            .andExpect(status().isOk());

        // Validate the UserWeight in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeUpdate);
        UserWeight testUserWeight = userWeightList.get(userWeightList.size() - 1);
        assertThat(testUserWeight.getWeight_date()).isEqualTo(UPDATED_WEIGHT_DATE);
        assertThat(testUserWeight.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWeight() throws Exception {
        int databaseSizeBeforeUpdate = userWeightRepository.findAll().size();

        // Create the UserWeight

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserWeightMockMvc.perform(put("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeight)))
            .andExpect(status().isCreated());

        // Validate the UserWeight in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserWeight() throws Exception {
        // Initialize the database
        userWeightService.save(userWeight);

        int databaseSizeBeforeDelete = userWeightRepository.findAll().size();

        // Get the userWeight
        restUserWeightMockMvc.perform(delete("/api/user-weights/{id}", userWeight.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
