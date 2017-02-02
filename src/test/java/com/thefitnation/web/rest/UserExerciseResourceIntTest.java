package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserExercise;
import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.domain.Exercise;
import com.thefitnation.repository.UserExerciseRepository;
import com.thefitnation.service.UserExerciseService;
import com.thefitnation.repository.search.UserExerciseSearchRepository;

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
 * Test class for the UserExerciseResource REST controller.
 *
 * @see UserExerciseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserExerciseResourceIntTest {

    @Inject
    private UserExerciseRepository userExerciseRepository;

    @Inject
    private UserExerciseService userExerciseService;

    @Inject
    private UserExerciseSearchRepository userExerciseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserExerciseMockMvc;

    private UserExercise userExercise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserExerciseResource userExerciseResource = new UserExerciseResource();
        ReflectionTestUtils.setField(userExerciseResource, "userExerciseService", userExerciseService);
        this.restUserExerciseMockMvc = MockMvcBuilders.standaloneSetup(userExerciseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExercise createEntity(EntityManager em) {
        UserExercise userExercise = new UserExercise();
        // Add required entity
        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceResourceIntTest.createEntity(em);
        em.persist(userWorkoutInstance);
        em.flush();
        userExercise.setUserWorkoutInstance(userWorkoutInstance);
        // Add required entity
        Exercise exercise = ExerciseResourceIntTest.createEntity(em);
        em.persist(exercise);
        em.flush();
        userExercise.setExercise(exercise);
        return userExercise;
    }

    @Before
    public void initTest() {
        userExerciseSearchRepository.deleteAll();
        userExercise = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExercise() throws Exception {
        int databaseSizeBeforeCreate = userExerciseRepository.findAll().size();

        // Create the UserExercise

        restUserExerciseMockMvc.perform(post("/api/user-exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExercise)))
            .andExpect(status().isCreated());

        // Validate the UserExercise in the database
        List<UserExercise> userExerciseList = userExerciseRepository.findAll();
        assertThat(userExerciseList).hasSize(databaseSizeBeforeCreate + 1);
        UserExercise testUserExercise = userExerciseList.get(userExerciseList.size() - 1);

        // Validate the UserExercise in ElasticSearch
        UserExercise userExerciseEs = userExerciseSearchRepository.findOne(testUserExercise.getId());
        assertThat(userExerciseEs).isEqualToComparingFieldByField(testUserExercise);
    }

    @Test
    @Transactional
    public void createUserExerciseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExerciseRepository.findAll().size();

        // Create the UserExercise with an existing ID
        UserExercise existingUserExercise = new UserExercise();
        existingUserExercise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExerciseMockMvc.perform(post("/api/user-exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserExercise)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserExercise> userExerciseList = userExerciseRepository.findAll();
        assertThat(userExerciseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserExercises() throws Exception {
        // Initialize the database
        userExerciseRepository.saveAndFlush(userExercise);

        // Get all the userExerciseList
        restUserExerciseMockMvc.perform(get("/api/user-exercises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExercise.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUserExercise() throws Exception {
        // Initialize the database
        userExerciseRepository.saveAndFlush(userExercise);

        // Get the userExercise
        restUserExerciseMockMvc.perform(get("/api/user-exercises/{id}", userExercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExercise.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserExercise() throws Exception {
        // Get the userExercise
        restUserExerciseMockMvc.perform(get("/api/user-exercises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExercise() throws Exception {
        // Initialize the database
        userExerciseService.save(userExercise);

        int databaseSizeBeforeUpdate = userExerciseRepository.findAll().size();

        // Update the userExercise
        UserExercise updatedUserExercise = userExerciseRepository.findOne(userExercise.getId());

        restUserExerciseMockMvc.perform(put("/api/user-exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExercise)))
            .andExpect(status().isOk());

        // Validate the UserExercise in the database
        List<UserExercise> userExerciseList = userExerciseRepository.findAll();
        assertThat(userExerciseList).hasSize(databaseSizeBeforeUpdate);
        UserExercise testUserExercise = userExerciseList.get(userExerciseList.size() - 1);

        // Validate the UserExercise in ElasticSearch
        UserExercise userExerciseEs = userExerciseSearchRepository.findOne(testUserExercise.getId());
        assertThat(userExerciseEs).isEqualToComparingFieldByField(testUserExercise);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExercise() throws Exception {
        int databaseSizeBeforeUpdate = userExerciseRepository.findAll().size();

        // Create the UserExercise

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserExerciseMockMvc.perform(put("/api/user-exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExercise)))
            .andExpect(status().isCreated());

        // Validate the UserExercise in the database
        List<UserExercise> userExerciseList = userExerciseRepository.findAll();
        assertThat(userExerciseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserExercise() throws Exception {
        // Initialize the database
        userExerciseService.save(userExercise);

        int databaseSizeBeforeDelete = userExerciseRepository.findAll().size();

        // Get the userExercise
        restUserExerciseMockMvc.perform(delete("/api/user-exercises/{id}", userExercise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean userExerciseExistsInEs = userExerciseSearchRepository.exists(userExercise.getId());
        assertThat(userExerciseExistsInEs).isFalse();

        // Validate the database is empty
        List<UserExercise> userExerciseList = userExerciseRepository.findAll();
        assertThat(userExerciseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserExercise() throws Exception {
        // Initialize the database
        userExerciseService.save(userExercise);

        // Search the userExercise
        restUserExerciseMockMvc.perform(get("/api/_search/user-exercises?query=id:" + userExercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExercise.getId().intValue())));
    }
}
