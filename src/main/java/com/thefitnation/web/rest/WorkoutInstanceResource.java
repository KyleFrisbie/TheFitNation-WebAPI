package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.WorkoutInstanceService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.WorkoutInstanceDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing WorkoutInstance.
 */
@RestController
@RequestMapping("/api")
public class WorkoutInstanceResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutInstanceResource.class);

    private static final String ENTITY_NAME = "workoutInstance";
        
    private final WorkoutInstanceService workoutInstanceService;

    public WorkoutInstanceResource(WorkoutInstanceService workoutInstanceService) {
        this.workoutInstanceService = workoutInstanceService;
    }

    /**
     * POST  /workout-instances : Create a new workoutInstance.
     *
     * @param workoutInstanceDTO the workoutInstanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workoutInstanceDTO, or with status 400 (Bad Request) if the workoutInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workout-instances")
    @Timed
    public ResponseEntity<WorkoutInstanceDTO> createWorkoutInstance(@Valid @RequestBody WorkoutInstanceDTO workoutInstanceDTO) throws URISyntaxException {
        log.debug("REST request to save WorkoutInstance : {}", workoutInstanceDTO);
        if (workoutInstanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workoutInstance cannot already have an ID")).body(null);
        }
        WorkoutInstanceDTO result = workoutInstanceService.save(workoutInstanceDTO);
        return ResponseEntity.created(new URI("/api/workout-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workout-instances : Updates an existing workoutInstance.
     *
     * @param workoutInstanceDTO the workoutInstanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workoutInstanceDTO,
     * or with status 400 (Bad Request) if the workoutInstanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the workoutInstanceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workout-instances")
    @Timed
    public ResponseEntity<WorkoutInstanceDTO> updateWorkoutInstance(@Valid @RequestBody WorkoutInstanceDTO workoutInstanceDTO) throws URISyntaxException {
        log.debug("REST request to update WorkoutInstance : {}", workoutInstanceDTO);
        if (workoutInstanceDTO.getId() == null) {
            return createWorkoutInstance(workoutInstanceDTO);
        }
        WorkoutInstanceDTO result = workoutInstanceService.save(workoutInstanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workoutInstanceDTO.getId().toString()))
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
    public ResponseEntity<List<WorkoutInstanceDTO>> getAllWorkoutInstances(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WorkoutInstances");
        Page<WorkoutInstanceDTO> page = workoutInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workout-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workout-instances/:id : get the "id" workoutInstance.
     *
     * @param id the id of the workoutInstanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workoutInstanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workout-instances/{id}")
    @Timed
    public ResponseEntity<WorkoutInstanceDTO> getWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to get WorkoutInstance : {}", id);
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workoutInstanceDTO));
    }

    /**
     * DELETE  /workout-instances/:id : delete the "id" workoutInstance.
     *
     * @param id the id of the workoutInstanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workout-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutInstance : {}", id);
        workoutInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
