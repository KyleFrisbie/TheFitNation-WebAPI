package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.*;
import com.thefitnation.repository.UserExerciseInstanceRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.service.UserExerciseInstanceService;
import com.thefitnation.service.dto.UserExerciseInstanceDTO;
import com.thefitnation.service.mapper.UserExerciseInstanceMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.ExerciseInstanceGenerator;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserExerciseInstanceGenerator;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserExerciseInstanceResource REST controller.
 *
 * @see UserExerciseInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserExerciseInstanceResourceIntTest {

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserExerciseInstanceRepository userExerciseInstanceRepository;

    @Autowired
    private UserExerciseInstanceMapper userExerciseInstanceMapper;

    @Autowired
    private UserExerciseInstanceService userExerciseInstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserExerciseInstanceMockMvc;

    private UserExerciseInstance userExerciseInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserExerciseInstanceResource userExerciseInstanceResource = new UserExerciseInstanceResource(userExerciseInstanceService);
        this.restUserExerciseInstanceMockMvc = MockMvcBuilders.standaloneSetup(userExerciseInstanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExerciseInstance createEntity(EntityManager em) {
        UserExerciseInstance userExerciseInstance = new UserExerciseInstance()
            .notes(DEFAULT_NOTES);
        // Add required entity
        UserWorkoutInstance userWorkoutInstance = UserWorkoutInstanceResourceIntTest.createEntity(em);
        em.persist(userWorkoutInstance);
        em.flush();
        userExerciseInstance.setUserWorkoutInstance(userWorkoutInstance);
        return userExerciseInstance;
    }

    @Before
    public void initTest() {
        userExerciseInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExerciseInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        int databaseSizeBeforeCreate = userExerciseInstanceRepository.findAll().size();

        // Create the UserExerciseInstance
        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);

        restUserExerciseInstanceMockMvc.perform(post("/api/user-exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExerciseInstance in the database
        List<UserExerciseInstance> userExerciseInstanceList = userExerciseInstanceRepository.findAll();
        assertThat(userExerciseInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        UserExerciseInstance testUserExerciseInstance = userExerciseInstanceList.get(userExerciseInstanceList.size() - 1);
        assertThat(testUserExerciseInstance.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createUserExerciseInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExerciseInstanceRepository.findAll().size();

        // Create the UserExerciseInstance with an existing ID
        UserExerciseInstance existingUserExerciseInstance = new UserExerciseInstance();
        existingUserExerciseInstance.setId(1L);
        UserExerciseInstanceDTO existingUserExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(existingUserExerciseInstance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExerciseInstanceMockMvc.perform(post("/api/user-exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserExerciseInstanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserExerciseInstance> userExerciseInstanceList = userExerciseInstanceRepository.findAll();
        assertThat(userExerciseInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserExerciseInstances() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        // Initialize the database
        userExerciseInstanceRepository.saveAndFlush(userExerciseInstance);

        // Get all the userExerciseInstanceList
        restUserExerciseInstanceMockMvc.perform(get("/api/user-exercise-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExerciseInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getUserExerciseInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        // Initialize the database
        userExerciseInstanceRepository.saveAndFlush(userExerciseInstance);

        // Get the userExerciseInstance
        restUserExerciseInstanceMockMvc.perform(get("/api/user-exercise-instances/{id}", userExerciseInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExerciseInstance.getId().intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserExerciseInstance() throws Exception {
        // Get the userExerciseInstance
        restUserExerciseInstanceMockMvc.perform(get("/api/user-exercise-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExerciseInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        // Initialize the database
        userExerciseInstanceRepository.saveAndFlush(userExerciseInstance);
        int databaseSizeBeforeUpdate = userExerciseInstanceRepository.findAll().size();

        // Update the userExerciseInstance
        UserExerciseInstance updatedUserExerciseInstance = userExerciseInstanceRepository.findOne(userExerciseInstance.getId());
        updatedUserExerciseInstance
            .notes(UPDATED_NOTES);
        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(updatedUserExerciseInstance);

        restUserExerciseInstanceMockMvc.perform(put("/api/user-exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceDTO)))
            .andExpect(status().isOk());

        // Validate the UserExerciseInstance in the database
        List<UserExerciseInstance> userExerciseInstanceList = userExerciseInstanceRepository.findAll();
        assertThat(userExerciseInstanceList).hasSize(databaseSizeBeforeUpdate);
        UserExerciseInstance testUserExerciseInstance = userExerciseInstanceList.get(userExerciseInstanceList.size() - 1);
        assertThat(testUserExerciseInstance.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExerciseInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        int databaseSizeBeforeUpdate = userExerciseInstanceRepository.findAll().size();

        // Create the UserExerciseInstance
        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserExerciseInstanceMockMvc.perform(put("/api/user-exercise-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExerciseInstance in the database
        List<UserExerciseInstance> userExerciseInstanceList = userExerciseInstanceRepository.findAll();
        assertThat(userExerciseInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserExerciseInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        UserExerciseInstance userExerciseInstance = UserExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        // Initialize the database
        userExerciseInstanceRepository.saveAndFlush(userExerciseInstance);
        int databaseSizeBeforeDelete = userExerciseInstanceRepository.findAll().size();

        // Get the userExerciseInstance
        restUserExerciseInstanceMockMvc.perform(delete("/api/user-exercise-instances/{id}", userExerciseInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserExerciseInstance> userExerciseInstanceList = userExerciseInstanceRepository.findAll();
        assertThat(userExerciseInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExerciseInstance.class);
    }
}
