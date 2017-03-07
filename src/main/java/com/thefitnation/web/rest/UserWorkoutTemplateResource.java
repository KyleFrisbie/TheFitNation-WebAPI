package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.UserWorkoutTemplateService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.UserWorkoutTemplateDTO;
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
 * REST controller for managing UserWorkoutTemplate.
 */
@RestController
@RequestMapping("/api")
public class UserWorkoutTemplateResource {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutTemplateResource.class);

    private static final String ENTITY_NAME = "userWorkoutTemplate";
        
    private final UserWorkoutTemplateService userWorkoutTemplateService;

    public UserWorkoutTemplateResource(UserWorkoutTemplateService userWorkoutTemplateService) {
        this.userWorkoutTemplateService = userWorkoutTemplateService;
    }

    /**
     * POST  /user-workout-templates : Create a new userWorkoutTemplate.
     *
     * @param userWorkoutTemplateDTO the userWorkoutTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userWorkoutTemplateDTO, or with status 400 (Bad Request) if the userWorkoutTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-workout-templates")
    @Timed
    public ResponseEntity<UserWorkoutTemplateDTO> createUserWorkoutTemplate(@Valid @RequestBody UserWorkoutTemplateDTO userWorkoutTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save UserWorkoutTemplate : {}", userWorkoutTemplateDTO);
        if (userWorkoutTemplateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userWorkoutTemplate cannot already have an ID")).body(null);
        }
        UserWorkoutTemplateDTO result = userWorkoutTemplateService.save(userWorkoutTemplateDTO);
        return ResponseEntity.created(new URI("/api/user-workout-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-workout-templates : Updates an existing userWorkoutTemplate.
     *
     * @param userWorkoutTemplateDTO the userWorkoutTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userWorkoutTemplateDTO,
     * or with status 400 (Bad Request) if the userWorkoutTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the userWorkoutTemplateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-workout-templates")
    @Timed
    public ResponseEntity<UserWorkoutTemplateDTO> updateUserWorkoutTemplate(@Valid @RequestBody UserWorkoutTemplateDTO userWorkoutTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update UserWorkoutTemplate : {}", userWorkoutTemplateDTO);
        if (userWorkoutTemplateDTO.getId() == null) {
            return createUserWorkoutTemplate(userWorkoutTemplateDTO);
        }
        UserWorkoutTemplateDTO result = userWorkoutTemplateService.save(userWorkoutTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userWorkoutTemplateDTO.getId().toString()))
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
    public ResponseEntity<List<UserWorkoutTemplateDTO>> getAllUserWorkoutTemplates(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserWorkoutTemplates");
        Page<UserWorkoutTemplateDTO> page = userWorkoutTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-workout-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-workout-templates/:id : get the "id" userWorkoutTemplate.
     *
     * @param id the id of the userWorkoutTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userWorkoutTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-workout-templates/{id}")
    @Timed
    public ResponseEntity<UserWorkoutTemplateDTO> getUserWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to get UserWorkoutTemplate : {}", id);
        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userWorkoutTemplateDTO));
    }

    /**
     * DELETE  /user-workout-templates/:id : delete the "id" userWorkoutTemplate.
     *
     * @param id the id of the userWorkoutTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-workout-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserWorkoutTemplate(@PathVariable Long id) {
        log.debug("REST request to delete UserWorkoutTemplate : {}", id);
        userWorkoutTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
