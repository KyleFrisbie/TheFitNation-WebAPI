package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserExerciseSet;
import com.thefitnation.domain.UserExercise;
import com.thefitnation.repository.UserExerciseSetRepository;
import com.thefitnation.service.UserExerciseSetService;
import com.thefitnation.repository.search.UserExerciseSetSearchRepository;

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
 * Test class for the UserExerciseSetResource REST controller.
 *
 * @see UserExerciseSetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserExerciseSetResourceIntTest {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    private static final Integer DEFAULT_REPS = 0;
    private static final Integer UPDATED_REPS = 1;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Integer DEFAULT_REST = 0;
    private static final Integer UPDATED_REST = 1;

    @Inject
    private UserExerciseSetRepository userExerciseSetRepository;

    @Inject
    private UserExerciseSetService userExerciseSetService;

    @Inject
    private UserExerciseSetSearchRepository userExerciseSetSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserExerciseSetMockMvc;

    private UserExerciseSet userExerciseSet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserExerciseSetResource userExerciseSetResource = new UserExerciseSetResource();
        ReflectionTestUtils.setField(userExerciseSetResource, "userExerciseSetService", userExerciseSetService);
        this.restUserExerciseSetMockMvc = MockMvcBuilders.standaloneSetup(userExerciseSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExerciseSet createEntity(EntityManager em) {
        UserExerciseSet userExerciseSet = new UserExerciseSet()
                .order_number(DEFAULT_ORDER_NUMBER)
                .reps(DEFAULT_REPS)
                .weight(DEFAULT_WEIGHT)
                .rest(DEFAULT_REST);
        // Add required entity
        UserExercise userExercise = UserExerciseResourceIntTest.createEntity(em);
        em.persist(userExercise);
        em.flush();
        userExerciseSet.setUserExercise(userExercise);
        return userExerciseSet;
    }

    @Before
    public void initTest() {
        userExerciseSetSearchRepository.deleteAll();
        userExerciseSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExerciseSet() throws Exception {
        int databaseSizeBeforeCreate = userExerciseSetRepository.findAll().size();

        // Create the UserExerciseSet

        restUserExerciseSetMockMvc.perform(post("/api/user-exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseSet)))
            .andExpect(status().isCreated());

        // Validate the UserExerciseSet in the database
        List<UserExerciseSet> userExerciseSetList = userExerciseSetRepository.findAll();
        assertThat(userExerciseSetList).hasSize(databaseSizeBeforeCreate + 1);
        UserExerciseSet testUserExerciseSet = userExerciseSetList.get(userExerciseSetList.size() - 1);
        assertThat(testUserExerciseSet.getOrder_number()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testUserExerciseSet.getReps()).isEqualTo(DEFAULT_REPS);
        assertThat(testUserExerciseSet.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testUserExerciseSet.getRest()).isEqualTo(DEFAULT_REST);

        // Validate the UserExerciseSet in ElasticSearch
        UserExerciseSet userExerciseSetEs = userExerciseSetSearchRepository.findOne(testUserExerciseSet.getId());
        assertThat(userExerciseSetEs).isEqualToComparingFieldByField(testUserExerciseSet);
    }

    @Test
    @Transactional
    public void createUserExerciseSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExerciseSetRepository.findAll().size();

        // Create the UserExerciseSet with an existing ID
        UserExerciseSet existingUserExerciseSet = new UserExerciseSet();
        existingUserExerciseSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExerciseSetMockMvc.perform(post("/api/user-exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserExerciseSet)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserExerciseSet> userExerciseSetList = userExerciseSetRepository.findAll();
        assertThat(userExerciseSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrder_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExerciseSetRepository.findAll().size();
        // set the field null
        userExerciseSet.setOrder_number(null);

        // Create the UserExerciseSet, which fails.

        restUserExerciseSetMockMvc.perform(post("/api/user-exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseSet)))
            .andExpect(status().isBadRequest());

        List<UserExerciseSet> userExerciseSetList = userExerciseSetRepository.findAll();
        assertThat(userExerciseSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExerciseSetRepository.findAll().size();
        // set the field null
        userExerciseSet.setReps(null);

        // Create the UserExerciseSet, which fails.

        restUserExerciseSetMockMvc.perform(post("/api/user-exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseSet)))
            .andExpect(status().isBadRequest());

        List<UserExerciseSet> userExerciseSetList = userExerciseSetRepository.findAll();
        assertThat(userExerciseSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserExerciseSets() throws Exception {
        // Initialize the database
        userExerciseSetRepository.saveAndFlush(userExerciseSet);

        // Get all the userExerciseSetList
        restUserExerciseSetMockMvc.perform(get("/api/user-exercise-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExerciseSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].order_number").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].reps").value(hasItem(DEFAULT_REPS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].rest").value(hasItem(DEFAULT_REST)));
    }

    @Test
    @Transactional
    public void getUserExerciseSet() throws Exception {
        // Initialize the database
        userExerciseSetRepository.saveAndFlush(userExerciseSet);

        // Get the userExerciseSet
        restUserExerciseSetMockMvc.perform(get("/api/user-exercise-sets/{id}", userExerciseSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExerciseSet.getId().intValue()))
            .andExpect(jsonPath("$.order_number").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.reps").value(DEFAULT_REPS))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.rest").value(DEFAULT_REST));
    }

    @Test
    @Transactional
    public void getNonExistingUserExerciseSet() throws Exception {
        // Get the userExerciseSet
        restUserExerciseSetMockMvc.perform(get("/api/user-exercise-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExerciseSet() throws Exception {
        // Initialize the database
        userExerciseSetService.save(userExerciseSet);

        int databaseSizeBeforeUpdate = userExerciseSetRepository.findAll().size();

        // Update the userExerciseSet
        UserExerciseSet updatedUserExerciseSet = userExerciseSetRepository.findOne(userExerciseSet.getId());
        updatedUserExerciseSet
                .order_number(UPDATED_ORDER_NUMBER)
                .reps(UPDATED_REPS)
                .weight(UPDATED_WEIGHT)
                .rest(UPDATED_REST);

        restUserExerciseSetMockMvc.perform(put("/api/user-exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExerciseSet)))
            .andExpect(status().isOk());

        // Validate the UserExerciseSet in the database
        List<UserExerciseSet> userExerciseSetList = userExerciseSetRepository.findAll();
        assertThat(userExerciseSetList).hasSize(databaseSizeBeforeUpdate);
        UserExerciseSet testUserExerciseSet = userExerciseSetList.get(userExerciseSetList.size() - 1);
        assertThat(testUserExerciseSet.getOrder_number()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testUserExerciseSet.getReps()).isEqualTo(UPDATED_REPS);
        assertThat(testUserExerciseSet.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testUserExerciseSet.getRest()).isEqualTo(UPDATED_REST);

        // Validate the UserExerciseSet in ElasticSearch
        UserExerciseSet userExerciseSetEs = userExerciseSetSearchRepository.findOne(testUserExerciseSet.getId());
        assertThat(userExerciseSetEs).isEqualToComparingFieldByField(testUserExerciseSet);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExerciseSet() throws Exception {
        int databaseSizeBeforeUpdate = userExerciseSetRepository.findAll().size();

        // Create the UserExerciseSet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserExerciseSetMockMvc.perform(put("/api/user-exercise-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseSet)))
            .andExpect(status().isCreated());

        // Validate the UserExerciseSet in the database
        List<UserExerciseSet> userExerciseSetList = userExerciseSetRepository.findAll();
        assertThat(userExerciseSetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserExerciseSet() throws Exception {
        // Initialize the database
        userExerciseSetService.save(userExerciseSet);

        int databaseSizeBeforeDelete = userExerciseSetRepository.findAll().size();

        // Get the userExerciseSet
        restUserExerciseSetMockMvc.perform(delete("/api/user-exercise-sets/{id}", userExerciseSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean userExerciseSetExistsInEs = userExerciseSetSearchRepository.exists(userExerciseSet.getId());
        assertThat(userExerciseSetExistsInEs).isFalse();

        // Validate the database is empty
        List<UserExerciseSet> userExerciseSetList = userExerciseSetRepository.findAll();
        assertThat(userExerciseSetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserExerciseSet() throws Exception {
        // Initialize the database
        userExerciseSetService.save(userExerciseSet);

        // Search the userExerciseSet
        restUserExerciseSetMockMvc.perform(get("/api/_search/user-exercise-sets?query=id:" + userExerciseSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExerciseSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].order_number").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].reps").value(hasItem(DEFAULT_REPS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].rest").value(hasItem(DEFAULT_REST)));
    }
}
