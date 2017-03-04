package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.service.UserWeightService;
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
 * REST controller for managing UserWeight.
 */
@RestController
@RequestMapping("/api")
public class UserWeightResource {

    private final Logger log = LoggerFactory.getLogger(UserWeightResource.class);
        
    @Inject
    private UserWeightService userWeightService;

    /**
     * POST  /user-weights : Create a new userWeight.
     *
     * @param userWeight the userWeight to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userWeight, or with status 400 (Bad Request) if the userWeight has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-weights")
    @Timed
    public ResponseEntity<UserWeight> createUserWeight(@Valid @RequestBody UserWeight userWeight) throws URISyntaxException {
        log.debug("REST request to save UserWeight : {}", userWeight);
        if (userWeight.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userWeight", "idexists", "A new userWeight cannot already have an ID")).body(null);
        }
        UserWeight result = userWeightService.save(userWeight);
        return ResponseEntity.created(new URI("/api/user-weights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userWeight", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-weights : Updates an existing userWeight.
     *
     * @param userWeight the userWeight to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userWeight,
     * or with status 400 (Bad Request) if the userWeight is not valid,
     * or with status 500 (Internal Server Error) if the userWeight couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-weights")
    @Timed
    public ResponseEntity<UserWeight> updateUserWeight(@Valid @RequestBody UserWeight userWeight) throws URISyntaxException {
        log.debug("REST request to update UserWeight : {}", userWeight);
        if (userWeight.getId() == null) {
            return createUserWeight(userWeight);
        }
        UserWeight result = userWeightService.save(userWeight);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userWeight", userWeight.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-weights : get all the userWeights.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userWeights in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-weights")
    @Timed
    public ResponseEntity<List<UserWeight>> getAllUserWeights(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserWeights");
        Page<UserWeight> page = userWeightService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-weights");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-weights/:id : get the "id" userWeight.
     *
     * @param id the id of the userWeight to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userWeight, or with status 404 (Not Found)
     */
    @GetMapping("/user-weights/{id}")
    @Timed
    public ResponseEntity<UserWeight> getUserWeight(@PathVariable Long id) {
        log.debug("REST request to get UserWeight : {}", id);
        UserWeight userWeight = userWeightService.findOne(id);
        return Optional.ofNullable(userWeight)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-weights/:id : delete the "id" userWeight.
     *
     * @param id the id of the userWeight to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-weights/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserWeight(@PathVariable Long id) {
        log.debug("REST request to delete UserWeight : {}", id);
        userWeightService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userWeight", id.toString())).build();
    }

}
