package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.service.WorkoutInstanceService;
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

/**
 * REST controller for managing WorkoutInstance.
 */
@RestController
@RequestMapping("/api")
public class WorkoutInstanceResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutInstanceResource.class);
        
    @Inject
    private WorkoutInstanceService workoutInstanceService;

    /**
     * POST  /workout-instances : Create a new workoutInstance.
     *
     * @param workoutInstance the workoutInstance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workoutInstance, or with status 400 (Bad Request) if the workoutInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workout-instances")
    @Timed
    public ResponseEntity<WorkoutInstance> createWorkoutInstance(@Valid @RequestBody WorkoutInstance workoutInstance) throws URISyntaxException {
        log.debug("REST request to save WorkoutInstance : {}", workoutInstance);
        if (workoutInstance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workoutInstance", "idexists", "A new workoutInstance cannot already have an ID")).body(null);
        }
        WorkoutInstance result = workoutInstanceService.save(workoutInstance);
        return ResponseEntity.created(new URI("/api/workout-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workoutInstance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workout-instances : Updates an existing workoutInstance.
     *
     * @param workoutInstance the workoutInstance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workoutInstance,
     * or with status 400 (Bad Request) if the workoutInstance is not valid,
     * or with status 500 (Internal Server Error) if the workoutInstance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workout-instances")
    @Timed
    public ResponseEntity<WorkoutInstance> updateWorkoutInstance(@Valid @RequestBody WorkoutInstance workoutInstance) throws URISyntaxException {
        log.debug("REST request to update WorkoutInstance : {}", workoutInstance);
        if (workoutInstance.getId() == null) {
            return createWorkoutInstance(workoutInstance);
        }
        WorkoutInstance result = workoutInstanceService.save(workoutInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workoutInstance", workoutInstance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workout-instances : get all the workoutInstances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workoutInstances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/workout-instances")
    @Timed
    public ResponseEntity<List<WorkoutInstance>> getAllWorkoutInstances(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WorkoutInstances");
        Page<WorkoutInstance> page = workoutInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workout-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workout-instances/:id : get the "id" workoutInstance.
     *
     * @param id the id of the workoutInstance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workoutInstance, or with status 404 (Not Found)
     */
    @GetMapping("/workout-instances/{id}")
    @Timed
    public ResponseEntity<WorkoutInstance> getWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to get WorkoutInstance : {}", id);
        WorkoutInstance workoutInstance = workoutInstanceService.findOne(id);
        return Optional.ofNullable(workoutInstance)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /workout-instances/:id : delete the "id" workoutInstance.
     *
     * @param id the id of the workoutInstance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workout-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutInstance : {}", id);
        workoutInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workoutInstance", id.toString())).build();
    }

}
