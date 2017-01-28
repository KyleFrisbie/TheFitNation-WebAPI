package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.service.UserDemographicService;
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
 * REST controller for managing UserDemographic.
 */
@RestController
@RequestMapping("/api")
public class UserDemographicResource {

    private final Logger log = LoggerFactory.getLogger(UserDemographicResource.class);
        
    @Inject
    private UserDemographicService userDemographicService;

    /**
     * POST  /user-demographics : Create a new userDemographic.
     *
     * @param userDemographic the userDemographic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDemographic, or with status 400 (Bad Request) if the userDemographic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-demographics")
    @Timed
    public ResponseEntity<UserDemographic> createUserDemographic(@Valid @RequestBody UserDemographic userDemographic) throws URISyntaxException {
        log.debug("REST request to save UserDemographic : {}", userDemographic);
        if (userDemographic.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userDemographic", "idexists", "A new userDemographic cannot already have an ID")).body(null);
        }
        UserDemographic result = userDemographicService.save(userDemographic);
        return ResponseEntity.created(new URI("/api/user-demographics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userDemographic", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-demographics : Updates an existing userDemographic.
     *
     * @param userDemographic the userDemographic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDemographic,
     * or with status 400 (Bad Request) if the userDemographic is not valid,
     * or with status 500 (Internal Server Error) if the userDemographic couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-demographics")
    @Timed
    public ResponseEntity<UserDemographic> updateUserDemographic(@Valid @RequestBody UserDemographic userDemographic) throws URISyntaxException {
        log.debug("REST request to update UserDemographic : {}", userDemographic);
        if (userDemographic.getId() == null) {
            return createUserDemographic(userDemographic);
        }
        UserDemographic result = userDemographicService.save(userDemographic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userDemographic", userDemographic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-demographics : get all the userDemographics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userDemographics in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-demographics")
    @Timed
    public ResponseEntity<List<UserDemographic>> getAllUserDemographics(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserDemographics");
        Page<UserDemographic> page = userDemographicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-demographics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-demographics/:id : get the "id" userDemographic.
     *
     * @param id the id of the userDemographic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDemographic, or with status 404 (Not Found)
     */
    @GetMapping("/user-demographics/{id}")
    @Timed
    public ResponseEntity<UserDemographic> getUserDemographic(@PathVariable Long id) {
        log.debug("REST request to get UserDemographic : {}", id);
        UserDemographic userDemographic = userDemographicService.findOne(id);
        return Optional.ofNullable(userDemographic)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-demographics/:id : delete the "id" userDemographic.
     *
     * @param id the id of the userDemographic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-demographics/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDemographic(@PathVariable Long id) {
        log.debug("REST request to delete UserDemographic : {}", id);
        userDemographicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userDemographic", id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-demographics?query=:query : search for the userDemographic corresponding
     * to the query.
     *
     * @param query the query of the userDemographic search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/user-demographics")
    @Timed
    public ResponseEntity<List<UserDemographic>> searchUserDemographics(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of UserDemographics for query {}", query);
        Page<UserDemographic> page = userDemographicService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-demographics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
