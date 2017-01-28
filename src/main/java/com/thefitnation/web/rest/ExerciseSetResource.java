package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.ExerciseSet;

import com.thefitnation.repository.ExerciseSetRepository;
import com.thefitnation.repository.search.ExerciseSetSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ExerciseSet.
 */
@RestController
@RequestMapping("/api")
public class ExerciseSetResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseSetResource.class);
        
    @Inject
    private ExerciseSetRepository exerciseSetRepository;

    @Inject
    private ExerciseSetSearchRepository exerciseSetSearchRepository;

    /**
     * POST  /exercise-sets : Create a new exerciseSet.
     *
     * @param exerciseSet the exerciseSet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseSet, or with status 400 (Bad Request) if the exerciseSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercise-sets")
    @Timed
    public ResponseEntity<ExerciseSet> createExerciseSet(@Valid @RequestBody ExerciseSet exerciseSet) throws URISyntaxException {
        log.debug("REST request to save ExerciseSet : {}", exerciseSet);
        if (exerciseSet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("exerciseSet", "idexists", "A new exerciseSet cannot already have an ID")).body(null);
        }
        ExerciseSet result = exerciseSetRepository.save(exerciseSet);
        exerciseSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/exercise-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("exerciseSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-sets : Updates an existing exerciseSet.
     *
     * @param exerciseSet the exerciseSet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseSet,
     * or with status 400 (Bad Request) if the exerciseSet is not valid,
     * or with status 500 (Internal Server Error) if the exerciseSet couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercise-sets")
    @Timed
    public ResponseEntity<ExerciseSet> updateExerciseSet(@Valid @RequestBody ExerciseSet exerciseSet) throws URISyntaxException {
        log.debug("REST request to update ExerciseSet : {}", exerciseSet);
        if (exerciseSet.getId() == null) {
            return createExerciseSet(exerciseSet);
        }
        ExerciseSet result = exerciseSetRepository.save(exerciseSet);
        exerciseSetSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("exerciseSet", exerciseSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-sets : get all the exerciseSets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseSets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/exercise-sets")
    @Timed
    public ResponseEntity<List<ExerciseSet>> getAllExerciseSets(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ExerciseSets");
        Page<ExerciseSet> page = exerciseSetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-sets/:id : get the "id" exerciseSet.
     *
     * @param id the id of the exerciseSet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseSet, or with status 404 (Not Found)
     */
    @GetMapping("/exercise-sets/{id}")
    @Timed
    public ResponseEntity<ExerciseSet> getExerciseSet(@PathVariable Long id) {
        log.debug("REST request to get ExerciseSet : {}", id);
        ExerciseSet exerciseSet = exerciseSetRepository.findOne(id);
        return Optional.ofNullable(exerciseSet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /exercise-sets/:id : delete the "id" exerciseSet.
     *
     * @param id the id of the exerciseSet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercise-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteExerciseSet(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseSet : {}", id);
        exerciseSetRepository.delete(id);
        exerciseSetSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("exerciseSet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/exercise-sets?query=:query : search for the exerciseSet corresponding
     * to the query.
     *
     * @param query the query of the exerciseSet search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/exercise-sets")
    @Timed
    public ResponseEntity<List<ExerciseSet>> searchExerciseSets(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ExerciseSets for query {}", query);
        Page<ExerciseSet> page = exerciseSetSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/exercise-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
