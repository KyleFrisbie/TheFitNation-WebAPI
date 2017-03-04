package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.WorkoutLog;
import com.thefitnation.service.WorkoutLogService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing WorkoutLog.
 */
@RestController
@RequestMapping("/api")
public class WorkoutLogResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutLogResource.class);
        
    @Inject
    private WorkoutLogService workoutLogService;

    /**
     * POST  /workout-logs : Create a new workoutLog.
     *
     * @param workoutLog the workoutLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workoutLog, or with status 400 (Bad Request) if the workoutLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workout-logs")
    @Timed
    public ResponseEntity<WorkoutLog> createWorkoutLog(@Valid @RequestBody WorkoutLog workoutLog) throws URISyntaxException {
        log.debug("REST request to save WorkoutLog : {}", workoutLog);
        if (workoutLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workoutLog", "idexists", "A new workoutLog cannot already have an ID")).body(null);
        }
        WorkoutLog result = workoutLogService.save(workoutLog);
        return ResponseEntity.created(new URI("/api/workout-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workoutLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workout-logs : Updates an existing workoutLog.
     *
     * @param workoutLog the workoutLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workoutLog,
     * or with status 400 (Bad Request) if the workoutLog is not valid,
     * or with status 500 (Internal Server Error) if the workoutLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workout-logs")
    @Timed
    public ResponseEntity<WorkoutLog> updateWorkoutLog(@Valid @RequestBody WorkoutLog workoutLog) throws URISyntaxException {
        log.debug("REST request to update WorkoutLog : {}", workoutLog);
        if (workoutLog.getId() == null) {
            return createWorkoutLog(workoutLog);
        }
        WorkoutLog result = workoutLogService.save(workoutLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workoutLog", workoutLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workout-logs : get all the workoutLogs.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of workoutLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/workout-logs")
    @Timed
    public ResponseEntity<List<WorkoutLog>> getAllWorkoutLogs(@ApiParam Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("userdemographic-is-null".equals(filter)) {
            log.debug("REST request to get all WorkoutLogs where userDemographic is null");
            return new ResponseEntity<>(workoutLogService.findAllWhereUserDemographicIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of WorkoutLogs");
        Page<WorkoutLog> page = workoutLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workout-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workout-logs/:id : get the "id" workoutLog.
     *
     * @param id the id of the workoutLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workoutLog, or with status 404 (Not Found)
     */
    @GetMapping("/workout-logs/{id}")
    @Timed
    public ResponseEntity<WorkoutLog> getWorkoutLog(@PathVariable Long id) {
        log.debug("REST request to get WorkoutLog : {}", id);
        WorkoutLog workoutLog = workoutLogService.findOne(id);
        return Optional.ofNullable(workoutLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /workout-logs/:id : delete the "id" workoutLog.
     *
     * @param id the id of the workoutLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workout-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkoutLog(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutLog : {}", id);
        workoutLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workoutLog", id.toString())).build();
    }

}
