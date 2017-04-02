package com.thefitnation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.security.AuthoritiesConstants;
import com.thefitnation.security.SecurityUtils;
import com.thefitnation.service.UserDemographicService;
import com.thefitnation.service.UserService;
import com.thefitnation.service.UserWeightService;
import com.thefitnation.service.dto.UserDemographicDTO;
import com.thefitnation.web.rest.util.HeaderUtil;
import com.thefitnation.web.rest.util.PaginationUtil;
import com.thefitnation.service.dto.UserWeightDTO;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserWeight.
 */
@RestController
@RequestMapping("/api")
public class UserWeightResource {

    private final Logger log = LoggerFactory.getLogger(UserWeightResource.class);

    private static final String ENTITY_NAME = "userWeight";

    private final UserWeightService userWeightService;

    private final UserService userService;

    private final UserDemographicService userDemographicService;

    private final UserRepository userRepository;

    public UserWeightResource(UserWeightService userWeightService, UserService userService, UserDemographicService userDemographicService, UserRepository userRepository) {
        this.userWeightService = userWeightService;
        this.userService = userService;
        this.userDemographicService = userDemographicService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /user-weights : Create a new userWeight.
     *
     * @param userWeightDTO the userWeightDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userWeightDTO, or with status 400 (Bad Request) if the userWeight has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-weights")
    @Timed
    public ResponseEntity<UserWeightDTO> createUserWeight(@Valid @RequestBody UserWeightDTO userWeightDTO) throws URISyntaxException {
        log.debug("REST request to save UserWeight : {}", userWeightDTO);

        if (userWeightDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userWeight cannot already have an ID")).body(null);
        }
        UserWeightDTO result = userWeightService.save(userWeightDTO);
        return ResponseEntity.created(new URI("/api/user-weights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST /user-weights/byLoggedInUser : Adds a userWeight to the logged in user
     *
     * @param userWeightDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/user-weights/byLoggedInUser")
    @Timed
    public ResponseEntity<UserWeightDTO> createUserWeightByLoggedInUser(@Valid @RequestBody UserWeightDTO userWeightDTO) throws URISyntaxException {
        log.debug("REST request to save UserWeight : {}", userWeightDTO);

        if (userWeightDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userWeight cannot already have an ID")).body(null);
        }
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if(!user.isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser", "Unable to find User by token to associate with UserWeight"))
                .body(null);
        }

        UserDemographicDTO userDemographicDTO = userDemographicService.findOneByUser(user.get().getId());
        if (userDemographicDTO == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "noAssociatedUserDemographic", "Unable to find UserDemographic associated with token owner"))
                .body(null);
        }

        userWeightDTO.setUserDemographicId(userDemographicDTO.getId());

        UserWeightDTO result = userWeightService.save(userWeightDTO);
        return ResponseEntity.created(new URI("/api/user-weights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-weights : Updates an existing userWeight.
     *
     * @param userWeightDTO the userWeightDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userWeightDTO,
     * or with status 400 (Bad Request) if the userWeightDTO is not valid,
     * or with status 500 (Internal Server Error) if the userWeightDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-weights")
    @Timed
    public ResponseEntity<UserWeightDTO> updateUserWeight(@Valid @RequestBody UserWeightDTO userWeightDTO) throws URISyntaxException {
        log.debug("REST request to update UserWeight : {}", userWeightDTO);
        if (userWeightDTO.getId() == null) {
            return createUserWeight(userWeightDTO);
        }
        UserWeightDTO result = userWeightService.save(userWeightDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userWeightDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT /user-weights/byLoggedInUser : Updates a logged in user's userWeight
     *
     * @param userWeightDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/user-weights/byLoggedInUser")
    @Timed
    public ResponseEntity<UserWeightDTO> updateUserWeightByLoggedInUser(@Valid @RequestBody UserWeightDTO userWeightDTO) throws URISyntaxException {
        log.debug("REST request to update UserWeight : {}", userWeightDTO);

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if(!user.isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser", "Unable to find User by token to associate with UserWeight"))
                .body(null);
        }

        UserDemographicDTO userDemographicDTO = userDemographicService.findOneByUser(user.get().getId());
        if (userDemographicDTO == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "noAssociatedUserDemographic", "Unable to find UserDemographic associated with token owner"))
                .body(null);
        }

        userWeightDTO.setUserDemographicId(userDemographicDTO.getId());

        if (userWeightDTO.getId() == null) {
            return createUserWeight(userWeightDTO);
        } else {
            UserWeightDTO foundUserWeight= userWeightService.findOne(userWeightDTO.getId());
            if (foundUserWeight == null || (!foundUserWeight.getUserDemographicId().equals(userWeightDTO.getUserDemographicId()))) {
                return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "notOwnerOfUserWeight", "Attempting to update a user weight not owned by you"))
                    .body(null);
            }
        }

            UserWeightDTO result = userWeightService.save(userWeightDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userWeightDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-weights : get all the userWeights.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userWeights in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-weights")
    @Timed
    public ResponseEntity<List<UserWeightDTO>> getAllUserWeights(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserWeights");
        Page<UserWeightDTO> page = userWeightService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-weights");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-weights/byLoggedInUser : get all the userWeights.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userWeights in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-weights/byLoggedInUser")
    @Timed
    public ResponseEntity<List<UserWeightDTO>> getAllUserWeightsByLoggedInUser(@ApiParam Pageable pageable)
        throws URISyntaxException {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if(!user.isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser", "Unable to find User by token to associate with UserWeight"))
                .body(null);
        }

        log.debug("REST request to get a page of UserWeights");
        Page<UserWeightDTO> page = userWeightService.findAllByUserId(pageable, user.get().getId());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-weights/byLoggedInUser");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-weights/:id : get the "id" userWeight.
     *
     * @param id the id of the userWeightDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userWeightDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-weights/{id}")
    @Timed
    public ResponseEntity<UserWeightDTO> getUserWeight(@PathVariable Long id) {
        log.debug("REST request to get UserWeight : {}", id);
        UserWeightDTO userWeightDTO = userWeightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userWeightDTO));
    }

    /**
     * GET  /user-weights/byLoggedInUser/:id : get the "id" userWeight.
     *
     * @param id the id of the userWeightDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userWeightDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-weights/byLoggedInUser/{id}")
    @Timed
    public ResponseEntity<UserWeightDTO> getUserWeightByLoggedInUser(@PathVariable Long id) {
        log.debug("REST request to get UserWeight : {}", id);
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if(!user.isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser", "Unable to find User by token to associate with UserWeight"))
                .body(null);
        }

        UserDemographicDTO userDemographicDTO = userDemographicService.findOneByUser(user.get().getId());
        if (userDemographicDTO == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "noAssociatedUserDemographic", "User does not own a UserDemographic"))
                .body(null);
        }

        UserWeightDTO foundUserWeight= userWeightService.findOne(id);
        if (foundUserWeight == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "doesNotExist", "UserWeight does not exist"))
                .body(null);
        }

        if (!foundUserWeight.getUserDemographicId().equals(userDemographicDTO.getId())) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "notOwnerOfUserWeight", "Attempting to delete a user weight not owned by you"))
                .body(null);
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(foundUserWeight));
    }

    /**
     * DELETE  /user-weights/:id : delete the "id" userWeight.
     *
     * @param id the id of the userWeightDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-weights/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserWeight(@PathVariable Long id) {
        log.debug("REST request to delete UserWeight : {}", id);
        userWeightService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * DELETE  /user-weights/byLoggedInUser/:id : delete the "id" userWeight.
     *
     * @param id the id of the userWeightDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-weights/byLoggedInUser/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserWeightByLoggedInUser(@PathVariable Long id) {
        log.debug("REST request to delete UserWeight : {}", id);

        ResponseEntity<Void> badRequestResponse = validateOwnerOfUserWeight(id);
        if (badRequestResponse != null) {
            return badRequestResponse;
        }

        userWeightService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private ResponseEntity<Void> validateOwnerOfUserWeight(Long id) {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if(!user.isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser", "Unable to find User by token to associate with UserWeight"))
                .body(null);
        }

        UserDemographicDTO userDemographicDTO = userDemographicService.findOneByUser(user.get().getId());
        if (userDemographicDTO == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "noAssociatedUserDemographic", "User does not own a UserDemographic"))
                .body(null);
        }

        UserWeightDTO foundUserWeight= userWeightService.findOne(id);
        if (foundUserWeight == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "doesNotExist", "UserWeight does not exist"))
                .body(null);
        }

        if (!foundUserWeight.getUserDemographicId().equals(userDemographicDTO.getId())) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "notOwnerOfUserWeight", "Attempting to delete a user weight not owned by you"))
                .body(null);
        }

        return null;
    }
}
