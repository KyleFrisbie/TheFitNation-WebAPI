package com.thefitnation.web.rest;

import com.thefitnation.*;
import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.WorkoutTemplateGenerator;
import com.thefitnation.web.rest.errors.*;
import java.time.*;
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
 * Test class for the WorkoutTemplateResource REST controller.
 *
 * @see WorkoutTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class WorkoutTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final Boolean UPDATED_IS_PRIVATE = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutTemplateRepository workoutTemplateRepository;

    @Autowired
    private WorkoutTemplateMapper workoutTemplateMapper;

    @Autowired
    private WorkoutTemplateService workoutTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkoutTemplateMockMvc;

    private WorkoutTemplate workoutTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkoutTemplateResource workoutTemplateResource = new WorkoutTemplateResource(workoutTemplateService, userRepository);
        this.restWorkoutTemplateMockMvc = MockMvcBuilders.standaloneSetup(workoutTemplateResource)
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
    public static WorkoutTemplate createEntity(EntityManager em) {
        WorkoutTemplate workoutTemplate = new WorkoutTemplate()
                .name(DEFAULT_NAME)
                .createdOn(DEFAULT_CREATED_ON)
                .lastUpdated(DEFAULT_LAST_UPDATED)
                .isPrivate(DEFAULT_IS_PRIVATE)
                .notes(DEFAULT_NOTES);
        // Add required entity
        UserDemographic userDemographic = UserDemographicResourceIntTest.createEntity(em);
        em.persist(userDemographic);
        em.flush();
        workoutTemplate.setUserDemographic(userDemographic);
        // Add required entity
        SkillLevel skillLevel = SkillLevelResourceIntTest.createEntity(em);
        em.persist(skillLevel);
        em.flush();
        workoutTemplate.setSkillLevel(skillLevel);
        return workoutTemplate;
    }

    @Before
    public void initTest() {
        workoutTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(em, user.get());

        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();
        LocalDate timeNow = LocalDate.now();
        workoutTemplate.setCreatedOn(timeNow);
        workoutTemplate.setLastUpdated(timeNow);

        // Create the WorkoutTemplate
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkoutTemplate in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        WorkoutTemplate testWorkoutTemplate = workoutTemplateList.get(workoutTemplateList.size() - 1);
        assertThat(testWorkoutTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkoutTemplate.getCreatedOn()).isEqualTo(LocalDate.now());
        assertThat(testWorkoutTemplate.getLastUpdated()).isEqualTo(LocalDate.now());
        assertThat(testWorkoutTemplate.isIsPrivate()).isEqualTo(DEFAULT_IS_PRIVATE);
        assertThat(testWorkoutTemplate.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createWorkoutTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutTemplateRepository.findAll().size();

        // Create the WorkoutTemplate with an existing ID
        WorkoutTemplate existingWorkoutTemplate = new WorkoutTemplate();
        existingWorkoutTemplate.setId(1L);
        WorkoutTemplateDTO existingWorkoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(existingWorkoutTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setName(null);

        // Create the WorkoutTemplate, which fails.
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setCreatedOn(null);

        // Create the WorkoutTemplate, which fails.
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setLastUpdated(null);

        // Create the WorkoutTemplate, which fails.
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPrivateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutTemplateRepository.findAll().size();
        // set the field null
        workoutTemplate.setIsPrivate(null);

        // Create the WorkoutTemplate, which fails.
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);

        restWorkoutTemplateMockMvc.perform(post("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkoutTemplates() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(em, user.get());
        em.persist(workoutTemplate);
        em.flush();

        // Get all the workoutTemplateList
        restWorkoutTemplateMockMvc.perform(get("/api/workout-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].isPrivate").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(em, user.get());
        em.persist(workoutTemplate);
        em.flush();
        workoutTemplateRepository.saveAndFlush(workoutTemplate);

        // Get the workoutTemplate
        restWorkoutTemplateMockMvc.perform(get("/api/workout-templates/{id}", workoutTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workoutTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.isPrivate").value(DEFAULT_IS_PRIVATE.booleanValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkoutTemplate() throws Exception {
        // Get the workoutTemplate
        restWorkoutTemplateMockMvc.perform(get("/api/workout-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkoutTemplate() throws Exception {
        // Initialize the database
        workoutTemplateRepository.saveAndFlush(workoutTemplate);
        int databaseSizeBeforeUpdate = workoutTemplateRepository.findAll().size();

        // Update the workoutTemplate
        WorkoutTemplate updatedWorkoutTemplate = workoutTemplateRepository.findOne(workoutTemplate.getId());
        updatedWorkoutTemplate
                .name(UPDATED_NAME)
                .createdOn(UPDATED_CREATED_ON)
                .lastUpdated(UPDATED_LAST_UPDATED)
                .isPrivate(UPDATED_IS_PRIVATE)
                .notes(UPDATED_NOTES);
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(updatedWorkoutTemplate);

        restWorkoutTemplateMockMvc.perform(put("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the WorkoutTemplate in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeUpdate);
        WorkoutTemplate testWorkoutTemplate = workoutTemplateList.get(workoutTemplateList.size() - 1);
        assertThat(testWorkoutTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkoutTemplate.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkoutTemplate.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testWorkoutTemplate.isIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
        assertThat(testWorkoutTemplate.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(em, user.get());

        int databaseSizeBeforeUpdate = workoutTemplateRepository.findAll().size();

        // Create the WorkoutTemplate
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkoutTemplateMockMvc.perform(put("/api/workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutTemplateDTO)))
            .andExpect(status().is2xxSuccessful());

        // Validate the WorkoutTemplate in the database
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        WorkoutTemplate workoutTemplate = WorkoutTemplateGenerator.getInstance().getOne(em, user.get());
        em.persist(workoutTemplate);
        em.flush();

        int databaseSizeBeforeDelete = workoutTemplateRepository.findAll().size();

        // Get the workoutTemplate
        restWorkoutTemplateMockMvc.perform(delete("/api/workout-templates/{id}", workoutTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkoutTemplate> workoutTemplateList = workoutTemplateRepository.findAll();
        assertThat(workoutTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkoutTemplate.class);
    }
}
