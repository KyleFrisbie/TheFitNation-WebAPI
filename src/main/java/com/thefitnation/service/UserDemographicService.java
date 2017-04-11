package com.thefitnation.service;

import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.security.SecurityUtils;
import com.thefitnation.service.dto.UserDemographicDTO;
import com.thefitnation.service.mapper.UserDemographicMapper;
import com.thefitnation.tools.AccountAuthTool;
import com.thefitnation.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Service Implementation for managing UserDemographic.
 */
@Service
@Transactional
public class UserDemographicService {

    private final Logger log = LoggerFactory.getLogger(UserDemographicService.class);

    private final UserRepository userRepository;

    private final UserDemographicRepository userDemographicRepository;

    private final UserDemographicMapper userDemographicMapper;

    public UserDemographicService(UserRepository userRepository, UserDemographicRepository userDemographicRepository, UserDemographicMapper userDemographicMapper) {
        this.userRepository = userRepository;
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
        User user = AccountAuthTool.getLoggedInUser(userRepository);
        if (user == null) {
            return null;
        }
        if (userDemographicDTO.getUserId() == null) {
            userDemographicDTO.setUserId(user.getId());
        }
        UserDemographic userDemographic = userDemographicMapper.userDemographicDTOToUserDemographic(userDemographicDTO);

        if (AccountAuthTool.isAdmin(user)) {
            UserDemographic dbUserDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(userDemographic.getUser().getId());
            userDemographic.setId(dbUserDemographic.getId());
            userDemographic = userDemographicRepository.save(userDemographic);
        } else {
            UserDemographic dbUserDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(user.getId());
            if (dbUserDemographic == null) {
                userDemographic.setId(null);
            } else {
                userDemographic.setId(dbUserDemographic.getId());
            }
            userDemographic = userDemographicRepository.save(userDemographic);
        }

        UserDemographicDTO result = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        return result;
    }

    /**
     * Get all the userDemographics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserDemographicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserDemographics");
        Page<UserDemographic> result = userDemographicRepository.findAll(pageable);
        return result.map(userDemographicMapper::userDemographicToUserDemographicDTO);
    }

    /**
     * Get one userDemographic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserDemographicDTO findOne(Long id) {
        log.debug("Request to get UserDemographic : {}", id);
        UserDemographic userDemographic = userDemographicRepository.findOneWithEagerRelationships(id);
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        return userDemographicDTO;
    }

    @Transactional(readOnly = true)
    public UserDemographicDTO findOneByUser() {
        log.debug("Request to get UserDemographic by User : {}");
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (!user.isPresent()) {
            return null;
        }
        UserDemographic userDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(user.get().getId());
        UserDemographicDTO userDemographicDTO = userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
        return userDemographicDTO;
    }

    /**
     * Delete the  userDemographic by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserDemographic : {}", id);
        userDemographicRepository.delete(id);
    }
}
