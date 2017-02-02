package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.service.UserWorkoutInstanceService;
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
 * REST controller for managing UserWorkoutInstance.
 */
@RestController
@RequestMapping("/api")
public class UserWorkoutInstanceResource {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutInstanceResource.class);
        
    @Inject
    private UserWorkoutInstanceService userWorkoutInstanceService;

    /**
     * POST  /user-workout-instances : Create a new userWorkoutInstance.
     *
     * @param userWorkoutInstance the userWorkoutInstance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userWorkoutInstance, or with status 400 (Bad Request) if the userWorkoutInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-workout-instances")
    @Timed
    public ResponseEntity<UserWorkoutInstance> createUserWorkoutInstance(@Valid @RequestBody UserWorkoutInstance userWorkoutInstance) throws URISyntaxException {
        log.debug("REST request to save UserWorkoutInstance : {}", userWorkoutInstance);
        if (userWorkoutInstance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userWorkoutInstance", "idexists", "A new userWorkoutInstance cannot already have an ID")).body(null);
        }
        UserWorkoutInstance result = userWorkoutInstanceService.save(userWorkoutInstance);
        return ResponseEntity.created(new URI("/api/user-workout-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userWorkoutInstance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-workout-instances : Updates an existing userWorkoutInstance.
     *
     * @param userWorkoutInstance the userWorkoutInstance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userWorkoutInstance,
     * or with status 400 (Bad Request) if the userWorkoutInstance is not valid,
     * or with status 500 (Internal Server Error) if the userWorkoutInstance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-workout-instances")
    @Timed
    public ResponseEntity<UserWorkoutInstance> updateUserWorkoutInstance(@Valid @RequestBody UserWorkoutInstance userWorkoutInstance) throws URISyntaxException {
        log.debug("REST request to update UserWorkoutInstance : {}", userWorkoutInstance);
        if (userWorkoutInstance.getId() == null) {
            return createUserWorkoutInstance(userWorkoutInstance);
        }
        UserWorkoutInstance result = userWorkoutInstanceService.save(userWorkoutInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userWorkoutInstance", userWorkoutInstance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-workout-instances : get all the userWorkoutInstances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userWorkoutInstances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-workout-instances")
    @Timed
    public ResponseEntity<List<UserWorkoutInstance>> getAllUserWorkoutInstances(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserWorkoutInstances");
        Page<UserWorkoutInstance> page = userWorkoutInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-workout-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-workout-instances/:id : get the "id" userWorkoutInstance.
     *
     * @param id the id of the userWorkoutInstance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userWorkoutInstance, or with status 404 (Not Found)
     */
    @GetMapping("/user-workout-instances/{id}")
    @Timed
    public ResponseEntity<UserWorkoutInstance> getUserWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to get UserWorkoutInstance : {}", id);
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceService.findOne(id);
        return Optional.ofNullable(userWorkoutInstance)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-workout-instances/:id : delete the "id" userWorkoutInstance.
     *
     * @param id the id of the userWorkoutInstance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-workout-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to delete UserWorkoutInstance : {}", id);
        userWorkoutInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userWorkoutInstance", id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-workout-instances?query=:query : search for the userWorkoutInstance corresponding
     * to the query.
     *
     * @param query the query of the userWorkoutInstance search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/user-workout-instances")
    @Timed
    public ResponseEntity<List<UserWorkoutInstance>> searchUserWorkoutInstances(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of UserWorkoutInstances for query {}", query);
        Page<UserWorkoutInstance> page = userWorkoutInstanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-workout-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
