package com.thefitnation.service;

import com.thefitnation.domain.UserExerciseSet;
import com.thefitnation.repository.UserExerciseSetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing UserExerciseSet.
 */
@Service
@Transactional
public class UserExerciseSetService {

    private final Logger log = LoggerFactory.getLogger(UserExerciseSetService.class);
    
    @Inject
    private UserExerciseSetRepository userExerciseSetRepository;

    /**
     * Save a userExerciseSet.
     *
     * @param userExerciseSet the entity to save
     * @return the persisted entity
     */
    public UserExerciseSet save(UserExerciseSet userExerciseSet) {
        log.debug("Request to save UserExerciseSet : {}", userExerciseSet);
        UserExerciseSet result = userExerciseSetRepository.save(userExerciseSet);
        return result;
    }

    /**
     *  Get all the userExerciseSets.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserExerciseSet> findAll(Pageable pageable) {
        log.debug("Request to get all UserExerciseSets");
        Page<UserExerciseSet> result = userExerciseSetRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one userExerciseSet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserExerciseSet findOne(Long id) {
        log.debug("Request to get UserExerciseSet : {}", id);
        UserExerciseSet userExerciseSet = userExerciseSetRepository.findOne(id);
        return userExerciseSet;
    }

    /**
     *  Delete the  userExerciseSet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExerciseSet : {}", id);
        userExerciseSetRepository.delete(id);
    }
}
