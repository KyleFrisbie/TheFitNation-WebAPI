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
 * REST controller for managing Muscle.
 */
@RestController
@RequestMapping("/api")
public class MuscleResource {

    private final Logger log = LoggerFactory.getLogger(MuscleResource.class);

    private static final String ENTITY_NAME = "muscle";

    private final MuscleService muscleService;

    public MuscleResource(MuscleService muscleService) {
        this.muscleService = muscleService;
    }

    /**
     * POST  /muscles : Create a new muscle.
     *
     * @param muscleDTO the muscleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new muscleDTO, or with status 400 (Bad Request) if the muscle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/muscles")
    @Timed
    public ResponseEntity<MuscleDTO> createMuscle(@Valid @RequestBody MuscleDTO muscleDTO) throws URISyntaxException {
        log.debug("REST request to save Muscle : {}", muscleDTO);
        if (muscleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new muscle cannot already have an ID")).body(null);
        }
        MuscleDTO result = muscleService.save(muscleDTO);
        return ResponseEntity.created(new URI("/api/muscles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /muscles : Updates an existing muscle.
     *
     * @param muscleDTO the muscleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated muscleDTO,
     * or with status 400 (Bad Request) if the muscleDTO is not valid,
     * or with status 500 (Internal Server Error) if the muscleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/muscles")
    @Timed
    public ResponseEntity<MuscleDTO> updateMuscle(@Valid @RequestBody MuscleDTO muscleDTO) throws URISyntaxException {
        log.debug("REST request to update Muscle : {}", muscleDTO);
        if (muscleDTO.getId() == null) {
            return createMuscle(muscleDTO);
        }
        MuscleDTO result = muscleService.save(muscleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, muscleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /muscles : get all the muscles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of muscles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/muscles")
    @Timed
    public ResponseEntity<List<MuscleDTO>> getAllMuscles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Muscles");
        Page<MuscleDTO> page = muscleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/muscles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /muscles/:id : get the "id" muscle.
     *
     * @param id the id of the muscleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the muscleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/muscles/{id}")
    @Timed
    public ResponseEntity<MuscleDTO> getMuscle(@PathVariable Long id) {
        log.debug("REST request to get Muscle : {}", id);
        MuscleDTO muscleDTO = muscleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(muscleDTO));
    }

    /**
     * GET  /muscles/:id : get the "id" muscle.
     *
     * @param muscleName the id of the muscleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the muscleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/muscles/name/{muscleName}")
    @Timed
    public ResponseEntity<MuscleDTO> getMuscle(@PathVariable String muscleName) {
        log.debug("REST request to get Muscle : {}", muscleName);
        MuscleDTO muscleDTO = muscleService.findOne(muscleName);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(muscleDTO));
    }

    /**
     * DELETE  /muscles/:id : delete the "id" muscle.
     *
     * @param id the id of the muscleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/muscles/{id}")
    @Timed
    public ResponseEntity<Void> deleteMuscle(@PathVariable Long id) {
        log.debug("REST request to delete Muscle : {}", id);
        muscleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
