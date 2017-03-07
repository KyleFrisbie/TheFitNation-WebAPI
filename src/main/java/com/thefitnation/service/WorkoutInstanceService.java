package com.thefitnation.service;

import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.repository.WorkoutInstanceRepository;
import com.thefitnation.service.dto.WorkoutInstanceDTO;
import com.thefitnation.service.mapper.WorkoutInstanceMapper;
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
 * Service Implementation for managing WorkoutInstance.
 */
@Service
@Transactional
public class WorkoutInstanceService {

    private final Logger log = LoggerFactory.getLogger(WorkoutInstanceService.class);
    
    private final WorkoutInstanceRepository workoutInstanceRepository;

    private final WorkoutInstanceMapper workoutInstanceMapper;

    public WorkoutInstanceService(WorkoutInstanceRepository workoutInstanceRepository, WorkoutInstanceMapper workoutInstanceMapper) {
        this.workoutInstanceRepository = workoutInstanceRepository;
        this.workoutInstanceMapper = workoutInstanceMapper;
    }

    /**
     * Save a workoutInstance.
     *
     * @param workoutInstanceDTO the entity to save
     * @return the persisted entity
     */
    public WorkoutInstanceDTO save(WorkoutInstanceDTO workoutInstanceDTO) {
        log.debug("Request to save WorkoutInstance : {}", workoutInstanceDTO);
        WorkoutInstance workoutInstance = workoutInstanceMapper.workoutInstanceDTOToWorkoutInstance(workoutInstanceDTO);
        workoutInstance = workoutInstanceRepository.save(workoutInstance);
        WorkoutInstanceDTO result = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        return result;
    }

    /**
     *  Get all the workoutInstances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutInstances");
        Page<WorkoutInstance> result = workoutInstanceRepository.findAll(pageable);
        return result.map(workoutInstance -> workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance));
    }

    /**
     *  Get one workoutInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkoutInstanceDTO findOne(Long id) {
        log.debug("Request to get WorkoutInstance : {}", id);
        WorkoutInstance workoutInstance = workoutInstanceRepository.findOne(id);
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        return workoutInstanceDTO;
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
