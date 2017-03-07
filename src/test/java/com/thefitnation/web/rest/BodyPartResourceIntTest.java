package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.BodyPart;
import com.thefitnation.repository.BodyPartRepository;
import com.thefitnation.service.BodyPartService;
import com.thefitnation.service.dto.BodyPartDTO;
import com.thefitnation.service.mapper.BodyPartMapper;
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
 * Test class for the BodyPartResource REST controller.
 *
 * @see BodyPartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class BodyPartResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BodyPartRepository bodyPartRepository;

    @Autowired
    private BodyPartMapper bodyPartMapper;

    @Autowired
    private BodyPartService bodyPartService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBodyPartMockMvc;

    private BodyPart bodyPart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BodyPartResource bodyPartResource = new BodyPartResource(bodyPartService);
        this.restBodyPartMockMvc = MockMvcBuilders.standaloneSetup(bodyPartResource)
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
    public static BodyPart createEntity(EntityManager em) {
        BodyPart bodyPart = new BodyPart()
                .name(DEFAULT_NAME);
        return bodyPart;
    }

    @Before
    public void initTest() {
        bodyPart = createEntity(em);
    }

    @Test
    @Transactional
    public void createBodyPart() throws Exception {
        int databaseSizeBeforeCreate = bodyPartRepository.findAll().size();

        // Create the BodyPart
        BodyPartDTO bodyPartDTO = bodyPartMapper.bodyPartToBodyPartDTO(bodyPart);

        restBodyPartMockMvc.perform(post("/api/body-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPartDTO)))
            .andExpect(status().isCreated());

        // Validate the BodyPart in the database
        List<BodyPart> bodyPartList = bodyPartRepository.findAll();
        assertThat(bodyPartList).hasSize(databaseSizeBeforeCreate + 1);
        BodyPart testBodyPart = bodyPartList.get(bodyPartList.size() - 1);
        assertThat(testBodyPart.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBodyPartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bodyPartRepository.findAll().size();

        // Create the BodyPart with an existing ID
        BodyPart existingBodyPart = new BodyPart();
        existingBodyPart.setId(1L);
        BodyPartDTO existingBodyPartDTO = bodyPartMapper.bodyPartToBodyPartDTO(existingBodyPart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyPartMockMvc.perform(post("/api/body-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBodyPartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BodyPart> bodyPartList = bodyPartRepository.findAll();
        assertThat(bodyPartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bodyPartRepository.findAll().size();
        // set the field null
        bodyPart.setName(null);

        // Create the BodyPart, which fails.
        BodyPartDTO bodyPartDTO = bodyPartMapper.bodyPartToBodyPartDTO(bodyPart);

        restBodyPartMockMvc.perform(post("/api/body-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPartDTO)))
            .andExpect(status().isBadRequest());

        List<BodyPart> bodyPartList = bodyPartRepository.findAll();
        assertThat(bodyPartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBodyParts() throws Exception {
        // Initialize the database
        bodyPartRepository.saveAndFlush(bodyPart);

        // Get all the bodyPartList
        restBodyPartMockMvc.perform(get("/api/body-parts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyPart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBodyPart() throws Exception {
        // Initialize the database
        bodyPartRepository.saveAndFlush(bodyPart);

        // Get the bodyPart
        restBodyPartMockMvc.perform(get("/api/body-parts/{id}", bodyPart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bodyPart.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBodyPart() throws Exception {
        // Get the bodyPart
        restBodyPartMockMvc.perform(get("/api/body-parts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBodyPart() throws Exception {
        // Initialize the database
        bodyPartRepository.saveAndFlush(bodyPart);
        int databaseSizeBeforeUpdate = bodyPartRepository.findAll().size();

        // Update the bodyPart
        BodyPart updatedBodyPart = bodyPartRepository.findOne(bodyPart.getId());
        updatedBodyPart
                .name(UPDATED_NAME);
        BodyPartDTO bodyPartDTO = bodyPartMapper.bodyPartToBodyPartDTO(updatedBodyPart);

        restBodyPartMockMvc.perform(put("/api/body-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPartDTO)))
            .andExpect(status().isOk());

        // Validate the BodyPart in the database
        List<BodyPart> bodyPartList = bodyPartRepository.findAll();
        assertThat(bodyPartList).hasSize(databaseSizeBeforeUpdate);
        BodyPart testBodyPart = bodyPartList.get(bodyPartList.size() - 1);
        assertThat(testBodyPart.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBodyPart() throws Exception {
        int databaseSizeBeforeUpdate = bodyPartRepository.findAll().size();

        // Create the BodyPart
        BodyPartDTO bodyPartDTO = bodyPartMapper.bodyPartToBodyPartDTO(bodyPart);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBodyPartMockMvc.perform(put("/api/body-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPartDTO)))
            .andExpect(status().isCreated());

        // Validate the BodyPart in the database
        List<BodyPart> bodyPartList = bodyPartRepository.findAll();
        assertThat(bodyPartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBodyPart() throws Exception {
        // Initialize the database
        bodyPartRepository.saveAndFlush(bodyPart);
        int databaseSizeBeforeDelete = bodyPartRepository.findAll().size();

        // Get the bodyPart
        restBodyPartMockMvc.perform(delete("/api/body-parts/{id}", bodyPart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BodyPart> bodyPartList = bodyPartRepository.findAll();
        assertThat(bodyPartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyPart.class);
    }
}
