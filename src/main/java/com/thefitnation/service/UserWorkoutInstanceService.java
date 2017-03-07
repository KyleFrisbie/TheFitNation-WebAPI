package com.thefitnation.service;

import com.thefitnation.domain.UserWorkoutInstance;
import com.thefitnation.repository.UserWorkoutInstanceRepository;
import com.thefitnation.service.dto.UserWorkoutInstanceDTO;
import com.thefitnation.service.mapper.UserWorkoutInstanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserWorkoutInstance.
 */
@Service
@Transactional
public class UserWorkoutInstanceService {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutInstanceService.class);
    
    private final UserWorkoutInstanceRepository userWorkoutInstanceRepository;

    private final UserWorkoutInstanceMapper userWorkoutInstanceMapper;

    public UserWorkoutInstanceService(UserWorkoutInstanceRepository userWorkoutInstanceRepository, UserWorkoutInstanceMapper userWorkoutInstanceMapper) {
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
}
