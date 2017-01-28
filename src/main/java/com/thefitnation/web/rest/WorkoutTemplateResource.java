package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.service.WorkoutTemplateService;
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
 * REST controller for managing WorkoutTemplate.
 */
@RestController
@RequestMapping("/api")
public class WorkoutTemplateResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutTemplateResource.class);
        
    @Inject
    private WorkoutTemplateService workoutTemplateService;

    /**
     * POST  /workout-templates : Create a new workoutTemplate.
     *
     * @param workoutTemplate the workoutTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workoutTemplate, or with status 400 (Bad Request) if the workoutTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workout-templates")
    @Timed
    public ResponseEntity<WorkoutTemplate> createWorkoutTemplate(@Valid @RequestBody WorkoutTemplate workoutTemplate) throws URISyntaxException {
        log.debug("REST request to save WorkoutTemplate : {}", workoutTemplate);
        if (workoutTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workoutTemplate", "idexists", "A new workoutTemplate cannot already have an ID")).body(null);
        }
        WorkoutTemplate result = workoutTemplateService.save(workoutTemplate);
        return ResponseEntity.created(new URI("/api/workout-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workoutTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workout-templates : Updates an existing workoutTemplate.
     *
     * @param workoutTemplate the workoutTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workoutTemplate,
     * or with status 400 (Bad Request) if the workoutTemplate is not valid,
     * or with status 500 (Internal Server Error) if the workoutTemplate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workout-templates")
    @Timed
    public ResponseEntity<WorkoutTemplate> updateWorkoutTemplate(@Valid @RequestBody WorkoutTemplate workoutTemplate) throws URISyntaxException {
        log.debug("REST request to update WorkoutTemplate : {}", workoutTemplate);
        if (workoutTemplate.getId() == null) {
            return createWorkoutTemplate(workoutTemplate);
        }
        WorkoutTemplate result = workoutTemplateService.save(workoutTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workoutTemplate", workoutTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workout-templates : get all the workoutTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workoutTemplates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/workout-templates")
    @Timed
    public ResponseEntity<List<WorkoutTemplate>> getAllWorkoutTemplates(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WorkoutTemplates");
        Page<WorkoutTemplate> page = workoutTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workout-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workout-templates/:id : get the "id" workoutTemplate.
     *
     * @param id the id of the workoutTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workoutTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/workout-templates/{id}")
    @Timed
    public ResponseEntity<WorkoutTemplate> getWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to get WorkoutTemplate : {}", id);
        WorkoutTemplate workoutTemplate = workoutTemplateService.findOne(id);
        return Optional.ofNullable(workoutTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /workout-templates/:id : delete the "id" workoutTemplate.
     *
     * @param id the id of the workoutTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workout-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutTemplate : {}", id);
        workoutTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workoutTemplate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/workout-templates?query=:query : search for the workoutTemplate corresponding
     * to the query.
     *
     * @param query the query of the workoutTemplate search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/workout-templates")
    @Timed
    public ResponseEntity<List<WorkoutTemplate>> searchWorkoutTemplates(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of WorkoutTemplates for query {}", query);
        Page<WorkoutTemplate> page = workoutTemplateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/workout-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
