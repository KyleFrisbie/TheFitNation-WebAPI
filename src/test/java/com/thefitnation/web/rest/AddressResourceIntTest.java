package com.thefitnation.web.rest;

import com.thefitnation.TheFitNationApp;

import com.thefitnation.domain.Address;
import com.thefitnation.repository.AddressRepository;
import com.thefitnation.service.AddressService;
import com.thefitnation.service.dto.AddressDTO;
import com.thefitnation.service.mapper.AddressMapper;
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
 * Test class for the AddressResource REST controller.
 *
 * @see AddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TheFitNationApp.class)
public class AddressResourceIntTest {

    private static final String DEFAULT_STREET_ADDRESS_ONE = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS_TWO = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS_TWO = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddressMockMvc;

    private Address address;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressResource addressResource = new AddressResource(addressService);
        this.restAddressMockMvc = MockMvcBuilders.standaloneSetup(addressResource)
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
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
                .streetAddressOne(DEFAULT_STREET_ADDRESS_ONE)
                .streetAddressTwo(DEFAULT_STREET_ADDRESS_TWO)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .zipCode(DEFAULT_ZIP_CODE);
        return address;
    }

    @Before
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getStreetAddressOne()).isEqualTo(DEFAULT_STREET_ADDRESS_ONE);
        assertThat(testAddress.getStreetAddressTwo()).isEqualTo(DEFAULT_STREET_ADDRESS_TWO);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        Address existingAddress = new Address();
        existingAddress.setId(1L);
        AddressDTO existingAddressDTO = addressMapper.addressToAddressDTO(existingAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStreetAddressOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setStreetAddressOne(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setCity(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setState(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setZipCode(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddressOne").value(hasItem(DEFAULT_STREET_ADDRESS_ONE.toString())))
            .andExpect(jsonPath("$.[*].streetAddressTwo").value(hasItem(DEFAULT_STREET_ADDRESS_TWO.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.streetAddressOne").value(DEFAULT_STREET_ADDRESS_ONE.toString()))
            .andExpect(jsonPath("$.streetAddressTwo").value(DEFAULT_STREET_ADDRESS_TWO.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findOne(address.getId());
        updatedAddress
                .streetAddressOne(UPDATED_STREET_ADDRESS_ONE)
                .streetAddressTwo(UPDATED_STREET_ADDRESS_TWO)
                .city(UPDATED_CITY)
                .state(UPDATED_STATE)
                .zipCode(UPDATED_ZIP_CODE);
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(updatedAddress);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getStreetAddressOne()).isEqualTo(UPDATED_STREET_ADDRESS_ONE);
        assertThat(testAddress.getStreetAddressTwo()).isEqualTo(UPDATED_STREET_ADDRESS_TWO);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Get the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
    }
}
