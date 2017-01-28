package com.thefitnation.service;

import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.repository.search.UserDemographicSearchRepository;
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
 * Service Implementation for managing UserDemographic.
 */
@Service
@Transactional
public class UserDemographicService {

    private final Logger log = LoggerFactory.getLogger(UserDemographicService.class);
    
    @Inject
    private UserDemographicRepository userDemographicRepository;

    @Inject
    private UserDemographicSearchRepository userDemographicSearchRepository;

    /**
     * Save a userDemographic.
     *
     * @param userDemographic the entity to save
     * @return the persisted entity
     */
    public UserDemographic save(UserDemographic userDemographic) {
        log.debug("Request to save UserDemographic : {}", userDemographic);
        UserDemographic result = userDemographicRepository.save(userDemographic);
        userDemographicSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the userDemographics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserDemographic> findAll(Pageable pageable) {
        log.debug("Request to get all UserDemographics");
        Page<UserDemographic> result = userDemographicRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one userDemographic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserDemographic findOne(Long id) {
        log.debug("Request to get UserDemographic : {}", id);
        UserDemographic userDemographic = userDemographicRepository.findOneWithEagerRelationships(id);
        return userDemographic;
    }

    /**
     *  Delete the  userDemographic by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserDemographic : {}", id);
        userDemographicRepository.delete(id);
        userDemographicSearchRepository.delete(id);
    }

    /**
     * Search for the userDemographic corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserDemographic> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserDemographics for query {}", query);
        Page<UserDemographic> result = userDemographicSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
