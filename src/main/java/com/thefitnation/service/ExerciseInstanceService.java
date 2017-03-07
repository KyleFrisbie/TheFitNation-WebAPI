package com.thefitnation.service;

import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.repository.ExerciseInstanceRepository;
import com.thefitnation.service.dto.ExerciseInstanceDTO;
import com.thefitnation.service.mapper.ExerciseInstanceMapper;
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
 * Service Implementation for managing ExerciseInstance.
 */
@Service
@Transactional
public class ExerciseInstanceService {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceService.class);
    
    private final ExerciseInstanceRepository exerciseInstanceRepository;

    private final ExerciseInstanceMapper exerciseInstanceMapper;

    public ExerciseInstanceService(ExerciseInstanceRepository exerciseInstanceRepository, ExerciseInstanceMapper exerciseInstanceMapper) {
        this.exerciseInstanceRepository = exerciseInstanceRepository;
        this.exerciseInstanceMapper = exerciseInstanceMapper;
    }

    /**
     * Save a exerciseInstance.
     *
     * @param exerciseInstanceDTO the entity to save
     * @return the persisted entity
     */
    public ExerciseInstanceDTO save(ExerciseInstanceDTO exerciseInstanceDTO) {
        log.debug("Request to save ExerciseInstance : {}", exerciseInstanceDTO);
        ExerciseInstance exerciseInstance = exerciseInstanceMapper.exerciseInstanceDTOToExerciseInstance(exerciseInstanceDTO);
        exerciseInstance = exerciseInstanceRepository.save(exerciseInstance);
        ExerciseInstanceDTO result = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        return result;
    }

    /**
     *  Get all the exerciseInstances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExerciseInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseInstances");
        Page<ExerciseInstance> result = exerciseInstanceRepository.findAll(pageable);
        return result.map(exerciseInstance -> exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance));
    }

    /**
     *  Get one exerciseInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ExerciseInstanceDTO findOne(Long id) {
        log.debug("Request to get ExerciseInstance : {}", id);
        ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne(id);
        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        return exerciseInstanceDTO;
    }

    /**
     *  Delete the  exerciseInstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExerciseInstance : {}", id);
        exerciseInstanceRepository.delete(id);
    }
}
