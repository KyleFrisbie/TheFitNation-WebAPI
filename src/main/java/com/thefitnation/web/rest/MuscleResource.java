package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.Muscle;
import com.thefitnation.service.MuscleService;
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
 * REST controller for managing Muscle.
 */
@RestController
@RequestMapping("/api")
public class MuscleResource {

    private final Logger log = LoggerFactory.getLogger(MuscleResource.class);
        
    @Inject
    private MuscleService muscleService;

    /**
     * POST  /muscles : Create a new muscle.
     *
     * @param muscle the muscle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new muscle, or with status 400 (Bad Request) if the muscle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/muscles")
    @Timed
    public ResponseEntity<Muscle> createMuscle(@Valid @RequestBody Muscle muscle) throws URISyntaxException {
        log.debug("REST request to save Muscle : {}", muscle);
        if (muscle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("muscle", "idexists", "A new muscle cannot already have an ID")).body(null);
        }
        Muscle result = muscleService.save(muscle);
        return ResponseEntity.created(new URI("/api/muscles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("muscle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /muscles : Updates an existing muscle.
     *
     * @param muscle the muscle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated muscle,
     * or with status 400 (Bad Request) if the muscle is not valid,
     * or with status 500 (Internal Server Error) if the muscle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/muscles")
    @Timed
    public ResponseEntity<Muscle> updateMuscle(@Valid @RequestBody Muscle muscle) throws URISyntaxException {
        log.debug("REST request to update Muscle : {}", muscle);
        if (muscle.getId() == null) {
            return createMuscle(muscle);
        }
        Muscle result = muscleService.save(muscle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("muscle", muscle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /muscles : get all the muscles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of muscles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/muscles")
    @Timed
    public ResponseEntity<List<Muscle>> getAllMuscles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Muscles");
        Page<Muscle> page = muscleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/muscles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /muscles/:id : get the "id" muscle.
     *
     * @param id the id of the muscle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the muscle, or with status 404 (Not Found)
     */
    @GetMapping("/muscles/{id}")
    @Timed
    public ResponseEntity<Muscle> getMuscle(@PathVariable Long id) {
        log.debug("REST request to get Muscle : {}", id);
        Muscle muscle = muscleService.findOne(id);
        return Optional.ofNullable(muscle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /muscles/:id : delete the "id" muscle.
     *
     * @param id the id of the muscle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/muscles/{id}")
    @Timed
    public ResponseEntity<Void> deleteMuscle(@PathVariable Long id) {
        log.debug("REST request to delete Muscle : {}", id);
        muscleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("muscle", id.toString())).build();
    }

}
