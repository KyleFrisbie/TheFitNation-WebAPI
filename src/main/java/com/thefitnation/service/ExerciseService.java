package com.thefitnation.service;

import com.thefitnation.domain.Exercise;
import com.thefitnation.repository.ExerciseRepository;
import com.thefitnation.repository.search.ExerciseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Exercise.
 */
@Service
@Transactional
public class ExerciseService {

    private final Logger log = LoggerFactory.getLogger(ExerciseService.class);
    
    @Inject
    private ExerciseRepository exerciseRepository;

    @Inject
    private ExerciseSearchRepository exerciseSearchRepository;

    /**
     * Save a exercise.
     *
     * @param exercise the entity to save
     * @return the persisted entity
     */
    public Exercise save(Exercise exercise) {
        log.debug("Request to save Exercise : {}", exercise);
        Exercise result = exerciseRepository.save(exercise);
        exerciseSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the exercises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Exercise> findAll(Pageable pageable) {
        log.debug("Request to get all Exercises");
        Page<Exercise> result = exerciseRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one exercise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Exercise findOne(Long id) {
        log.debug("Request to get Exercise : {}", id);
        Exercise exercise = exerciseRepository.findOneWithEagerRelationships(id);
        return exercise;
    }

    /**
     *  Delete the  exercise by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Exercise : {}", id);
        exerciseRepository.delete(id);
        exerciseSearchRepository.delete(id);
    }

    /**
     * Search for the exercise corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Exercise> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Exercises for query {}", query);
        Page<Exercise> result = exerciseSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
