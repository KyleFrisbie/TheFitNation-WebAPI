package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.UserExercise;
import com.thefitnation.service.UserExerciseService;
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
 * REST controller for managing UserExercise.
 */
@RestController
@RequestMapping("/api")
public class UserExerciseResource {

    private final Logger log = LoggerFactory.getLogger(UserExerciseResource.class);
        
    @Inject
    private UserExerciseService userExerciseService;

    /**
     * POST  /user-exercises : Create a new userExercise.
     *
     * @param userExercise the userExercise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExercise, or with status 400 (Bad Request) if the userExercise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-exercises")
    @Timed
    public ResponseEntity<UserExercise> createUserExercise(@Valid @RequestBody UserExercise userExercise) throws URISyntaxException {
        log.debug("REST request to save UserExercise : {}", userExercise);
        if (userExercise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userExercise", "idexists", "A new userExercise cannot already have an ID")).body(null);
        }
        UserExercise result = userExerciseService.save(userExercise);
        return ResponseEntity.created(new URI("/api/user-exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userExercise", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-exercises : Updates an existing userExercise.
     *
     * @param userExercise the userExercise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExercise,
     * or with status 400 (Bad Request) if the userExercise is not valid,
     * or with status 500 (Internal Server Error) if the userExercise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-exercises")
    @Timed
    public ResponseEntity<UserExercise> updateUserExercise(@Valid @RequestBody UserExercise userExercise) throws URISyntaxException {
        log.debug("REST request to update UserExercise : {}", userExercise);
        if (userExercise.getId() == null) {
            return createUserExercise(userExercise);
        }
        UserExercise result = userExerciseService.save(userExercise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userExercise", userExercise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-exercises : get all the userExercises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userExercises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-exercises")
    @Timed
    public ResponseEntity<List<UserExercise>> getAllUserExercises(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserExercises");
        Page<UserExercise> page = userExerciseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-exercises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-exercises/:id : get the "id" userExercise.
     *
     * @param id the id of the userExercise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExercise, or with status 404 (Not Found)
     */
    @GetMapping("/user-exercises/{id}")
    @Timed
    public ResponseEntity<UserExercise> getUserExercise(@PathVariable Long id) {
        log.debug("REST request to get UserExercise : {}", id);
        UserExercise userExercise = userExerciseService.findOne(id);
        return Optional.ofNullable(userExercise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-exercises/:id : delete the "id" userExercise.
     *
     * @param id the id of the userExercise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-exercises/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExercise(@PathVariable Long id) {
        log.debug("REST request to delete UserExercise : {}", id);
        userExerciseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userExercise", id.toString())).build();
    }

}
