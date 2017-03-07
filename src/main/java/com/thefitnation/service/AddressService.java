package com.thefitnation.service;

import com.thefitnation.domain.Address;
import com.thefitnation.repository.AddressRepository;
import com.thefitnation.service.dto.AddressDTO;
import com.thefitnation.service.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Address.
 */
@Service
@Transactional
public class AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressService.class);
    
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.addressDTOToAddress(addressDTO);
        address = addressRepository.save(address);
        AddressDTO result = addressMapper.addressToAddressDTO(address);
        return result;
    }

    /**
     *  Get all the addresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        Page<Address> result = addressRepository.findAll(pageable);
        return result.map(address -> addressMapper.addressToAddressDTO(address));
    }


    /**
     *  get all the addresses where Location is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AddressDTO> findAllWhereLocationIsNull() {
        log.debug("Request to get all addresses where Location is null");
        return StreamSupport
            .stream(addressRepository.findAll().spliterator(), false)
            .filter(address -> address.getLocation() == null)
            .map(addressMapper::addressToAddressDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one address by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AddressDTO findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);
        return addressDTO;
    }

    /**
     *  Delete the  address by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.delete(id);
    }
}
