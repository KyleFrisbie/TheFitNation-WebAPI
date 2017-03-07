package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.GymService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.GymDTO;
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
 * REST controller for managing Gym.
 */
@RestController
@RequestMapping("/api")
public class GymResource {

    private final Logger log = LoggerFactory.getLogger(GymResource.class);

    private static final String ENTITY_NAME = "gym";
        
    private final GymService gymService;

    public GymResource(GymService gymService) {
        this.gymService = gymService;
    }

    /**
     * POST  /gyms : Create a new gym.
     *
     * @param gymDTO the gymDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gymDTO, or with status 400 (Bad Request) if the gym has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gyms")
    @Timed
    public ResponseEntity<GymDTO> createGym(@Valid @RequestBody GymDTO gymDTO) throws URISyntaxException {
        log.debug("REST request to save Gym : {}", gymDTO);
        if (gymDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gym cannot already have an ID")).body(null);
        }
        GymDTO result = gymService.save(gymDTO);
        return ResponseEntity.created(new URI("/api/gyms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gyms : Updates an existing gym.
     *
     * @param gymDTO the gymDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gymDTO,
     * or with status 400 (Bad Request) if the gymDTO is not valid,
     * or with status 500 (Internal Server Error) if the gymDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gyms")
    @Timed
    public ResponseEntity<GymDTO> updateGym(@Valid @RequestBody GymDTO gymDTO) throws URISyntaxException {
        log.debug("REST request to update Gym : {}", gymDTO);
        if (gymDTO.getId() == null) {
            return createGym(gymDTO);
        }
        GymDTO result = gymService.save(gymDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gymDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gyms : get all the gyms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gyms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/gyms")
    @Timed
    public ResponseEntity<List<GymDTO>> getAllGyms(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Gyms");
        Page<GymDTO> page = gymService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gyms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /gyms/:id : get the "id" gym.
     *
     * @param id the id of the gymDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gymDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gyms/{id}")
    @Timed
    public ResponseEntity<GymDTO> getGym(@PathVariable Long id) {
        log.debug("REST request to get Gym : {}", id);
        GymDTO gymDTO = gymService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gymDTO));
    }

    /**
     * DELETE  /gyms/:id : delete the "id" gym.
     *
     * @param id the id of the gymDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gyms/{id}")
    @Timed
    public ResponseEntity<Void> deleteGym(@PathVariable Long id) {
        log.debug("REST request to delete Gym : {}", id);
        gymService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
