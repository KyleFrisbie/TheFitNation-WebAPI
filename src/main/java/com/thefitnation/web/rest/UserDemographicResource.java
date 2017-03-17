package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.User;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.security.SecurityUtils;
import com.thefitnation.service.UserDemographicService;
import com.thefitnation.service.dto.UserDTO;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.UserDemographicDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserDemographic.
 */
@RestController
@RequestMapping("/api")
public class UserDemographicResource {

    private final Logger log = LoggerFactory.getLogger(UserDemographicResource.class);

    private static final String ENTITY_NAME = "userDemographic";

    private final UserDemographicService userDemographicService;

    private final UserRepository userRepository;

    public UserDemographicResource(UserRepository userRepository, UserDemographicService userDemographicService) {
        this.userRepository = userRepository;
        this.userDemographicService = userDemographicService;
    }

    /**
     * POST  /user-demographics : Create a new userDemographic.
     *
     * @param userDemographicDTO the userDemographicDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDemographicDTO, or with status 400 (Bad Request) if the userDemographic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-demographics")
    @Timed
    public ResponseEntity<UserDemographicDTO> createUserDemographic(@Valid @RequestBody UserDemographicDTO userDemographicDTO) throws URISyntaxException {
        log.debug("REST request to save UserDemographic : {}", userDemographicDTO);
        if (userDemographicDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userDemographic cannot already have an ID")).body(null);
        }

        userDemographicDTO.setCreatedOn(LocalDate.now());

        UserDemographicDTO result = userDemographicService.save(userDemographicDTO);
        return ResponseEntity.created(new URI("/api/user-demographics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-demographics : Updates an existing userDemographic.
     *
     * @param userDemographicDTO the userDemographicDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDemographicDTO,
     * or with status 400 (Bad Request) if the userDemographicDTO is not valid,
     * or with status 500 (Internal Server Error) if the userDemographicDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-demographics")
    @Timed
    public ResponseEntity<UserDemographicDTO> updateUserDemographic(@Valid @RequestBody UserDemographicDTO userDemographicDTO) throws URISyntaxException {
        log.debug("REST request to update UserDemographic : {}", userDemographicDTO);
        if (userDemographicDTO.getId() == null) {
            return createUserDemographic(userDemographicDTO);
        }
        UserDemographicDTO result = userDemographicService.save(userDemographicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDemographicDTO.getId().toString()))
            .body(result);
    }

    /***
     * PUT /user-demographics/byLoggedInUser : Updates a logged in user's userDemographic
     *
     * @param userDemographicDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/user-demographics/byLoggedInUser")
    @Timed
    public ResponseEntity<UserDemographicDTO> updateUserDemographicByLoggedInUser(@Valid @RequestBody UserDemographicDTO userDemographicDTO) throws URISyntaxException {
        log.debug("REST request to update UserDemographic : {}", userDemographicDTO);
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if(!user.isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser", "Unable to find User by token to associate with UserDemographic"))
                .body(null);
        }
        userDemographicDTO.setId(null);
        userDemographicDTO.setUserId(user.get().getId());
        UserDemographicDTO foundUserDemographic = userDemographicService.findOneByUser(user.get().getId());

        if(foundUserDemographic != null) {
            userDemographicDTO.setId(foundUserDemographic.getId());
        }

        if (userDemographicDTO.getId() == null) {
            return createUserDemographic(userDemographicDTO);
        }
        UserDemographicDTO result = userDemographicService.save(userDemographicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDemographicDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-demographics : get all the userDemographics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userDemographics in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-demographics")
    @Timed
    public ResponseEntity<List<UserDemographicDTO>> getAllUserDemographics(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserDemographics");
        Page<UserDemographicDTO> page = userDemographicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-demographics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-demographics/:id : get the "id" userDemographic.
     *
     * @param id the id of the userDemographicDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDemographicDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-demographics/{id}")
    @Timed
    public ResponseEntity<UserDemographicDTO> getUserDemographic(@PathVariable Long id) {
        log.debug("REST request to get UserDemographic : {}", id);
        UserDemographicDTO userDemographicDTO = userDemographicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userDemographicDTO));
    }

    /**
     * DELETE  /user-demographics/:id : delete the "id" userDemographic.
     *
     * @param id the id of the userDemographicDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-demographics/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDemographic(@PathVariable Long id) {
        log.debug("REST request to delete UserDemographic : {}", id);
        userDemographicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
