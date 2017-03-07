package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.Unit;
import com.thefitnation.repository.UnitRepository;
import com.thefitnation.service.UnitService;
import com.thefitnation.service.dto.UnitDTO;
import com.thefitnation.service.mapper.UnitMapper;
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

import com.thefitnation.domain.enumeration.Units;
/**
 * Test class for the UnitResource REST controller.
 *
 * @see UnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class UnitResourceIntTest {

    private static final Units DEFAULT_NAME = Units.Repetitions;
    private static final Units UPDATED_NAME = Units.Time;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnitMockMvc;

    private Unit unit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UnitResource unitResource = new UnitResource(unitService);
        this.restUnitMockMvc = MockMvcBuilders.standaloneSetup(unitResource)
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
    public static Unit createEntity(EntityManager em) {
        Unit unit = new Unit()
                .name(DEFAULT_NAME);
        return unit;
    }

    @Before
    public void initTest() {
        unit = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit
        UnitDTO unitDTO = unitMapper.unitToUnitDTO(unit);

        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit with an existing ID
        Unit existingUnit = new Unit();
        existingUnit.setId(1L);
        UnitDTO existingUnitDTO = unitMapper.unitToUnitDTO(existingUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setName(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.unitToUnitDTO(unit);

        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList
        restUnitMockMvc.perform(get("/api/units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        Unit updatedUnit = unitRepository.findOne(unit.getId());
        updatedUnit
                .name(UPDATED_NAME);
        UnitDTO unitDTO = unitMapper.unitToUnitDTO(updatedUnit);

        restUnitMockMvc.perform(put("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Create the Unit
        UnitDTO unitDTO = unitMapper.unitToUnitDTO(unit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUnitMockMvc.perform(put("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);
        int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Get the unit
        restUnitMockMvc.perform(delete("/api/units/{id}", unit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unit.class);
    }
}
