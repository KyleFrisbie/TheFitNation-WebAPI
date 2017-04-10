package com.thefitnation.service;

import com.thefitnation.domain.Authority;
import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWeight;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWeightRepository;
import com.thefitnation.security.AuthoritiesConstants;
import com.thefitnation.security.SecurityUtils;
import com.thefitnation.service.dto.UserWeightDTO;
import com.thefitnation.service.mapper.UserWeightMapper;
import com.thefitnation.tools.AccountAuthTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserWeight.
 */
@Service
@Transactional
public class UserWeightService {

    private final Logger log = LoggerFactory.getLogger(UserWeightService.class);

    private final UserWeightRepository userWeightRepository;

    private final UserRepository userRepository;

    private final UserDemographicRepository userDemographicRepository;

    private final UserWeightMapper userWeightMapper;

    public UserWeightService(UserWeightRepository userWeightRepository, UserRepository userRepository, UserDemographicRepository userDemographicRepository, UserWeightMapper userWeightMapper) {
        this.userWeightRepository = userWeightRepository;
        this.userRepository = userRepository;
        this.userDemographicRepository = userDemographicRepository;
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
        User user = AccountAuthTool.getLoggedInUser(userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()));
        if (user == null) {
            return null;
        }
        if (userWeightDTO.getUserDemographicId() == null) {
            userWeightDTO.setUserDemographicId(userDemographicRepository.findOneByUserWithEagerRelationships(user.getId()).getId());
        }

        UserWeight userWeight = userWeightMapper.userWeightDTOToUserWeight(userWeightDTO);

        if (isAdmin(user)) {
            userWeight = userWeightRepository.save(userWeight);
        } else {
            Optional<UserWeight> dbUserWeight = userWeightRepository.findOneByUserId(userWeight.getId(), user.getId());
            if (!dbUserWeight.isPresent()) {
                userWeight.setId(null);
            }
            userWeight = userWeightRepository.save(userWeight);
        }
        UserWeightDTO result = userWeightMapper.userWeightToUserWeightDTO(userWeight);
        return result;
    }

    /**
     * Get all the userWeights.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWeightDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWeights");
        User user = AccountAuthTool.getLoggedInUser(userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()));
        if (user == null) {
            return null;
        }

        Page<UserWeight> result;
        if (isAdmin(user)) {
            result = userWeightRepository.findAll(pageable);
        } else {
            result = userWeightRepository.findAllByUserId(pageable, user.getId());
        }
        return result.map(userWeightMapper::userWeightToUserWeightDTO);
    }

    /**
     * Get one userWeight by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserWeightDTO findOne(Long id) {
        log.debug("Request to get UserWeight : {}", id);

        UserWeightDTO userWeightDTO = getUserWeightWithPermission(id);
        return userWeightDTO;
    }

    /**
     * Delete the  userWeight by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWeight : {}", id);
        UserWeightDTO userWeightDTO = getUserWeightWithPermission(id);
        if ((userWeightDTO != null) && (userWeightDTO.getId() != null)) {
            userWeightRepository.delete(id);
        }
    }

    private UserWeightDTO getUserWeightWithPermission(Long id) {
        User user = AccountAuthTool.getLoggedInUser(userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()));
        UserDemographic userDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(user.getId());
        if (userDemographic == null) {
            return null;
        }
        UserWeight userWeight = userWeightRepository.findOne(id);
        if (!isAdmin(user)) {
            if (!(userWeight.getUserDemographic().getId().equals(userDemographic.getId()))) {
                return null;
            }
        }
        return userWeightMapper.userWeightToUserWeightDTO(userWeight);
    }

    private boolean isAdmin(User user) {
        for (Authority role : user.getAuthorities()) {
            if (role.getName().equals(AuthoritiesConstants.ADMIN)) {
                return true;
            }
        }
        return false;
    }
}
