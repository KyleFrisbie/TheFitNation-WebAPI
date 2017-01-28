package com.thefitnation.service;

import com.thefitnation.domain.ExerciseSet;
import com.thefitnation.repository.ExerciseSetRepository;
import com.thefitnation.repository.search.ExerciseSetSearchRepository;
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
 * Service Implementation for managing ExerciseSet.
 */
@Service
@Transactional
public class ExerciseSetService {

    private final Logger log = LoggerFactory.getLogger(ExerciseSetService.class);
    
    @Inject
    private ExerciseSetRepository exerciseSetRepository;

    @Inject
    private ExerciseSetSearchRepository exerciseSetSearchRepository;

    /**
     * Save a exerciseSet.
     *
     * @param exerciseSet the entity to save
     * @return the persisted entity
     */
    public ExerciseSet save(ExerciseSet exerciseSet) {
        log.debug("Request to save ExerciseSet : {}", exerciseSet);
        ExerciseSet result = exerciseSetRepository.save(exerciseSet);
        exerciseSetSearchRepository.save(result);
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
        exerciseSetSearchRepository.delete(id);
    }

    /**
     * Search for the exerciseSet corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExerciseSet> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExerciseSets for query {}", query);
        Page<ExerciseSet> result = exerciseSetSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
