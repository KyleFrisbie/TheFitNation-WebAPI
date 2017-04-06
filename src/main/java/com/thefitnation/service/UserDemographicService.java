package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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

    @Transactional(readOnly = true)
    public UserDemographicDTO findOneByUser(Long id) {
        log.debug("Request to get UserDemographic by User : {}", id);
        UserDemographic userDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(id);
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        return userDemographicDTO;
    }

    @Transactional(readOnly = true)
    public UserDemographic findOneByLogin(String currentUserLogin) {
        log.debug("Request to get UserDemographic by User : {}", currentUserLogin);
        return userDemographicRepository.findOneByLogin(currentUserLogin);
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
