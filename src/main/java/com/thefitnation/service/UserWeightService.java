package com.thefitnation.service;

import com.thefitnation.domain.UserWeight;
import com.thefitnation.repository.UserWeightRepository;
import com.thefitnation.service.dto.UserWeightDTO;
import com.thefitnation.service.mapper.UserWeightMapper;
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
 * Service Implementation for managing UserWeight.
 */
@Service
@Transactional
public class UserWeightService {

    private final Logger log = LoggerFactory.getLogger(UserWeightService.class);

    private final UserWeightRepository userWeightRepository;

    private final UserWeightMapper userWeightMapper;

    public UserWeightService(UserWeightRepository userWeightRepository, UserWeightMapper userWeightMapper) {
        this.userWeightRepository = userWeightRepository;
        this.userWeightMapper = userWeightMapper;
    }

    /**
     * Save a userWeight.
     *
     * @param userWeightDTO the entity to save
     * @return the persisted entity
     */
    public UserWeightDTO save(UserWeightDTO userWeightDTO) {
        log.debug("Request to save UserWeight : {}", userWeightDTO);
        UserWeight userWeight = userWeightMapper.userWeightDTOToUserWeight(userWeightDTO);
        userWeight = userWeightRepository.save(userWeight);
        UserWeightDTO result = userWeightMapper.userWeightToUserWeightDTO(userWeight);
        return result;
    }

    /**
     *  Get all the userWeights.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWeightDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWeights");
        Page<UserWeight> result = userWeightRepository.findAll(pageable);
        return result.map(userWeight -> userWeightMapper.userWeightToUserWeightDTO(userWeight));
    }

    /**
     *  Get all the userWeights owned by a user.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWeightDTO> findAllByUserId(Pageable pageable, Long id) {
        log.debug("Request to get all UserWeights");
        Page<UserWeight> result = userWeightRepository.findAllByUserId(pageable, id);
        return result.map(userWeight -> userWeightMapper.userWeightToUserWeightDTO(userWeight));
    }

    /**
     *  Get one userWeight by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserWeightDTO findOne(Long id) {
        log.debug("Request to get UserWeight : {}", id);
        UserWeight userWeight = userWeightRepository.findOne(id);
        UserWeightDTO userWeightDTO = userWeightMapper.userWeightToUserWeightDTO(userWeight);
        return userWeightDTO;
    }

    /**
     *  Delete the  userWeight by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWeight : {}", id);
        userWeightRepository.delete(id);
    }
}
