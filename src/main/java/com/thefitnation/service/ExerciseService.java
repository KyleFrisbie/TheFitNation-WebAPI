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
        return result.map(exerciseMapper::exerciseToExerciseDTO);
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
        return exerciseMapper.exerciseToExerciseDTO(exercise);
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
