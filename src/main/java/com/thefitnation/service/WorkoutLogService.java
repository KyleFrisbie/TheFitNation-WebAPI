package com.thefitnation.service;

import com.thefitnation.domain.WorkoutLog;
import com.thefitnation.repository.WorkoutLogRepository;
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

/**
 * Service Implementation for managing WorkoutLog.
 */
@Service
@Transactional
public class WorkoutLogService {

    private final Logger log = LoggerFactory.getLogger(WorkoutLogService.class);
    
    @Inject
    private WorkoutLogRepository workoutLogRepository;

    /**
     * Save a workoutLog.
     *
     * @param workoutLog the entity to save
     * @return the persisted entity
     */
    public WorkoutLog save(WorkoutLog workoutLog) {
        log.debug("Request to save WorkoutLog : {}", workoutLog);
        WorkoutLog result = workoutLogRepository.save(workoutLog);
        return result;
    }

    /**
     *  Get all the workoutLogs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WorkoutLog> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutLogs");
        Page<WorkoutLog> result = workoutLogRepository.findAll(pageable);
        return result;
    }


    /**
     *  get all the workoutLogs where UserDemographic is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<WorkoutLog> findAllWhereUserDemographicIsNull() {
        log.debug("Request to get all workoutLogs where UserDemographic is null");
        return StreamSupport
            .stream(workoutLogRepository.findAll().spliterator(), false)
            .filter(workoutLog -> workoutLog.getUserDemographic() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one workoutLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WorkoutLog findOne(Long id) {
        log.debug("Request to get WorkoutLog : {}", id);
        WorkoutLog workoutLog = workoutLogRepository.findOne(id);
        return workoutLog;
    }

    /**
     *  Delete the  workoutLog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkoutLog : {}", id);
        workoutLogRepository.delete(id);
    }
}
