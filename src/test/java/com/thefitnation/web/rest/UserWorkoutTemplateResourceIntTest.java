package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.User;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.security.SecurityUtils;
import com.thefitnation.service.UserWorkoutTemplateService;
import com.thefitnation.service.dto.UserWorkoutTemplateDTO;
import com.thefitnation.service.mapper.UserWorkoutTemplateMapper;
import com.thefitnation.testTools.AuthUtil;
import com.thefitnation.testTools.UserDemographicGenerator;
import com.thefitnation.testTools.UserWorkoutTemplateGenerator;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserWorkoutTemplateResource REST controller.
 *
 * @see UserWorkoutTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserWorkoutTemplateResourceIntTest {

    private static final int NUMBER_OF_USER_WORKOUT_TEMPLATES = 10;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    @Autowired
    private UserWorkoutTemplateMapper userWorkoutTemplateMapper;

    @Autowired
    private UserWorkoutTemplateService userWorkoutTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserWorkoutTemplateMockMvc;

    private UserWorkoutTemplate userWorkoutTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserWorkoutTemplateResource userWorkoutTemplateResource = new UserWorkoutTemplateResource(userWorkoutTemplateService);
        this.restUserWorkoutTemplateMockMvc = MockMvcBuilders.standaloneSetup(userWorkoutTemplateResource)
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
    public static UserWorkoutTemplate createEntity(EntityManager em) {
        UserWorkoutTemplate userWorkoutTemplate = new UserWorkoutTemplate()
                .createdOn(DEFAULT_CREATED_ON)
                .lastUpdated(DEFAULT_LAST_UPDATED)
                .notes(DEFAULT_NOTES);
        // Add required entity
        UserDemographic userDemographic = UserDemographicResourceIntTest.createEntity(em);
        em.persist(userDemographic);
        em.flush();
        userWorkoutTemplate.setUserDemographic(userDemographic);
        return userWorkoutTemplate;
    }

    @Before
    public void initTest() {
        userWorkoutTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(em, userDemographic);

        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        // Create the UserWorkoutTemplate
        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);

        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutTemplate in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        UserWorkoutTemplate testUserWorkoutTemplate = userWorkoutTemplateList.get(userWorkoutTemplateList.size() - 1);
        assertThat(testUserWorkoutTemplate.getCreatedOn()).isEqualTo(LocalDate.now());
        assertThat(testUserWorkoutTemplate.getLastUpdated()).isEqualTo(LocalDate.now());
        assertThat(testUserWorkoutTemplate.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createUserWorkoutTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWorkoutTemplateRepository.findAll().size();

        // Create the UserWorkoutTemplate with an existing ID
        UserWorkoutTemplate existingUserWorkoutTemplate = new UserWorkoutTemplate();
        existingUserWorkoutTemplate.setId(1L);
        UserWorkoutTemplateDTO existingUserWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(existingUserWorkoutTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserWorkoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutTemplateRepository.findAll().size();
        // set the field null
        userWorkoutTemplate.setCreatedOn(null);

        // Create the UserWorkoutTemplate, which fails.
        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);

        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWorkoutTemplateRepository.findAll().size();
        // set the field null
        userWorkoutTemplate.setLastUpdated(null);

        // Create the UserWorkoutTemplate, which fails.
        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);

        restUserWorkoutTemplateMockMvc.perform(post("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserWorkoutTemplates() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        List<UserWorkoutTemplate> userWorkoutTemplates = UserWorkoutTemplateGenerator.getInstance().getMany(em, NUMBER_OF_USER_WORKOUT_TEMPLATES, userDemographic);
        UserWorkoutTemplateGenerator.getInstance().getMany(em, NUMBER_OF_USER_WORKOUT_TEMPLATES);

        // Get all the userWorkoutTemplateList
        restUserWorkoutTemplateMockMvc.perform(get("/api/user-workout-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWorkoutTemplates.get(0).getId().intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getUserWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(em, userDemographic);
        userWorkoutTemplateRepository.saveAndFlush(userWorkoutTemplate);

        // Get the userWorkoutTemplate
        restUserWorkoutTemplateMockMvc.perform(get("/api/user-workout-templates/{id}", userWorkoutTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userWorkoutTemplate.getId().intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserWorkoutTemplate() throws Exception {
        // Get the userWorkoutTemplate
        restUserWorkoutTemplateMockMvc.perform(get("/api/user-workout-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(em, userDemographic);
        // Initialize the database
        userWorkoutTemplateRepository.saveAndFlush(userWorkoutTemplate);
        int databaseSizeBeforeUpdate = userWorkoutTemplateRepository.findAll().size();

        // Update the userWorkoutTemplate
        UserWorkoutTemplate updatedUserWorkoutTemplate = userWorkoutTemplateRepository.findOne(userWorkoutTemplate.getId());
        updatedUserWorkoutTemplate
                .createdOn(UPDATED_CREATED_ON)
                .lastUpdated(UPDATED_LAST_UPDATED)
                .notes(UPDATED_NOTES);
        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(updatedUserWorkoutTemplate);

        restUserWorkoutTemplateMockMvc.perform(put("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the UserWorkoutTemplate in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeUpdate);
        UserWorkoutTemplate testUserWorkoutTemplate = userWorkoutTemplateList.get(userWorkoutTemplateList.size() - 1);
        assertThat(testUserWorkoutTemplate.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUserWorkoutTemplate.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testUserWorkoutTemplate.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(em, userDemographic);
        int databaseSizeBeforeUpdate = userWorkoutTemplateRepository.findAll().size();

        // Create the UserWorkoutTemplate
        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserWorkoutTemplateMockMvc.perform(put("/api/user-workout-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWorkoutTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the UserWorkoutTemplate in the database
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserWorkoutTemplate() throws Exception {
        Optional<User> user = AuthUtil.logInUser("user", "user", userRepository);
        UserDemographic userDemographic = UserDemographicGenerator.getOne(em, user.get());
        em.persist(userDemographic);
        em.flush();
        UserWorkoutTemplate userWorkoutTemplate = UserWorkoutTemplateGenerator.getInstance().getOne(em, userDemographic);
        em.persist(userWorkoutTemplate);
        em.flush();

        // Initialize the database
        userWorkoutTemplateRepository.saveAndFlush(userWorkoutTemplate);
        int databaseSizeBeforeDelete = userWorkoutTemplateRepository.findAll().size();

        // Get the userWorkoutTemplate
        restUserWorkoutTemplateMockMvc.perform(delete("/api/user-workout-templates/{id}", userWorkoutTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserWorkoutTemplate> userWorkoutTemplateList = userWorkoutTemplateRepository.findAll();
        assertThat(userWorkoutTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWorkoutTemplate.class);
    }
}
