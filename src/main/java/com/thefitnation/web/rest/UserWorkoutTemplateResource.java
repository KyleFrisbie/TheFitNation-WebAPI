package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.service.UserWorkoutTemplateService;
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
 * REST controller for managing UserWorkoutTemplate.
 */
@RestController
@RequestMapping("/api")
public class UserWorkoutTemplateResource {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutTemplateResource.class);
        
    @Inject
    private UserWorkoutTemplateService userWorkoutTemplateService;

    /**
     * POST  /user-workout-templates : Create a new userWorkoutTemplate.
     *
     * @param userWorkoutTemplate the userWorkoutTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userWorkoutTemplate, or with status 400 (Bad Request) if the userWorkoutTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-workout-templates")
    @Timed
    public ResponseEntity<UserWorkoutTemplate> createUserWorkoutTemplate(@Valid @RequestBody UserWorkoutTemplate userWorkoutTemplate) throws URISyntaxException {
        log.debug("REST request to save UserWorkoutTemplate : {}", userWorkoutTemplate);
        if (userWorkoutTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userWorkoutTemplate", "idexists", "A new userWorkoutTemplate cannot already have an ID")).body(null);
        }
        UserWorkoutTemplate result = userWorkoutTemplateService.save(userWorkoutTemplate);
        return ResponseEntity.created(new URI("/api/user-workout-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userWorkoutTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-workout-templates : Updates an existing userWorkoutTemplate.
     *
     * @param userWorkoutTemplate the userWorkoutTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userWorkoutTemplate,
     * or with status 400 (Bad Request) if the userWorkoutTemplate is not valid,
     * or with status 500 (Internal Server Error) if the userWorkoutTemplate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-workout-templates")
    @Timed
    public ResponseEntity<UserWorkoutTemplate> updateUserWorkoutTemplate(@Valid @RequestBody UserWorkoutTemplate userWorkoutTemplate) throws URISyntaxException {
        log.debug("REST request to update UserWorkoutTemplate : {}", userWorkoutTemplate);
        if (userWorkoutTemplate.getId() == null) {
            return createUserWorkoutTemplate(userWorkoutTemplate);
        }
        UserWorkoutTemplate result = userWorkoutTemplateService.save(userWorkoutTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userWorkoutTemplate", userWorkoutTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-workout-templates : get all the userWorkoutTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userWorkoutTemplates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-workout-templates")
    @Timed
    public ResponseEntity<List<UserWorkoutTemplate>> getAllUserWorkoutTemplates(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserWorkoutTemplates");
        Page<UserWorkoutTemplate> page = userWorkoutTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-workout-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-workout-templates/:id : get the "id" userWorkoutTemplate.
     *
     * @param id the id of the userWorkoutTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userWorkoutTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/user-workout-templates/{id}")
    @Timed
    public ResponseEntity<UserWorkoutTemplate> getUserWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to get UserWorkoutTemplate : {}", id);
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateService.findOne(id);
        return Optional.ofNullable(userWorkoutTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-workout-templates/:id : delete the "id" userWorkoutTemplate.
     *
     * @param id the id of the userWorkoutTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-workout-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to delete UserWorkoutTemplate : {}", id);
        userWorkoutTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userWorkoutTemplate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-workout-templates?query=:query : search for the userWorkoutTemplate corresponding
     * to the query.
     *
     * @param query the query of the userWorkoutTemplate search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/user-workout-templates")
    @Timed
    public ResponseEntity<List<UserWorkoutTemplate>> searchUserWorkoutTemplates(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of UserWorkoutTemplates for query {}", query);
        Page<UserWorkoutTemplate> page = userWorkoutTemplateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-workout-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
