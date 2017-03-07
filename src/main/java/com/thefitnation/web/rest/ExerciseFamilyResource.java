package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.ExerciseFamilyService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.ExerciseFamilyDTO;
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
 * REST controller for managing ExerciseFamily.
 */
@RestController
@RequestMapping("/api")
public class ExerciseFamilyResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseFamilyResource.class);

    private static final String ENTITY_NAME = "exerciseFamily";
        
    private final ExerciseFamilyService exerciseFamilyService;

    public ExerciseFamilyResource(ExerciseFamilyService exerciseFamilyService) {
        this.exerciseFamilyService = exerciseFamilyService;
    }

    /**
     * POST  /exercise-families : Create a new exerciseFamily.
     *
     * @param exerciseFamilyDTO the exerciseFamilyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseFamilyDTO, or with status 400 (Bad Request) if the exerciseFamily has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercise-families")
    @Timed
    public ResponseEntity<ExerciseFamilyDTO> createExerciseFamily(@Valid @RequestBody ExerciseFamilyDTO exerciseFamilyDTO) throws URISyntaxException {
        log.debug("REST request to save ExerciseFamily : {}", exerciseFamilyDTO);
        if (exerciseFamilyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new exerciseFamily cannot already have an ID")).body(null);
        }
        ExerciseFamilyDTO result = exerciseFamilyService.save(exerciseFamilyDTO);
        return ResponseEntity.created(new URI("/api/exercise-families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-families : Updates an existing exerciseFamily.
     *
     * @param exerciseFamilyDTO the exerciseFamilyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseFamilyDTO,
     * or with status 400 (Bad Request) if the exerciseFamilyDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseFamilyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercise-families")
    @Timed
    public ResponseEntity<ExerciseFamilyDTO> updateExerciseFamily(@Valid @RequestBody ExerciseFamilyDTO exerciseFamilyDTO) throws URISyntaxException {
        log.debug("REST request to update ExerciseFamily : {}", exerciseFamilyDTO);
        if (exerciseFamilyDTO.getId() == null) {
            return createExerciseFamily(exerciseFamilyDTO);
        }
        ExerciseFamilyDTO result = exerciseFamilyService.save(exerciseFamilyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exerciseFamilyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-families : get all the exerciseFamilies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseFamilies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/exercise-families")
    @Timed
    public ResponseEntity<List<ExerciseFamilyDTO>> getAllExerciseFamilies(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ExerciseFamilies");
        Page<ExerciseFamilyDTO> page = exerciseFamilyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-families");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-families/:id : get the "id" exerciseFamily.
     *
     * @param id the id of the exerciseFamilyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseFamilyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercise-families/{id}")
    @Timed
    public ResponseEntity<ExerciseFamilyDTO> getExerciseFamily(@PathVariable Long id) {
        log.debug("REST request to get ExerciseFamily : {}", id);
        ExerciseFamilyDTO exerciseFamilyDTO = exerciseFamilyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseFamilyDTO));
    }

    /**
     * DELETE  /exercise-families/:id : delete the "id" exerciseFamily.
     *
     * @param id the id of the exerciseFamilyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercise-families/{id}")
    @Timed
    public ResponseEntity<Void> deleteExerciseFamily(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseFamily : {}", id);
        exerciseFamilyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
