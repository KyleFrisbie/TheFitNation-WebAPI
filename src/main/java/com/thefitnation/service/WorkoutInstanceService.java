package com.thefitnation.service;

import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.repository.WorkoutInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WorkoutInstance.
 */
@Service
@Transactional
public class WorkoutInstanceService {

    private final Logger log = LoggerFactory.getLogger(WorkoutInstanceService.class);
    
    @Inject
    private WorkoutInstanceRepository workoutInstanceRepository;

    /**
     * Save a workoutInstance.
     *
     * @param workoutInstance the entity to save
     * @return the persisted entity
     */
    public WorkoutInstance save(WorkoutInstance workoutInstance) {
        log.debug("Request to save WorkoutInstance : {}", workoutInstance);
        WorkoutInstance result = workoutInstanceRepository.save(workoutInstance);
        return result;
    }

    /**
     *  Get all the workoutInstances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WorkoutInstance> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutInstances");
        Page<WorkoutInstance> result = workoutInstanceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one workoutInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WorkoutInstance findOne(Long id) {
        log.debug("Request to get WorkoutInstance : {}", id);
        WorkoutInstance workoutInstance = workoutInstanceRepository.findOneWithEagerRelationships(id);
        return workoutInstance;
    }

    /**
     *  Delete the  workoutInstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkoutInstance : {}", id);
        workoutInstanceRepository.delete(id);
    }
}
