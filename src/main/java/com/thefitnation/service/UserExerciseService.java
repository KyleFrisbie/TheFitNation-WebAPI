package com.thefitnation.service;

import com.thefitnation.domain.UserExercise;
import com.thefitnation.repository.UserExerciseRepository;
import com.thefitnation.repository.search.UserExerciseSearchRepository;
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
 * Service Implementation for managing UserExercise.
 */
@Service
@Transactional
public class UserExerciseService {

    private final Logger log = LoggerFactory.getLogger(UserExerciseService.class);
    
    @Inject
    private UserExerciseRepository userExerciseRepository;

    @Inject
    private UserExerciseSearchRepository userExerciseSearchRepository;

    /**
     * Save a userExercise.
     *
     * @param userExercise the entity to save
     * @return the persisted entity
     */
    public UserExercise save(UserExercise userExercise) {
        log.debug("Request to save UserExercise : {}", userExercise);
        UserExercise result = userExerciseRepository.save(userExercise);
        userExerciseSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the userExercises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserExercise> findAll(Pageable pageable) {
        log.debug("Request to get all UserExercises");
        Page<UserExercise> result = userExerciseRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one userExercise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserExercise findOne(Long id) {
        log.debug("Request to get UserExercise : {}", id);
        UserExercise userExercise = userExerciseRepository.findOne(id);
        return userExercise;
    }

    /**
     *  Delete the  userExercise by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExercise : {}", id);
        userExerciseRepository.delete(id);
        userExerciseSearchRepository.delete(id);
    }

    /**
     * Search for the userExercise corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserExercise> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserExercises for query {}", query);
        Page<UserExercise> result = userExerciseSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
