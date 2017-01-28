package com.thefitnation.service;

import com.thefitnation.domain.Muscle;
import com.thefitnation.repository.MuscleRepository;
import com.thefitnation.repository.search.MuscleSearchRepository;
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
 * Service Implementation for managing Muscle.
 */
@Service
@Transactional
public class MuscleService {

    private final Logger log = LoggerFactory.getLogger(MuscleService.class);
    
    @Inject
    private MuscleRepository muscleRepository;

    @Inject
    private MuscleSearchRepository muscleSearchRepository;

    /**
     * Save a muscle.
     *
     * @param muscle the entity to save
     * @return the persisted entity
     */
    public Muscle save(Muscle muscle) {
        log.debug("Request to save Muscle : {}", muscle);
        Muscle result = muscleRepository.save(muscle);
        muscleSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the muscles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Muscle> findAll(Pageable pageable) {
        log.debug("Request to get all Muscles");
        Page<Muscle> result = muscleRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one muscle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Muscle findOne(Long id) {
        log.debug("Request to get Muscle : {}", id);
        Muscle muscle = muscleRepository.findOne(id);
        return muscle;
    }

    /**
     *  Delete the  muscle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Muscle : {}", id);
        muscleRepository.delete(id);
        muscleSearchRepository.delete(id);
    }

    /**
     * Search for the muscle corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Muscle> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Muscles for query {}", query);
        Page<Muscle> result = muscleSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
