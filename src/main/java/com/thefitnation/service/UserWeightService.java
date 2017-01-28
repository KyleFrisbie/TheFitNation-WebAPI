package com.thefitnation.service;

import com.thefitnation.domain.UserWeight;
import com.thefitnation.repository.UserWeightRepository;
import com.thefitnation.repository.search.UserWeightSearchRepository;
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
 * Service Implementation for managing UserWeight.
 */
@Service
@Transactional
public class UserWeightService {

    private final Logger log = LoggerFactory.getLogger(UserWeightService.class);
    
    @Inject
    private UserWeightRepository userWeightRepository;

    @Inject
    private UserWeightSearchRepository userWeightSearchRepository;

    /**
     * Save a userWeight.
     *
     * @param userWeight the entity to save
     * @return the persisted entity
     */
    public UserWeight save(UserWeight userWeight) {
        log.debug("Request to save UserWeight : {}", userWeight);
        UserWeight result = userWeightRepository.save(userWeight);
        userWeightSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the userWeights.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserWeight> findAll(Pageable pageable) {
        log.debug("Request to get all UserWeights");
        Page<UserWeight> result = userWeightRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one userWeight by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserWeight findOne(Long id) {
        log.debug("Request to get UserWeight : {}", id);
        UserWeight userWeight = userWeightRepository.findOne(id);
        return userWeight;
    }

    /**
     *  Delete the  userWeight by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWeight : {}", id);
        userWeightRepository.delete(id);
        userWeightSearchRepository.delete(id);
    }

    /**
     * Search for the userWeight corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWeight> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserWeights for query {}", query);
        Page<UserWeight> result = userWeightSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
