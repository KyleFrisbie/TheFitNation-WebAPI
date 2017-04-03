package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

/**
 * Service Implementation for managing UserWorkoutInstance.
 */
@Service
@Transactional
public class UserWorkoutInstanceService {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutInstanceService.class);
    private final UserService userService;
    private final UserWorkoutInstanceRepository userWorkoutInstanceRepository;
    private final UserWorkoutInstanceMapper userWorkoutInstanceMapper;

    public UserWorkoutInstanceService(UserService userService, UserWorkoutInstanceRepository userWorkoutInstanceRepository, UserWorkoutInstanceMapper userWorkoutInstanceMapper) {
        this.userService = userService;
        this.userWorkoutInstanceRepository = userWorkoutInstanceRepository;
        this.userWorkoutInstanceMapper = userWorkoutInstanceMapper;
    }

    /**
     * Save a userWorkoutInstance.
     *
     * @param userWorkoutInstanceDTO the entity to save
     * @return the persisted entity
     */
    public UserWorkoutInstanceDTO save(UserWorkoutInstanceDTO userWorkoutInstanceDTO) {
        log.debug("Request to save UserWorkoutInstance : {}", userWorkoutInstanceDTO);
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceMapper.userWorkoutInstanceDTOToUserWorkoutInstance(userWorkoutInstanceDTO);
        userWorkoutInstance = userWorkoutInstanceRepository.save(userWorkoutInstance);
        UserWorkoutInstanceDTO result = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);
        return result;
    }

    /**
     *  Get all the userWorkoutInstances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWorkoutInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorkoutInstances");
        Page<UserWorkoutInstance> result = userWorkoutInstanceRepository.findAll(pageable);
        return result.map(userWorkoutInstance -> userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance));
    }

    /**
     *  Get one userWorkoutInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserWorkoutInstanceDTO findOne(Long id) {
        log.debug("Request to get UserWorkoutInstance : {}", id);
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceRepository.findOne(id);
        UserWorkoutInstanceDTO userWorkoutInstanceDTO = userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);
        return userWorkoutInstanceDTO;
    }

    /**
     *  Delete the  userWorkoutInstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWorkoutInstance : {}", id);
        userWorkoutInstanceRepository.delete(id);
    }

    public Page<UserWorkoutInstanceDTO> findAllByCurrUser(Pageable pageable) {
        log.debug("Request to get all UserWorkoutInstances");

        Optional<User> user = userService.findCurrentLoggedInUser();

        if (userService.findCurrentLoggedInUser().isPresent()) {
            Page<UserWorkoutInstanceDTO> result =
                userWorkoutInstanceRepository.findAllByCurrentUser(user.get().getLogin(), pageable);
            return result;
        }
        return null;
    }
}
