package com.thefitnation.service;

import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.repository.search.UserWorkoutTemplateSearchRepository;
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
 * Service Implementation for managing UserWorkoutTemplate.
 */
@Service
@Transactional
public class UserWorkoutTemplateService {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutTemplateService.class);
    
    @Inject
    private UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    @Inject
    private UserWorkoutTemplateSearchRepository userWorkoutTemplateSearchRepository;

    /**
     * Save a userWorkoutTemplate.
     *
     * @param userWorkoutTemplate the entity to save
     * @return the persisted entity
     */
    public UserWorkoutTemplate save(UserWorkoutTemplate userWorkoutTemplate) {
        log.debug("Request to save UserWorkoutTemplate : {}", userWorkoutTemplate);
        UserWorkoutTemplate result = userWorkoutTemplateRepository.save(userWorkoutTemplate);
        userWorkoutTemplateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the userWorkoutTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserWorkoutTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorkoutTemplates");
        Page<UserWorkoutTemplate> result = userWorkoutTemplateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one userWorkoutTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserWorkoutTemplate findOne(Long id) {
        log.debug("Request to get UserWorkoutTemplate : {}", id);
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateRepository.findOne(id);
        return userWorkoutTemplate;
    }

    /**
     *  Delete the  userWorkoutTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWorkoutTemplate : {}", id);
        userWorkoutTemplateRepository.delete(id);
        userWorkoutTemplateSearchRepository.delete(id);
    }

    /**
     * Search for the userWorkoutTemplate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWorkoutTemplate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserWorkoutTemplates for query {}", query);
        Page<UserWorkoutTemplate> result = userWorkoutTemplateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
