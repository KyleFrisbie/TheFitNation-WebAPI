package com.thefitnation.service;

import com.thefitnation.domain.Exercise;
import com.thefitnation.repository.ExerciseRepository;
import com.thefitnation.service.dto.ExerciseDTO;
import com.thefitnation.service.mapper.ExerciseMapper;
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
 * Service Implementation for managing Exercise.
 */
@Service
@Transactional
public class ExerciseService {

    private final Logger log = LoggerFactory.getLogger(ExerciseService.class);
    
    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    /**
     * Save a exercise.
     *
     * @param exerciseDTO the entity to save
     * @return the persisted entity
     */
    public ExerciseDTO save(ExerciseDTO exerciseDTO) {
        log.debug("Request to save Exercise : {}", exerciseDTO);
        Exercise exercise = exerciseMapper.exerciseDTOToExercise(exerciseDTO);
        exercise = exerciseRepository.save(exercise);
        ExerciseDTO result = exerciseMapper.exerciseToExerciseDTO(exercise);
        return result;
    }

    /**
     *  Get all the exercises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExerciseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exercises");
        Page<Exercise> result = exerciseRepository.findAll(pageable);
        return result.map(exercise -> exerciseMapper.exerciseToExerciseDTO(exercise));
    }

    /**
     *  Get one exercise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ExerciseDTO findOne(Long id) {
        log.debug("Request to get Exercise : {}", id);
        Exercise exercise = exerciseRepository.findOneWithEagerRelationships(id);
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);
        return exerciseDTO;
    }

    /**
     *  Delete the  exercise by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Exercise : {}", id);
        exerciseRepository.delete(id);
    }
}
