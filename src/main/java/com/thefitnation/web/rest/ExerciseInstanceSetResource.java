package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.*;
import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.security.*;
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
 * REST controller for managing ExerciseInstanceSet.
 */
@RestController
@RequestMapping("/api")
public class ExerciseInstanceSetResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceSetResource.class);

    private static final String ENTITY_NAME = "exerciseInstanceSet";

    private final ExerciseInstanceSetService exerciseInstanceSetService;
    private final UserRepository userRepository;

    public ExerciseInstanceSetResource(ExerciseInstanceSetService exerciseInstanceSetService, UserRepository userRepository) {
        this.exerciseInstanceSetService = exerciseInstanceSetService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /exercise-instance-sets : Create a new exerciseInstanceSet.
     *
     * @param exerciseInstanceSetDTO the exerciseInstanceSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseInstanceSetDTO, or with status 400 (Bad Request) if the exerciseInstanceSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercise-instance-sets")
    @Timed
    public ResponseEntity<ExerciseInstanceSetDTO> createExerciseInstanceSet(@Valid @RequestBody ExerciseInstanceSetDTO exerciseInstanceSetDTO) throws URISyntaxException {
        log.debug("REST request to save ExerciseInstanceSet : {}", exerciseInstanceSetDTO);
        if (exerciseInstanceSetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new exerciseInstanceSet cannot already have an ID")).body(null);
        }
        ExerciseInstanceSetDTO result = exerciseInstanceSetService.save(exerciseInstanceSetDTO);
        return ResponseEntity.created(new URI("/api/exercise-instance-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-instance-sets : Updates an existing exerciseInstanceSet.
     *
     * @param exerciseInstanceSetDTO the exerciseInstanceSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseInstanceSetDTO,
     * or with status 400 (Bad Request) if the exerciseInstanceSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseInstanceSetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercise-instance-sets")
    @Timed
    public ResponseEntity<ExerciseInstanceSetDTO> updateExerciseInstanceSet(@Valid @RequestBody ExerciseInstanceSetDTO exerciseInstanceSetDTO) throws URISyntaxException {
        log.debug("REST request to update ExerciseInstanceSet : {}", exerciseInstanceSetDTO);
        if (exerciseInstanceSetDTO.getId() == null) {
            return createExerciseInstanceSet(exerciseInstanceSetDTO);
        }
        ExerciseInstanceSetDTO result = exerciseInstanceSetService.save(exerciseInstanceSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-instance-sets : get all the exerciseInstanceSets by current logged in user.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseInstanceSets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/exercise-instance-sets")
    @Timed
    public ResponseEntity<List<ExerciseInstanceSetDTO>> getAllExerciseInstanceSetsByCurrUSer(@ApiParam Pageable pageable)
        throws URISyntaxException {

        log.debug("REST request to get a page of ExerciseInstanceSets by current logged in user.");

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if(user.isPresent()) {
            Page<ExerciseInstanceSetDTO> page = exerciseInstanceSetService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/exercise-instance-sets");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser", "Unable to find User by token"))
                .body(null);
        }
    }

    /**
     * GET  /exercise-instance-sets/:id : get the "id" exerciseInstanceSet.
     *
     * @param id the id of the exerciseInstanceSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseInstanceSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercise-instance-sets/{id}")
    @Timed
    public ResponseEntity<ExerciseInstanceSetDTO> getExerciseInstanceSet(@PathVariable Long id) {
        log.debug("REST request to get ExerciseInstanceSet : {}", id);
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseInstanceSetDTO));
    }

    /**
     * DELETE  /exercise-instance-sets/:id : delete the "id" exerciseInstanceSet.
     *
     * @param id the id of the exerciseInstanceSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercise-instance-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteExerciseInstanceSet(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseInstanceSet : {}", id);
        exerciseInstanceSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
