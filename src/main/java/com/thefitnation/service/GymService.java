package com.thefitnation.service;

import com.thefitnation.domain.Gym;
import com.thefitnation.repository.GymRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Gym.
 */
@Service
@Transactional
public class GymService {

    private final Logger log = LoggerFactory.getLogger(GymService.class);
    
    @Inject
    private GymRepository gymRepository;

    /**
     * Save a gym.
     *
     * @param gym the entity to save
     * @return the persisted entity
     */
    public Gym save(Gym gym) {
        log.debug("Request to save Gym : {}", gym);
        Gym result = gymRepository.save(gym);
        return result;
    }

    /**
     *  Get all the gyms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Gym> findAll(Pageable pageable) {
        log.debug("Request to get all Gyms");
        Page<Gym> result = gymRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one gym by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Gym findOne(Long id) {
        log.debug("Request to get Gym : {}", id);
        Gym gym = gymRepository.findOneWithEagerRelationships(id);
        return gym;
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
