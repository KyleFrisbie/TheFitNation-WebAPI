package com.thefitnation.service;

import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.service.dto.UserDemographicDTO;
import com.thefitnation.service.mapper.UserDemographicMapper;
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
 * Service Implementation for managing UserDemographic.
 */
@Service
@Transactional
public class UserDemographicService {

    private final Logger log = LoggerFactory.getLogger(UserDemographicService.class);
    
    private final UserDemographicRepository userDemographicRepository;

    private final UserDemographicMapper userDemographicMapper;

    public UserDemographicService(UserDemographicRepository userDemographicRepository, UserDemographicMapper userDemographicMapper) {
        this.userDemographicRepository = userDemographicRepository;
        this.userDemographicMapper = userDemographicMapper;
    }

    /**
     * Save a userDemographic.
     *
     * @param userDemographicDTO the entity to save
     * @return the persisted entity
     */
    public UserDemographicDTO save(UserDemographicDTO userDemographicDTO) {
        log.debug("Request to save UserDemographic : {}", userDemographicDTO);
        UserDemographic userDemographic = userDemographicMapper.userDemographicDTOToUserDemographic(userDemographicDTO);
        userDemographic = userDemographicRepository.save(userDemographic);
        UserDemographicDTO result = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        return result;
    }

    /**
     *  Get all the userDemographics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserDemographicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserDemographics");
        Page<UserDemographic> result = userDemographicRepository.findAll(pageable);
        return result.map(userDemographic -> userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic));
    }

    /**
     *  Get one userDemographic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserDemographicDTO findOne(Long id) {
        log.debug("Request to get UserDemographic : {}", id);
        UserDemographic userDemographic = userDemographicRepository.findOneWithEagerRelationships(id);
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        return userDemographicDTO;
    }

    /**
     *  Delete the  userDemographic by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserDemographic : {}", id);
        userDemographicRepository.delete(id);
    }
}
