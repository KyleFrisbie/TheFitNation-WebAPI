package com.thefitnation.service;

import com.thefitnation.domain.Gym;
import com.thefitnation.repository.GymRepository;
import com.thefitnation.service.dto.GymDTO;
import com.thefitnation.service.mapper.GymMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Gym.
 */
@Service
@Transactional
public class GymService {

    private final Logger log = LoggerFactory.getLogger(GymService.class);
    
    private final GymRepository gymRepository;

    private final GymMapper gymMapper;

    public GymService(GymRepository gymRepository, GymMapper gymMapper) {
        this.gymRepository = gymRepository;
        this.gymMapper = gymMapper;
    }

    /**
     * Save a gym.
     *
     * @param gymDTO the entity to save
     * @return the persisted entity
     */
    public GymDTO save(GymDTO gymDTO) {
        log.debug("Request to save Gym : {}", gymDTO);
        Gym gym = gymMapper.gymDTOToGym(gymDTO);
        gym = gymRepository.save(gym);
        GymDTO result = gymMapper.gymToGymDTO(gym);
        return result;
    }

    /**
     *  Get all the gyms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GymDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gyms");
        Page<Gym> result = gymRepository.findAll(pageable);
        return result.map(gym -> gymMapper.gymToGymDTO(gym));
    }

    /**
     *  Get one gym by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GymDTO findOne(Long id) {
        log.debug("Request to get Gym : {}", id);
        Gym gym = gymRepository.findOne(id);
        GymDTO gymDTO = gymMapper.gymToGymDTO(gym);
        return gymDTO;
    }

    /**
     *  Delete the  gym by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Gym : {}", id);
        gymRepository.delete(id);
    }
}
