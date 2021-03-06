package com.thefitnation.web.rest;

import com.thefitnation.*;
import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.WorkoutInstanceGenerator;
import com.thefitnation.web.rest.errors.*;
import java.time.*;
import java.util.*;
import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.swing.text.html.Option;

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
import scopt.Opt;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkoutInstanceResource REST controller.
 *
 * @see WorkoutInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class WorkoutInstanceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_REST_BETWEEN_INSTANCES = 1F;
    private static final Float UPDATED_REST_BETWEEN_INSTANCES = 2F;

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private WorkoutInstanceRepository workoutInstanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutInstanceMapper workoutInstanceMapper;

    @Autowired
    private WorkoutInstanceService workoutInstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkoutInstanceMockMvc;

    private WorkoutInstance workoutInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkoutInstanceResource workoutInstanceResource = new WorkoutInstanceResource(workoutInstanceService, userRepository);
        this.restWorkoutInstanceMockMvc = MockMvcBuilders.standaloneSetup(workoutInstanceResource)
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
    public static WorkoutInstance createEntity(EntityManager em) {
        WorkoutInstance workoutInstance = new WorkoutInstance()
                .name(DEFAULT_NAME)
                .createdOn(DEFAULT_CREATED_ON)
                .lastUpdated(DEFAULT_LAST_UPDATED)
                .restBetweenInstances(DEFAULT_REST_BETWEEN_INSTANCES)
                .orderNumber(DEFAULT_ORDER_NUMBER)
                .notes(DEFAULT_NOTES);
        // Add required entity
        WorkoutTemplate workoutTemplate = WorkoutTemplateResourceIntTest.createEntity(em);
        em.persist(workoutTemplate);
        em.flush();
        workoutInstance.setWorkoutTemplate(workoutTemplate);
        return workoutInstance;
    }

    @Before
    public void initTest() {
        workoutInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkoutInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(em, user.get());

        int databaseSizeBeforeCreate = workoutInstanceRepository.findAll().size();
        LocalDate timeNow = LocalDate.now();
        workoutInstance.setCreatedOn(timeNow);
        workoutInstance.setLastUpdated(timeNow);
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkoutInstance in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkoutInstance testWorkoutInstance = workoutInstanceList.get(workoutInstanceList.size() - 1);
        assertThat(testWorkoutInstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkoutInstance.getCreatedOn()).isEqualTo(LocalDate.now());
        assertThat(testWorkoutInstance.getLastUpdated()).isEqualTo(LocalDate.now());
        assertThat(testWorkoutInstance.getRestBetweenInstances()).isEqualTo(DEFAULT_REST_BETWEEN_INSTANCES);
        assertThat(testWorkoutInstance.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testWorkoutInstance.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createWorkoutInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutInstanceRepository.findAll().size();

        // Create the WorkoutInstance with an existing ID
        WorkoutInstance existingWorkoutInstance = new WorkoutInstance();
        existingWorkoutInstance.setId(1L);
        WorkoutInstanceDTO existingWorkoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(existingWorkoutInstance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        AuthUtil.logInUser("user", "user", userRepository);
        int databaseSizeBeforeTest = workoutInstanceRepository.findAll().size();
        // set the field null
        workoutInstance.setCreatedOn(null);

        // Create the WorkoutInstance, which fails.
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutInstanceRepository.findAll().size();
        // set the field null
        workoutInstance.setLastUpdated(null);

        // Create the WorkoutInstance, which fails.
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutInstanceRepository.findAll().size();
        // set the field null
        workoutInstance.setOrderNumber(null);

        // Create the WorkoutInstance, which fails.
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);

        restWorkoutInstanceMockMvc.perform(post("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstanceDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkoutInstances() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(em, user.get());
        em.persist(workoutInstance);
        em.flush();

        // Get all the workoutInstanceList
        restWorkoutInstanceMockMvc.perform(get("/api/workout-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].restBetweenInstances").value(hasItem(DEFAULT_REST_BETWEEN_INSTANCES.doubleValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getWorkoutInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(em, user.get());
        em.persist(workoutInstance);
        em.flush();

        // Get the workoutInstance
        restWorkoutInstanceMockMvc.perform(get("/api/workout-instances/{id}", workoutInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workoutInstance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.restBetweenInstances").value(DEFAULT_REST_BETWEEN_INSTANCES.doubleValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkoutInstance() throws Exception {
        // Get the workoutInstance
        restWorkoutInstanceMockMvc.perform(get("/api/workout-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkoutInstance() throws Exception {
        // Initialize the database
        workoutInstanceRepository.saveAndFlush(workoutInstance);
        int databaseSizeBeforeUpdate = workoutInstanceRepository.findAll().size();

        // Update the workoutInstance
        WorkoutInstance updatedWorkoutInstance = workoutInstanceRepository.findOne(workoutInstance.getId());
        updatedWorkoutInstance
                .name(UPDATED_NAME)
                .createdOn(UPDATED_CREATED_ON)
                .lastUpdated(UPDATED_LAST_UPDATED)
                .restBetweenInstances(UPDATED_REST_BETWEEN_INSTANCES)
                .orderNumber(UPDATED_ORDER_NUMBER)
                .notes(UPDATED_NOTES);
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(updatedWorkoutInstance);

        restWorkoutInstanceMockMvc.perform(put("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstanceDTO)))
            .andExpect(status().isOk());

        // Validate the WorkoutInstance in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeUpdate);
        WorkoutInstance testWorkoutInstance = workoutInstanceList.get(workoutInstanceList.size() - 1);
        assertThat(testWorkoutInstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkoutInstance.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkoutInstance.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testWorkoutInstance.getRestBetweenInstances()).isEqualTo(UPDATED_REST_BETWEEN_INSTANCES);
        assertThat(testWorkoutInstance.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testWorkoutInstance.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkoutInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(em, user.get());
        int databaseSizeBeforeUpdate = workoutInstanceRepository.findAll().size();

        // Create the WorkoutInstance
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkoutInstanceMockMvc.perform(put("/api/workout-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutInstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkoutInstance in the database
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkoutInstance() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutInstance workoutInstance = WorkoutInstanceGenerator.getInstance().getOne(em, user.get());
        em.persist(workoutInstance);
        em.flush();

        workoutInstanceRepository.saveAndFlush(workoutInstance);
        int databaseSizeBeforeDelete = workoutInstanceRepository.findAll().size();

        // Get the workoutInstance
        restWorkoutInstanceMockMvc.perform(delete("/api/workout-instances/{id}", workoutInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkoutInstance> workoutInstanceList = workoutInstanceRepository.findAll();
        assertThat(workoutInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkoutInstance.class);
    }
}
