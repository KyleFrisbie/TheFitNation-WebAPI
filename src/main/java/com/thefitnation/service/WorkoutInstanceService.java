package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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
     *  Get all the workoutInstances by currentl logged in user.
     *
     *
     * @param login
     * @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutInstanceDTO> findAllByCurrentLoggedInUser(String login, Pageable pageable) {
        log.debug("Request to get all WorkoutInstances by current logged in user");
        Page<WorkoutInstance> result = workoutInstanceRepository.findAllByCurrentLoggedInUser(login, pageable);
        return result.map(workoutInstanceMapper::workoutInstanceToWorkoutInstanceDTO);
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
