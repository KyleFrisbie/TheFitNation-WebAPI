package com.thefitnation.service;

import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.repository.WorkoutTemplateRepository;
import com.thefitnation.repository.search.WorkoutTemplateSearchRepository;
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
 * Service Implementation for managing WorkoutTemplate.
 */
@Service
@Transactional
public class WorkoutTemplateService {

    private final Logger log = LoggerFactory.getLogger(WorkoutTemplateService.class);
    
    @Inject
    private WorkoutTemplateRepository workoutTemplateRepository;

    @Inject
    private WorkoutTemplateSearchRepository workoutTemplateSearchRepository;

    /**
     * Save a workoutTemplate.
     *
     * @param workoutTemplate the entity to save
     * @return the persisted entity
     */
    public WorkoutTemplate save(WorkoutTemplate workoutTemplate) {
        log.debug("Request to save WorkoutTemplate : {}", workoutTemplate);
        WorkoutTemplate result = workoutTemplateRepository.save(workoutTemplate);
        workoutTemplateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the workoutTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WorkoutTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutTemplates");
        Page<WorkoutTemplate> result = workoutTemplateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one workoutTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WorkoutTemplate findOne(Long id) {
        log.debug("Request to get WorkoutTemplate : {}", id);
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOneWithEagerRelationships(id);
        return workoutTemplate;
    }

    /**
     *  Delete the  workoutTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkoutTemplate : {}", id);
        workoutTemplateRepository.delete(id);
        workoutTemplateSearchRepository.delete(id);
    }

    /**
     * Search for the workoutTemplate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutTemplate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorkoutTemplates for query {}", query);
        Page<WorkoutTemplate> result = workoutTemplateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
