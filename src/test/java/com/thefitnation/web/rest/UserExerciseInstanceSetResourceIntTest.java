package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.domain.UserExerciseInstance;
import com.thefitnation.repository.UserExerciseInstanceSetRepository;
import com.thefitnation.service.UserExerciseInstanceSetService;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.UserExerciseInstanceSetMapper;
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
 * Test class for the UserExerciseInstanceSetResource REST controller.
 *
 * @see UserExerciseInstanceSetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UserExerciseInstanceSetResourceIntTest {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    private static final Float DEFAULT_REP_QUANTITY = 1F;
    private static final Float UPDATED_REP_QUANTITY = 2F;

    private static final Float DEFAULT_EFFORT_QUANTITY = 1F;
    private static final Float UPDATED_EFFORT_QUANTITY = 2F;

    private static final Float DEFAULT_REST_TIME = 1F;
    private static final Float UPDATED_REST_TIME = 2F;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    @Autowired
    private UserExerciseInstanceSetMapper userExerciseInstanceSetMapper;

    @Autowired
    private UserExerciseInstanceSetService userExerciseInstanceSetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserExerciseInstanceSetMockMvc;

    private UserExerciseInstanceSet userExerciseInstanceSet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserExerciseInstanceSetResource userExerciseInstanceSetResource = new UserExerciseInstanceSetResource(userExerciseInstanceSetService);
        this.restUserExerciseInstanceSetMockMvc = MockMvcBuilders.standaloneSetup(userExerciseInstanceSetResource)
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
    public static UserExerciseInstanceSet createEntity(EntityManager em) {
        UserExerciseInstanceSet userExerciseInstanceSet = new UserExerciseInstanceSet()
                .orderNumber(DEFAULT_ORDER_NUMBER)
                .repQuantity(DEFAULT_REP_QUANTITY)
                .effortQuantity(DEFAULT_EFFORT_QUANTITY)
                .restTime(DEFAULT_REST_TIME)
                .notes(DEFAULT_NOTES);
        // Add required entity
        UserExerciseInstance userExerciseInstance = UserExerciseInstanceResourceIntTest.createEntity(em);
        em.persist(userExerciseInstance);
        em.flush();
        userExerciseInstanceSet.setUserExerciseInstance(userExerciseInstance);
        return userExerciseInstanceSet;
    }

    @Before
    public void initTest() {
        userExerciseInstanceSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExerciseInstanceSet() throws Exception {
        int databaseSizeBeforeCreate = userExerciseInstanceSetRepository.findAll().size();

        // Create the UserExerciseInstanceSet
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);

        restUserExerciseInstanceSetMockMvc.perform(post("/api/user-exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceSetDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExerciseInstanceSet in the database
        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeCreate + 1);
        UserExerciseInstanceSet testUserExerciseInstanceSet = userExerciseInstanceSetList.get(userExerciseInstanceSetList.size() - 1);
        assertThat(testUserExerciseInstanceSet.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testUserExerciseInstanceSet.getRepQuantity()).isEqualTo(DEFAULT_REP_QUANTITY);
        assertThat(testUserExerciseInstanceSet.getEffortQuantity()).isEqualTo(DEFAULT_EFFORT_QUANTITY);
        assertThat(testUserExerciseInstanceSet.getRestTime()).isEqualTo(DEFAULT_REST_TIME);
        assertThat(testUserExerciseInstanceSet.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createUserExerciseInstanceSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExerciseInstanceSetRepository.findAll().size();

        // Create the UserExerciseInstanceSet with an existing ID
        UserExerciseInstanceSet existingUserExerciseInstanceSet = new UserExerciseInstanceSet();
        existingUserExerciseInstanceSet.setId(1L);
        UserExerciseInstanceSetDTO existingUserExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(existingUserExerciseInstanceSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExerciseInstanceSetMockMvc.perform(post("/api/user-exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserExerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExerciseInstanceSetRepository.findAll().size();
        // set the field null
        userExerciseInstanceSet.setOrderNumber(null);

        // Create the UserExerciseInstanceSet, which fails.
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);

        restUserExerciseInstanceSetMockMvc.perform(post("/api/user-exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExerciseInstanceSetRepository.findAll().size();
        // set the field null
        userExerciseInstanceSet.setRepQuantity(null);

        // Create the UserExerciseInstanceSet, which fails.
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);

        restUserExerciseInstanceSetMockMvc.perform(post("/api/user-exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEffortQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExerciseInstanceSetRepository.findAll().size();
        // set the field null
        userExerciseInstanceSet.setEffortQuantity(null);

        // Create the UserExerciseInstanceSet, which fails.
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);

        restUserExerciseInstanceSetMockMvc.perform(post("/api/user-exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceSetDTO)))
            .andExpect(status().isBadRequest());

        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserExerciseInstanceSets() throws Exception {
        // Initialize the database
        userExerciseInstanceSetRepository.saveAndFlush(userExerciseInstanceSet);

        // Get all the userExerciseInstanceSetList
        restUserExerciseInstanceSetMockMvc.perform(get("/api/user-exercise-instance-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExerciseInstanceSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].repQuantity").value(hasItem(DEFAULT_REP_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].effortQuantity").value(hasItem(DEFAULT_EFFORT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].restTime").value(hasItem(DEFAULT_REST_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getUserExerciseInstanceSet() throws Exception {
        // Initialize the database
        userExerciseInstanceSetRepository.saveAndFlush(userExerciseInstanceSet);

        // Get the userExerciseInstanceSet
        restUserExerciseInstanceSetMockMvc.perform(get("/api/user-exercise-instance-sets/{id}", userExerciseInstanceSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExerciseInstanceSet.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.repQuantity").value(DEFAULT_REP_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.effortQuantity").value(DEFAULT_EFFORT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.restTime").value(DEFAULT_REST_TIME.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserExerciseInstanceSet() throws Exception {
        // Get the userExerciseInstanceSet
        restUserExerciseInstanceSetMockMvc.perform(get("/api/user-exercise-instance-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExerciseInstanceSet() throws Exception {
        // Initialize the database
        userExerciseInstanceSetRepository.saveAndFlush(userExerciseInstanceSet);
        int databaseSizeBeforeUpdate = userExerciseInstanceSetRepository.findAll().size();

        // Update the userExerciseInstanceSet
        UserExerciseInstanceSet updatedUserExerciseInstanceSet = userExerciseInstanceSetRepository.findOne(userExerciseInstanceSet.getId());
        updatedUserExerciseInstanceSet
                .orderNumber(UPDATED_ORDER_NUMBER)
                .repQuantity(UPDATED_REP_QUANTITY)
                .effortQuantity(UPDATED_EFFORT_QUANTITY)
                .restTime(UPDATED_REST_TIME)
                .notes(UPDATED_NOTES);
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(updatedUserExerciseInstanceSet);

        restUserExerciseInstanceSetMockMvc.perform(put("/api/user-exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceSetDTO)))
            .andExpect(status().isOk());

        // Validate the UserExerciseInstanceSet in the database
        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeUpdate);
        UserExerciseInstanceSet testUserExerciseInstanceSet = userExerciseInstanceSetList.get(userExerciseInstanceSetList.size() - 1);
        assertThat(testUserExerciseInstanceSet.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testUserExerciseInstanceSet.getRepQuantity()).isEqualTo(UPDATED_REP_QUANTITY);
        assertThat(testUserExerciseInstanceSet.getEffortQuantity()).isEqualTo(UPDATED_EFFORT_QUANTITY);
        assertThat(testUserExerciseInstanceSet.getRestTime()).isEqualTo(UPDATED_REST_TIME);
        assertThat(testUserExerciseInstanceSet.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExerciseInstanceSet() throws Exception {
        int databaseSizeBeforeUpdate = userExerciseInstanceSetRepository.findAll().size();

        // Create the UserExerciseInstanceSet
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserExerciseInstanceSetMockMvc.perform(put("/api/user-exercise-instance-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExerciseInstanceSetDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExerciseInstanceSet in the database
        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserExerciseInstanceSet() throws Exception {
        // Initialize the database
        userExerciseInstanceSetRepository.saveAndFlush(userExerciseInstanceSet);
        int databaseSizeBeforeDelete = userExerciseInstanceSetRepository.findAll().size();

        // Get the userExerciseInstanceSet
        restUserExerciseInstanceSetMockMvc.perform(delete("/api/user-exercise-instance-sets/{id}", userExerciseInstanceSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserExerciseInstanceSet> userExerciseInstanceSetList = userExerciseInstanceSetRepository.findAll();
        assertThat(userExerciseInstanceSetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExerciseInstanceSet.class);
    }
}
