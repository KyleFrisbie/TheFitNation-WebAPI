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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
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
            if (dbUserDemographic == null) {
                userDemographic.setId(null);
            }
            userDemographic = userDemographicRepository.save(userDemographic);
        } else {
            userDemographic.setUser(user);
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
        User user = AccountAuthTool.getLoggedInUser(userRepository);
        Page<UserDemographic> result;
        if (AccountAuthTool.isAdmin(user)) {
            result = userDemographicRepository.findAll(pageable);
        } else {
            return null;
        }
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
        User user = AccountAuthTool.getLoggedInUser(userRepository);
        UserDemographic userDemographic;
        if (AccountAuthTool.isAdmin(user)) {
            userDemographic = userDemographicRepository.findOneWithEagerRelationships(id);
        } else {
            userDemographic = userDemographicRepository.findOneWithEagerRelationships(id);
            if (!Objects.equals(userDemographic.getUser().getId(), user.getId())) {
                return null;
            }
        }
        return userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
    }

    @Transactional(readOnly = true)
    public UserDemographicDTO findOneByUser() {
        log.debug("Request to get UserDemographic : {}");
        User user = AccountAuthTool.getLoggedInUser(userRepository);
        UserDemographic userDemographic;
        userDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(user.getId());
        return userDemographicMapper.userDemographicToUserDemographicDTO(userDemographic);
    }

    @Transactional(readOnly = true)
    public UserDemographic findOneByLogin(String currentUserLogin) {
        log.debug("Request to get UserDemographic by User : {}", currentUserLogin);
        return userDemographicRepository.findOneByLogin(currentUserLogin);
    }

    /**
     * Delete the  userDemographic by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserDemographic : {}", id);
        User user = AccountAuthTool.getLoggedInUser(userRepository);
        if (AccountAuthTool.isAdmin(user)) {
            userDemographicRepository.delete(id);
        }
    }
}
