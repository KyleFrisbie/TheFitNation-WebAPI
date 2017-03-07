package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.ExerciseInstanceSetService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
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
 * REST controller for managing ExerciseInstanceSet.
 */
@RestController
@RequestMapping("/api")
public class ExerciseInstanceSetResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceSetResource.class);

    private static final String ENTITY_NAME = "exerciseInstanceSet";
        
    private final ExerciseInstanceSetService exerciseInstanceSetService;

    public ExerciseInstanceSetResource(ExerciseInstanceSetService exerciseInstanceSetService) {
        this.exerciseInstanceSetService = exerciseInstanceSetService;
    }

    /**
     * POST  /exercise-instance-sets : Create a new exerciseInstanceSet.
     *
     * @param exerciseInstanceSetDTO the exerciseInstanceSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseInstanceSetDTO, or with status 400 (Bad Request) if the exerciseInstanceSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercise-instance-sets")
    @Timed
    public ResponseEntity<ExerciseInstanceSetDTO> createExerciseInstanceSet(@Valid @RequestBody ExerciseInstanceSetDTO exerciseInstanceSetDTO) throws URISyntaxException {
        log.debug("REST request to save ExerciseInstanceSet : {}", exerciseInstanceSetDTO);
        if (exerciseInstanceSetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new exerciseInstanceSet cannot already have an ID")).body(null);
        }
        ExerciseInstanceSetDTO result = exerciseInstanceSetService.save(exerciseInstanceSetDTO);
        return ResponseEntity.created(new URI("/api/exercise-instance-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-instance-sets : Updates an existing exerciseInstanceSet.
     *
     * @param exerciseInstanceSetDTO the exerciseInstanceSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseInstanceSetDTO,
     * or with status 400 (Bad Request) if the exerciseInstanceSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseInstanceSetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercise-instance-sets")
    @Timed
    public ResponseEntity<ExerciseInstanceSetDTO> updateExerciseInstanceSet(@Valid @RequestBody ExerciseInstanceSetDTO exerciseInstanceSetDTO) throws URISyntaxException {
        log.debug("REST request to update ExerciseInstanceSet : {}", exerciseInstanceSetDTO);
        if (exerciseInstanceSetDTO.getId() == null) {
            return createExerciseInstanceSet(exerciseInstanceSetDTO);
        }
        ExerciseInstanceSetDTO result = exerciseInstanceSetService.save(exerciseInstanceSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exerciseInstanceSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-instance-sets : get all the exerciseInstanceSets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseInstanceSets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/exercise-instance-sets")
    @Timed
    public ResponseEntity<List<ExerciseInstanceSetDTO>> getAllExerciseInstanceSets(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ExerciseInstanceSets");
        Page<ExerciseInstanceSetDTO> page = exerciseInstanceSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-instance-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-instance-sets/:id : get the "id" exerciseInstanceSet.
     *
     * @param id the id of the exerciseInstanceSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseInstanceSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercise-instance-sets/{id}")
    @Timed
    public ResponseEntity<ExerciseInstanceSetDTO> getExerciseInstanceSet(@PathVariable Long id) {
        log.debug("REST request to get ExerciseInstanceSet : {}", id);
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseInstanceSetDTO));
    }

    /**
     * DELETE  /exercise-instance-sets/:id : delete the "id" exerciseInstanceSet.
     *
     * @param id the id of the exerciseInstanceSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercise-instance-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteExerciseInstanceSet(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseInstanceSet : {}", id);
        exerciseInstanceSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
