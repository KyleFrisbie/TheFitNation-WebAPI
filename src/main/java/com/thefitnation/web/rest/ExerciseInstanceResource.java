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
 * REST controller for managing ExerciseInstance.
 */
@RestController
@RequestMapping("/api")
public class ExerciseInstanceResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceResource.class);

    private static final String ENTITY_NAME = "exerciseInstance";

    private final ExerciseInstanceService exerciseInstanceService;
    private final UserRepository userRepository;

    public ExerciseInstanceResource(ExerciseInstanceService exerciseInstanceService, UserRepository userRepository) {
        this.exerciseInstanceService = exerciseInstanceService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /exercise-instances : Create a new exerciseInstance.
     *
     * @param exerciseInstanceDTO the exerciseInstanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseInstanceDTO, or with status 400 (Bad Request) if the exerciseInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercise-instances")
    @Timed
    public ResponseEntity<ExerciseInstanceDTO> createExerciseInstance(@Valid @RequestBody ExerciseInstanceDTO exerciseInstanceDTO) throws URISyntaxException {
        log.debug("REST request to save ExerciseInstance : {}", exerciseInstanceDTO);
        if (exerciseInstanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new exerciseInstance cannot already have an ID")).body(null);
        }
        ExerciseInstanceDTO result = exerciseInstanceService.save(exerciseInstanceDTO);
        return ResponseEntity.created(new URI("/api/exercise-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-instances : Updates an existing exerciseInstance.
     *
     * @param exerciseInstanceDTO the exerciseInstanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseInstanceDTO,
     * or with status 400 (Bad Request) if the exerciseInstanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseInstanceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercise-instances")
    @Timed
    public ResponseEntity<ExerciseInstanceDTO> updateExerciseInstance(@Valid @RequestBody ExerciseInstanceDTO exerciseInstanceDTO) throws URISyntaxException {
        log.debug("REST request to update ExerciseInstance : {}", exerciseInstanceDTO);
        if (exerciseInstanceDTO.getId() == null) {
            return createExerciseInstance(exerciseInstanceDTO);
        }
        ExerciseInstanceDTO result = exerciseInstanceService.save(exerciseInstanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exerciseInstanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-instances : get all the exerciseInstances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseInstances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/exercise-instances")
    @Timed
    public ResponseEntity<List<ExerciseInstanceDTO>> getAllExerciseInstances(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ExerciseInstances");
        Page<ExerciseInstanceDTO> page = exerciseInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-instances/:id : get the "id" exerciseInstance.
     *
     * @param id the id of the exerciseInstanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseInstanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercise-instances/{id}")
    @Timed
    public ResponseEntity<ExerciseInstanceDTO> getExerciseInstance(@PathVariable Long id) {
        log.debug("REST request to get ExerciseInstance : {}", id);
        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseInstanceDTO));
    }

    /**
     * DELETE  /exercise-instances/:id : delete the "id" exerciseInstance.
     *
     * @param id the id of the exerciseInstanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercise-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteExerciseInstance(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseInstance : {}", id);
        exerciseInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
