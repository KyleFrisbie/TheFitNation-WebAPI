package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.SkillLevel;
import com.thefitnation.repository.SkillLevelRepository;
import com.thefitnation.service.SkillLevelService;
import com.thefitnation.service.dto.SkillLevelDTO;
import com.thefitnation.service.mapper.SkillLevelMapper;
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
 * Test class for the SkillLevelResource REST controller.
 *
 * @see SkillLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class SkillLevelResourceIntTest {

    private static final com.thefitnation.domain.enumeration.SkillLevel DEFAULT_LEVEL = com.thefitnation.domain.enumeration.SkillLevel.Beginner;
    private static final com.thefitnation.domain.enumeration.SkillLevel UPDATED_LEVEL = com.thefitnation.domain.enumeration.SkillLevel.Intermediate;

    @Autowired
    private SkillLevelRepository skillLevelRepository;

    @Autowired
    private SkillLevelMapper skillLevelMapper;

    @Autowired
    private SkillLevelService skillLevelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillLevelMockMvc;

    private SkillLevel skillLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SkillLevelResource skillLevelResource = new SkillLevelResource(skillLevelService);
        this.restSkillLevelMockMvc = MockMvcBuilders.standaloneSetup(skillLevelResource)
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
    public static SkillLevel createEntity(EntityManager em) {
        SkillLevel skillLevel = new SkillLevel()
                .level(DEFAULT_LEVEL);
        return skillLevel;
    }

    @Before
    public void initTest() {
        skillLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillLevel() throws Exception {
        int databaseSizeBeforeCreate = skillLevelRepository.findAll().size();

        // Create the SkillLevel
        SkillLevelDTO skillLevelDTO = skillLevelMapper.skillLevelToSkillLevelDTO(skillLevel);

        restSkillLevelMockMvc.perform(post("/api/skill-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the SkillLevel in the database
        List<SkillLevel> skillLevelList = skillLevelRepository.findAll();
        assertThat(skillLevelList).hasSize(databaseSizeBeforeCreate + 1);
        SkillLevel testSkillLevel = skillLevelList.get(skillLevelList.size() - 1);
        assertThat(testSkillLevel.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createSkillLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillLevelRepository.findAll().size();

        // Create the SkillLevel with an existing ID
        SkillLevel existingSkillLevel = new SkillLevel();
        existingSkillLevel.setId(1L);
        SkillLevelDTO existingSkillLevelDTO = skillLevelMapper.skillLevelToSkillLevelDTO(existingSkillLevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillLevelMockMvc.perform(post("/api/skill-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSkillLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SkillLevel> skillLevelList = skillLevelRepository.findAll();
        assertThat(skillLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillLevelRepository.findAll().size();
        // set the field null
        skillLevel.setLevel(null);

        // Create the SkillLevel, which fails.
        SkillLevelDTO skillLevelDTO = skillLevelMapper.skillLevelToSkillLevelDTO(skillLevel);

        restSkillLevelMockMvc.perform(post("/api/skill-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillLevelDTO)))
            .andExpect(status().isBadRequest());

        List<SkillLevel> skillLevelList = skillLevelRepository.findAll();
        assertThat(skillLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkillLevels() throws Exception {
        // Initialize the database
        skillLevelRepository.saveAndFlush(skillLevel);

        // Get all the skillLevelList
        restSkillLevelMockMvc.perform(get("/api/skill-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void getSkillLevel() throws Exception {
        // Initialize the database
        skillLevelRepository.saveAndFlush(skillLevel);

        // Get the skillLevel
        restSkillLevelMockMvc.perform(get("/api/skill-levels/{id}", skillLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillLevel.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSkillLevel() throws Exception {
        // Get the skillLevel
        restSkillLevelMockMvc.perform(get("/api/skill-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillLevel() throws Exception {
        // Initialize the database
        skillLevelRepository.saveAndFlush(skillLevel);
        int databaseSizeBeforeUpdate = skillLevelRepository.findAll().size();

        // Update the skillLevel
        SkillLevel updatedSkillLevel = skillLevelRepository.findOne(skillLevel.getId());
        updatedSkillLevel
                .level(UPDATED_LEVEL);
        SkillLevelDTO skillLevelDTO = skillLevelMapper.skillLevelToSkillLevelDTO(updatedSkillLevel);

        restSkillLevelMockMvc.perform(put("/api/skill-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillLevelDTO)))
            .andExpect(status().isOk());

        // Validate the SkillLevel in the database
        List<SkillLevel> skillLevelList = skillLevelRepository.findAll();
        assertThat(skillLevelList).hasSize(databaseSizeBeforeUpdate);
        SkillLevel testSkillLevel = skillLevelList.get(skillLevelList.size() - 1);
        assertThat(testSkillLevel.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillLevel() throws Exception {
        int databaseSizeBeforeUpdate = skillLevelRepository.findAll().size();

        // Create the SkillLevel
        SkillLevelDTO skillLevelDTO = skillLevelMapper.skillLevelToSkillLevelDTO(skillLevel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkillLevelMockMvc.perform(put("/api/skill-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the SkillLevel in the database
        List<SkillLevel> skillLevelList = skillLevelRepository.findAll();
        assertThat(skillLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSkillLevel() throws Exception {
        // Initialize the database
        skillLevelRepository.saveAndFlush(skillLevel);
        int databaseSizeBeforeDelete = skillLevelRepository.findAll().size();

        // Get the skillLevel
        restSkillLevelMockMvc.perform(delete("/api/skill-levels/{id}", skillLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SkillLevel> skillLevelList = skillLevelRepository.findAll();
        assertThat(skillLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillLevel.class);
    }
}
