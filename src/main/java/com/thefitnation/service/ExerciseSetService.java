package com.thefitnation.service;

import com.thefitnation.domain.ExerciseSet;
import com.thefitnation.repository.ExerciseSetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ExerciseSet.
 */
@Service
@Transactional
public class ExerciseSetService {

    private final Logger log = LoggerFactory.getLogger(ExerciseSetService.class);
    
    @Inject
    private ExerciseSetRepository exerciseSetRepository;

    /**
     * Save a exerciseSet.
     *
     * @param exerciseSet the entity to save
     * @return the persisted entity
     */
    public ExerciseSet save(ExerciseSet exerciseSet) {
        log.debug("Request to save ExerciseSet : {}", exerciseSet);
        ExerciseSet result = exerciseSetRepository.save(exerciseSet);
        return result;
    }

    /**
     *  Get all the exerciseSets.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ExerciseSet> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseSets");
        Page<ExerciseSet> result = exerciseSetRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one exerciseSet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ExerciseSet findOne(Long id) {
        log.debug("Request to get ExerciseSet : {}", id);
        ExerciseSet exerciseSet = exerciseSetRepository.findOne(id);
        return exerciseSet;
    }

    /**
     *  Delete the  exerciseSet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExerciseSet : {}", id);
        exerciseSetRepository.delete(id);
    }
}
