package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.web.rest.util.*;
import io.github.jhipster.web.util.*;
import io.swagger.annotations.*;
import java.net.*;
import java.util.*;
import javax.validation.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing WorkoutTemplate.
 */
@RestController
@RequestMapping("/api")
public class WorkoutTemplateResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutTemplateResource.class);

    private static final String ENTITY_NAME = "workoutTemplate";

    private final WorkoutTemplateService workoutTemplateService;

    public WorkoutTemplateResource(WorkoutTemplateService workoutTemplateService, UserRepository userRepository) {
        this.workoutTemplateService = workoutTemplateService;
    }

    /**
     * POST  /workout-templates : Create a new workoutTemplate.
     *
     * @param workoutTemplateDTO the workoutTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workoutTemplateDTO, or with status 400 (Bad Request) if the workoutTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workout-templates")
    @Timed
    public ResponseEntity<WorkoutTemplateDTO> createWorkoutTemplate(@Valid @RequestBody WorkoutTemplateDTO workoutTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save WorkoutTemplate : {}", workoutTemplateDTO);
        if (workoutTemplateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workoutTemplate cannot already have an ID")).body(null);
        }
        WorkoutTemplateDTO result = workoutTemplateService.save(workoutTemplateDTO);
        return ResponseEntity.created(new URI("/api/workout-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workout-templates : Updates an existing workoutTemplate.
     *
     * @param workoutTemplateDTO the workoutTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workoutTemplateDTO,
     * or with status 400 (Bad Request) if the workoutTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the workoutTemplateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workout-templates")
    @Timed
    public ResponseEntity<WorkoutTemplateDTO> updateWorkoutTemplate(@Valid @RequestBody WorkoutTemplateDTO workoutTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update WorkoutTemplate : {}", workoutTemplateDTO);
        if (workoutTemplateDTO.getId() == null) {
            return createWorkoutTemplate(workoutTemplateDTO);
        }
        WorkoutTemplateDTO result = workoutTemplateService.save(workoutTemplateDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workout-templates : get all the workoutTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workoutTemplates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/workout-templates")
    @Timed
    public ResponseEntity<List<WorkoutTemplateDTO>> getAllWorkoutTemplates(@ApiParam Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of WorkoutTemplates");
        Page<WorkoutTemplateDTO> page = workoutTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workout-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workout-templates/:id : get the "id" workoutTemplate.
     *
     * @param id the id of the workoutTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workoutTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workout-templates/{id}")
    @Timed
    public ResponseEntity<WorkoutTemplateWithChildrenDTO> getWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to get WorkoutTemplate : {}", id);
        WorkoutTemplateWithChildrenDTO workoutTemplateDTO = workoutTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workoutTemplateDTO));
    }

    /**
     * DELETE  /workout-templates/:id : delete the "id" workoutTemplate.
     *
     * @param id the id of the workoutTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workout-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutTemplate : {}", id);
        workoutTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
