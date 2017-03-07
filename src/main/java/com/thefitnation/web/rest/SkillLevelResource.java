package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.service.SkillLevelService;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.SkillLevelDTO;
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
 * REST controller for managing SkillLevel.
 */
@RestController
@RequestMapping("/api")
public class SkillLevelResource {

    private final Logger log = LoggerFactory.getLogger(SkillLevelResource.class);

    private static final String ENTITY_NAME = "skillLevel";
        
    private final SkillLevelService skillLevelService;

    public SkillLevelResource(SkillLevelService skillLevelService) {
        this.skillLevelService = skillLevelService;
    }

    /**
     * POST  /skill-levels : Create a new skillLevel.
     *
     * @param skillLevelDTO the skillLevelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillLevelDTO, or with status 400 (Bad Request) if the skillLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skill-levels")
    @Timed
    public ResponseEntity<SkillLevelDTO> createSkillLevel(@Valid @RequestBody SkillLevelDTO skillLevelDTO) throws URISyntaxException {
        log.debug("REST request to save SkillLevel : {}", skillLevelDTO);
        if (skillLevelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new skillLevel cannot already have an ID")).body(null);
        }
        SkillLevelDTO result = skillLevelService.save(skillLevelDTO);
        return ResponseEntity.created(new URI("/api/skill-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skill-levels : Updates an existing skillLevel.
     *
     * @param skillLevelDTO the skillLevelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillLevelDTO,
     * or with status 400 (Bad Request) if the skillLevelDTO is not valid,
     * or with status 500 (Internal Server Error) if the skillLevelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skill-levels")
    @Timed
    public ResponseEntity<SkillLevelDTO> updateSkillLevel(@Valid @RequestBody SkillLevelDTO skillLevelDTO) throws URISyntaxException {
        log.debug("REST request to update SkillLevel : {}", skillLevelDTO);
        if (skillLevelDTO.getId() == null) {
            return createSkillLevel(skillLevelDTO);
        }
        SkillLevelDTO result = skillLevelService.save(skillLevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skill-levels : get all the skillLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of skillLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/skill-levels")
    @Timed
    public ResponseEntity<List<SkillLevelDTO>> getAllSkillLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SkillLevels");
        Page<SkillLevelDTO> page = skillLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skill-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /skill-levels/:id : get the "id" skillLevel.
     *
     * @param id the id of the skillLevelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillLevelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/skill-levels/{id}")
    @Timed
    public ResponseEntity<SkillLevelDTO> getSkillLevel(@PathVariable Long id) {
        log.debug("REST request to get SkillLevel : {}", id);
        SkillLevelDTO skillLevelDTO = skillLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(skillLevelDTO));
    }

    /**
     * DELETE  /skill-levels/:id : delete the "id" skillLevel.
     *
     * @param id the id of the skillLevelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skill-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillLevel(@PathVariable Long id) {
        log.debug("REST request to delete SkillLevel : {}", id);
        skillLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
