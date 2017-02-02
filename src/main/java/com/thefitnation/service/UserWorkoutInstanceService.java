package com.thefitnation.service;

import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.repository.UserWorkoutInstanceRepository;
import com.thefitnation.repository.search.UserWorkoutInstanceSearchRepository;
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
 * Service Implementation for managing UserWorkoutInstance.
 */
@Service
@Transactional
public class UserWorkoutInstanceService {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutInstanceService.class);
    
    @Inject
    private UserWorkoutInstanceRepository userWorkoutInstanceRepository;

    @Inject
    private UserWorkoutInstanceSearchRepository userWorkoutInstanceSearchRepository;

    /**
     * Save a userWorkoutInstance.
     *
     * @param userWorkoutInstance the entity to save
     * @return the persisted entity
     */
    public UserWorkoutInstance save(UserWorkoutInstance userWorkoutInstance) {
        log.debug("Request to save UserWorkoutInstance : {}", userWorkoutInstance);
        UserWorkoutInstance result = userWorkoutInstanceRepository.save(userWorkoutInstance);
        userWorkoutInstanceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the userWorkoutInstances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserWorkoutInstance> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorkoutInstances");
        Page<UserWorkoutInstance> result = userWorkoutInstanceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one userWorkoutInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserWorkoutInstance findOne(Long id) {
        log.debug("Request to get UserWorkoutInstance : {}", id);
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceRepository.findOne(id);
        return userWorkoutInstance;
    }

    /**
     *  Delete the  userWorkoutInstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWorkoutInstance : {}", id);
        userWorkoutInstanceRepository.delete(id);
        userWorkoutInstanceSearchRepository.delete(id);
    }

    /**
     * Search for the userWorkoutInstance corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWorkoutInstance> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserWorkoutInstances for query {}", query);
        Page<UserWorkoutInstance> result = userWorkoutInstanceSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
