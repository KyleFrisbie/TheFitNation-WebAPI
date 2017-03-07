package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.UserExerciseInstanceService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.UserExerciseInstanceDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserExerciseInstance.
 */
@RestController
@RequestMapping("/api")
public class UserExerciseInstanceResource {

    private final Logger log = LoggerFactory.getLogger(UserExerciseInstanceResource.class);

    private static final String ENTITY_NAME = "userExerciseInstance";
        
    private final UserExerciseInstanceService userExerciseInstanceService;

    public UserExerciseInstanceResource(UserExerciseInstanceService userExerciseInstanceService) {
        this.userExerciseInstanceService = userExerciseInstanceService;
    }

    /**
     * POST  /user-exercise-instances : Create a new userExerciseInstance.
     *
     * @param userExerciseInstanceDTO the userExerciseInstanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExerciseInstanceDTO, or with status 400 (Bad Request) if the userExerciseInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-exercise-instances")
    @Timed
    public ResponseEntity<UserExerciseInstanceDTO> createUserExerciseInstance(@Valid @RequestBody UserExerciseInstanceDTO userExerciseInstanceDTO) throws URISyntaxException {
        log.debug("REST request to save UserExerciseInstance : {}", userExerciseInstanceDTO);
        if (userExerciseInstanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userExerciseInstance cannot already have an ID")).body(null);
        }
        UserExerciseInstanceDTO result = userExerciseInstanceService.save(userExerciseInstanceDTO);
        return ResponseEntity.created(new URI("/api/user-exercise-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-exercise-instances : Updates an existing userExerciseInstance.
     *
     * @param userExerciseInstanceDTO the userExerciseInstanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExerciseInstanceDTO,
     * or with status 400 (Bad Request) if the userExerciseInstanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the userExerciseInstanceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-exercise-instances")
    @Timed
    public ResponseEntity<UserExerciseInstanceDTO> updateUserExerciseInstance(@Valid @RequestBody UserExerciseInstanceDTO userExerciseInstanceDTO) throws URISyntaxException {
        log.debug("REST request to update UserExerciseInstance : {}", userExerciseInstanceDTO);
        if (userExerciseInstanceDTO.getId() == null) {
            return createUserExerciseInstance(userExerciseInstanceDTO);
        }
        UserExerciseInstanceDTO result = userExerciseInstanceService.save(userExerciseInstanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userExerciseInstanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-exercise-instances : get all the userExerciseInstances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userExerciseInstances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-exercise-instances")
    @Timed
    public ResponseEntity<List<UserExerciseInstanceDTO>> getAllUserExerciseInstances(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserExerciseInstances");
        Page<UserExerciseInstanceDTO> page = userExerciseInstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-exercise-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-exercise-instances/:id : get the "id" userExerciseInstance.
     *
     * @param id the id of the userExerciseInstanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExerciseInstanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-exercise-instances/{id}")
    @Timed
    public ResponseEntity<UserExerciseInstanceDTO> getUserExerciseInstance(@PathVariable Long id) {
        log.debug("REST request to get UserExerciseInstance : {}", id);
        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExerciseInstanceDTO));
    }

    /**
     * DELETE  /user-exercise-instances/:id : delete the "id" userExerciseInstance.
     *
     * @param id the id of the userExerciseInstanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-exercise-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExerciseInstance(@PathVariable Long id) {
        log.debug("REST request to delete UserExerciseInstance : {}", id);
        userExerciseInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
