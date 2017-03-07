package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.UserExerciseInstanceSetService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
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
 * REST controller for managing UserExerciseInstanceSet.
 */
@RestController
@RequestMapping("/api")
public class UserExerciseInstanceSetResource {

    private final Logger log = LoggerFactory.getLogger(UserExerciseInstanceSetResource.class);

    private static final String ENTITY_NAME = "userExerciseInstanceSet";
        
    private final UserExerciseInstanceSetService userExerciseInstanceSetService;

    public UserExerciseInstanceSetResource(UserExerciseInstanceSetService userExerciseInstanceSetService) {
        this.userExerciseInstanceSetService = userExerciseInstanceSetService;
    }

    /**
     * POST  /user-exercise-instance-sets : Create a new userExerciseInstanceSet.
     *
     * @param userExerciseInstanceSetDTO the userExerciseInstanceSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExerciseInstanceSetDTO, or with status 400 (Bad Request) if the userExerciseInstanceSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-exercise-instance-sets")
    @Timed
    public ResponseEntity<UserExerciseInstanceSetDTO> createUserExerciseInstanceSet(@Valid @RequestBody UserExerciseInstanceSetDTO userExerciseInstanceSetDTO) throws URISyntaxException {
        log.debug("REST request to save UserExerciseInstanceSet : {}", userExerciseInstanceSetDTO);
        if (userExerciseInstanceSetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userExerciseInstanceSet cannot already have an ID")).body(null);
        }
        UserExerciseInstanceSetDTO result = userExerciseInstanceSetService.save(userExerciseInstanceSetDTO);
        return ResponseEntity.created(new URI("/api/user-exercise-instance-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-exercise-instance-sets : Updates an existing userExerciseInstanceSet.
     *
     * @param userExerciseInstanceSetDTO the userExerciseInstanceSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExerciseInstanceSetDTO,
     * or with status 400 (Bad Request) if the userExerciseInstanceSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the userExerciseInstanceSetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-exercise-instance-sets")
    @Timed
    public ResponseEntity<UserExerciseInstanceSetDTO> updateUserExerciseInstanceSet(@Valid @RequestBody UserExerciseInstanceSetDTO userExerciseInstanceSetDTO) throws URISyntaxException {
        log.debug("REST request to update UserExerciseInstanceSet : {}", userExerciseInstanceSetDTO);
        if (userExerciseInstanceSetDTO.getId() == null) {
            return createUserExerciseInstanceSet(userExerciseInstanceSetDTO);
        }
        UserExerciseInstanceSetDTO result = userExerciseInstanceSetService.save(userExerciseInstanceSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userExerciseInstanceSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-exercise-instance-sets : get all the userExerciseInstanceSets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userExerciseInstanceSets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-exercise-instance-sets")
    @Timed
    public ResponseEntity<List<UserExerciseInstanceSetDTO>> getAllUserExerciseInstanceSets(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserExerciseInstanceSets");
        Page<UserExerciseInstanceSetDTO> page = userExerciseInstanceSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-exercise-instance-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-exercise-instance-sets/:id : get the "id" userExerciseInstanceSet.
     *
     * @param id the id of the userExerciseInstanceSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExerciseInstanceSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-exercise-instance-sets/{id}")
    @Timed
    public ResponseEntity<UserExerciseInstanceSetDTO> getUserExerciseInstanceSet(@PathVariable Long id) {
        log.debug("REST request to get UserExerciseInstanceSet : {}", id);
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExerciseInstanceSetDTO));
    }

    /**
     * DELETE  /user-exercise-instance-sets/:id : delete the "id" userExerciseInstanceSet.
     *
     * @param id the id of the userExerciseInstanceSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-exercise-instance-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExerciseInstanceSet(@PathVariable Long id) {
        log.debug("REST request to delete UserExerciseInstanceSet : {}", id);
        userExerciseInstanceSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
