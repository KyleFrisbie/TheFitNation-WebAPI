package com.thefitnation.web.rest;

import com.thefitnation.*;
import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.ExerciseInstanceGenerator;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.web.rest.errors.*;
import java.util.*;
import javax.persistence.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.web.*;
import org.springframework.http.*;
import org.springframework.http.converter.json.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.transaction.annotation.*;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
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
    private UserRepository userRepository;

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
        ExerciseInstanceResource exerciseInstanceResource = new ExerciseInstanceResource(exerciseInstanceService, userRepository);
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
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();

        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
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
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        exerciseInstanceRepository.saveAndFlush(exerciseInstance);
        restExerciseInstanceMockMvc.perform(get("/api/exercise-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getExerciseInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        exerciseInstanceRepository.saveAndFlush(exerciseInstance);
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
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
        // Get the exerciseInstance
        restExerciseInstanceMockMvc.perform(get("/api/exercise-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
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
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
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
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        ExerciseInstance exerciseInstance = ExerciseInstanceGenerator.getInstance().getOne(em, userDemographic);
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
