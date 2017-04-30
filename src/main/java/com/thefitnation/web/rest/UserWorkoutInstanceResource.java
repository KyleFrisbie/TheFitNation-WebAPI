package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.*;
import com.thefitnation.service.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.web.rest.util.*;
import io.github.jhipster.web.util.*;
import io.swagger.annotations.*;
import java.net.*;
import java.util.*;
import javax.validation.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing UserWorkoutInstance.
 */
@RestController
@RequestMapping("/api")
public class UserWorkoutInstanceResource {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutInstanceResource.class);

    private static final String ENTITY_NAME = "userWorkoutInstance";

    private final UserWorkoutInstanceService userWorkoutInstanceService;

    public UserWorkoutInstanceResource(UserWorkoutInstanceService userWorkoutInstanceService) {
        this.userWorkoutInstanceService = userWorkoutInstanceService;
    }

    /**
     * POST  /user-workout-instances : Create a new userWorkoutInstance.
     *
     * @param userWorkoutInstanceDTO the userWorkoutInstanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userWorkoutInstanceDTO, or with status 400 (Bad Request) if the userWorkoutInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-workout-instances")
    @Timed
    public ResponseEntity<UserWorkoutInstanceDTO> createUserWorkoutInstance(@Valid @RequestBody UserWorkoutInstanceDTO userWorkoutInstanceDTO) throws URISyntaxException {
        log.debug("REST request to save UserWorkoutInstance : {}", userWorkoutInstanceDTO);
        if (userWorkoutInstanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userWorkoutInstance cannot already have an ID")).body(null);
        }
        UserWorkoutInstanceDTO result = userWorkoutInstanceService.save(userWorkoutInstanceDTO);
        return ResponseEntity.created(new URI("/api/user-workout-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-workout-instances : Updates an existing userWorkoutInstance.
     *
     * @param userWorkoutInstanceDTO the userWorkoutInstanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userWorkoutInstanceDTO,
     * or with status 400 (Bad Request) if the userWorkoutInstanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the userWorkoutInstanceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-workout-instances")
    @Timed
    public ResponseEntity<UserWorkoutInstanceDTO> updateUserWorkoutInstance(@Valid @RequestBody UserWorkoutInstanceDTO userWorkoutInstanceDTO) throws URISyntaxException {
        log.debug("REST request to update UserWorkoutInstance : {}", userWorkoutInstanceDTO);
        if (userWorkoutInstanceDTO.getId() == null) {
            return createUserWorkoutInstance(userWorkoutInstanceDTO);
        }
        UserWorkoutInstanceDTO result = userWorkoutInstanceService.save(userWorkoutInstanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
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
    public ResponseEntity<List<UserWorkoutInstanceDTO>> getAllUserWorkoutInstances(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserWorkoutInstances");
        Page<UserWorkoutInstanceDTO> page = userWorkoutInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-workout-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /user-workout-instances : get all the userWorkoutInstances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userWorkoutInstances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("user/user-workout-instances")
    @Timed
    public ResponseEntity<List<UserWorkoutInstanceDTO>> getAllUserWorkoutInstancesByCurrUSer(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserWorkoutInstances");
        Page<UserWorkoutInstanceDTO> page = userWorkoutInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-workout-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-workout-instances/:id : get the "id" userWorkoutInstance.
     *
     * @param id the id of the userWorkoutInstanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userWorkoutInstanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-workout-instances/{id}")
    @Timed
    public ResponseEntity<UserWorkoutInstanceDTO> getUserWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to get UserWorkoutInstance : {}", id);
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userWorkoutInstanceDTO));
    }

    /**
     * DELETE  /user-workout-instances/:id : delete the "id" userWorkoutInstance.
     *
     * @param id the id of the userWorkoutInstanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-workout-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserWorkoutInstance(@PathVariable Long id) {
        log.debug("REST request to delete UserWorkoutInstance : {}", id);
        userWorkoutInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
