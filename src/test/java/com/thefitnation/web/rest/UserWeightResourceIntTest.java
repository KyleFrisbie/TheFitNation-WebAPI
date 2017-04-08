package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.User;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWeightRepository;
import com.thefitnation.security.SecurityUtils;
import com.thefitnation.service.UserDemographicService;
import com.thefitnation.service.UserService;
import com.thefitnation.service.UserWeightService;
import com.thefitnation.service.dto.UserWeightDTO;
import com.thefitnation.service.mapper.UserWeightMapper;
import com.thefitnation.testTools.CreateEntities;
import com.thefitnation.web.rest.errors.ExceptionTranslator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    private static final LocalDate DEFAULT_WEIGHT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WEIGHT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWeightRepository userWeightRepository;

    @Autowired
    private UserWeightMapper userWeightMapper;

    @Autowired
    private UserDemographicService userDemographicService;

    @Autowired
    private UserWeightService userWeightService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserWeightMockMvc;

    private UserWeight userWeight;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserWeightResource userWeightResource = new UserWeightResource(userWeightService, userService, userDemographicService, userRepository);
        this.restUserWeightMockMvc = MockMvcBuilders.standaloneSetup(userWeightResource)
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
    public static UserWeight createEntity(EntityManager em) {
        UserWeight userWeight = new UserWeight()
                .weightDate(DEFAULT_WEIGHT_DATE)
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
        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeightDTO)))
            .andExpect(status().isCreated());

        // Validate the UserWeight in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeCreate + 1);
        UserWeight testUserWeight = userWeightList.get(userWeightList.size() - 1);
        assertThat(testUserWeight.getWeightDate()).isEqualTo(DEFAULT_WEIGHT_DATE);
        assertThat(testUserWeight.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createUserWeightWithExistingId() throws Exception {
        // Create the UserWeight with an existing ID
        em.persist(userWeight);
        em.flush();

        int databaseSizeBeforeCreate = userWeightRepository.findAll().size();

        UserWeightDTO existingUserWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserWeightDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWeightDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWeightRepository.findAll().size();
        // set the field null
        userWeight.setWeightDate(null);

        // Create the UserWeight, which fails.
        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeightDTO)))
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
        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        restUserWeightMockMvc.perform(post("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeightDTO)))
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
            .andExpect(jsonPath("$.[*].weightDate").value(hasItem(DEFAULT_WEIGHT_DATE.toString())))
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
            .andExpect(jsonPath("$.weightDate").value(DEFAULT_WEIGHT_DATE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()));
    }

    @Test
    @Transactional
    public void getUserWeightByLoggedInUser() throws Exception {
        User user = userRepository.findOneByLogin("admin").get();

        // Initialize the database
        UserWeight testUserWeight = CreateEntities.generateUserWeightForUser(em, user);
        em.persist(testUserWeight);
        em.flush();

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);

        // Get the userWeight
        restUserWeightMockMvc.perform(get("/api/user-weights/byLoggedInUser/{id}", testUserWeight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testUserWeight.getId().intValue()))
            .andExpect(jsonPath("$.weightDate").value(testUserWeight.getWeightDate().toString()))
            .andExpect(jsonPath("$.weight").value(testUserWeight.getWeight().doubleValue()));
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
        userWeightRepository.saveAndFlush(userWeight);
        int databaseSizeBeforeUpdate = userWeightRepository.findAll().size();

        // Update the userWeight
        UserWeight updatedUserWeight = userWeightRepository.findOne(userWeight.getId());
        updatedUserWeight
                .weightDate(UPDATED_WEIGHT_DATE)
                .weight(UPDATED_WEIGHT);
        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(updatedUserWeight);

        restUserWeightMockMvc.perform(put("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeightDTO)))
            .andExpect(status().isOk());

        // Validate the UserWeight in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeUpdate);
        UserWeight testUserWeight = userWeightList.get(userWeightList.size() - 1);
        assertThat(testUserWeight.getWeightDate()).isEqualTo(UPDATED_WEIGHT_DATE);
        assertThat(testUserWeight.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWeight() throws Exception {
        int databaseSizeBeforeUpdate = userWeightRepository.findAll().size();

        // Create the UserWeight
        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserWeightMockMvc.perform(put("/api/user-weights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userWeightDTO)))
            .andExpect(status().isCreated());

        // Validate the UserWeight in the database
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserWeight() throws Exception {
        // Initialize the database
        userWeightRepository.saveAndFlush(userWeight);
        int databaseSizeBeforeDelete = userWeightRepository.findAll().size();

        // Get the userWeight
        restUserWeightMockMvc.perform(delete("/api/user-weights/{id}", userWeight.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserWeight> userWeightList = userWeightRepository.findAll();
        assertThat(userWeightList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWeight.class);
    }
}
