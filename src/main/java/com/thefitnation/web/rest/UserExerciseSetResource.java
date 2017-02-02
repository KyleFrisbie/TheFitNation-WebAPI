package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.UserExerciseSet;
import com.thefitnation.service.UserExerciseSetService;
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
 * REST controller for managing UserExerciseSet.
 */
@RestController
@RequestMapping("/api")
public class UserExerciseSetResource {

    private final Logger log = LoggerFactory.getLogger(UserExerciseSetResource.class);
        
    @Inject
    private UserExerciseSetService userExerciseSetService;

    /**
     * POST  /user-exercise-sets : Create a new userExerciseSet.
     *
     * @param userExerciseSet the userExerciseSet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExerciseSet, or with status 400 (Bad Request) if the userExerciseSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-exercise-sets")
    @Timed
    public ResponseEntity<UserExerciseSet> createUserExerciseSet(@Valid @RequestBody UserExerciseSet userExerciseSet) throws URISyntaxException {
        log.debug("REST request to save UserExerciseSet : {}", userExerciseSet);
        if (userExerciseSet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userExerciseSet", "idexists", "A new userExerciseSet cannot already have an ID")).body(null);
        }
        UserExerciseSet result = userExerciseSetService.save(userExerciseSet);
        return ResponseEntity.created(new URI("/api/user-exercise-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userExerciseSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-exercise-sets : Updates an existing userExerciseSet.
     *
     * @param userExerciseSet the userExerciseSet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExerciseSet,
     * or with status 400 (Bad Request) if the userExerciseSet is not valid,
     * or with status 500 (Internal Server Error) if the userExerciseSet couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-exercise-sets")
    @Timed
    public ResponseEntity<UserExerciseSet> updateUserExerciseSet(@Valid @RequestBody UserExerciseSet userExerciseSet) throws URISyntaxException {
        log.debug("REST request to update UserExerciseSet : {}", userExerciseSet);
        if (userExerciseSet.getId() == null) {
            return createUserExerciseSet(userExerciseSet);
        }
        UserExerciseSet result = userExerciseSetService.save(userExerciseSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userExerciseSet", userExerciseSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-exercise-sets : get all the userExerciseSets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userExerciseSets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-exercise-sets")
    @Timed
    public ResponseEntity<List<UserExerciseSet>> getAllUserExerciseSets(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserExerciseSets");
        Page<UserExerciseSet> page = userExerciseSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-exercise-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-exercise-sets/:id : get the "id" userExerciseSet.
     *
     * @param id the id of the userExerciseSet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExerciseSet, or with status 404 (Not Found)
     */
    @GetMapping("/user-exercise-sets/{id}")
    @Timed
    public ResponseEntity<UserExerciseSet> getUserExerciseSet(@PathVariable Long id) {
        log.debug("REST request to get UserExerciseSet : {}", id);
        UserExerciseSet userExerciseSet = userExerciseSetService.findOne(id);
        return Optional.ofNullable(userExerciseSet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-exercise-sets/:id : delete the "id" userExerciseSet.
     *
     * @param id the id of the userExerciseSet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-exercise-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExerciseSet(@PathVariable Long id) {
        log.debug("REST request to delete UserExerciseSet : {}", id);
        userExerciseSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userExerciseSet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-exercise-sets?query=:query : search for the userExerciseSet corresponding
     * to the query.
     *
     * @param query the query of the userExerciseSet search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/user-exercise-sets")
    @Timed
    public ResponseEntity<List<UserExerciseSet>> searchUserExerciseSets(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of UserExerciseSets for query {}", query);
        Page<UserExerciseSet> page = userExerciseSetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-exercise-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
