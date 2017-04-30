package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.security.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;

import java.time.*;
import java.util.*;

import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

/**
 * Service Implementation for managing UserWorkoutTemplate.
 */
@Service
@Transactional
public class UserWorkoutTemplateService {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutTemplateService.class);

    private final UserRepository userRepository;

    private final UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    private final WorkoutTemplateRepository workoutTemplateRepository;

    private final UserDemographicRepository userDemographicRepository;

    private final UserWorkoutTemplateMapper userWorkoutTemplateMapper;

    private final UserWorkoutTemplateWithChildrenMapper userWorkoutTemplateWithChildrenMapper;

    public UserWorkoutTemplateService(UserRepository userRepository,
                                      UserWorkoutTemplateRepository userWorkoutTemplateRepository,
                                      WorkoutTemplateRepository workoutTemplateRepository,
                                      UserDemographicRepository userDemographicRepository,
                                      UserWorkoutTemplateMapper userWorkoutTemplateMapper,
                                      UserWorkoutTemplateWithChildrenMapper userWorkoutTemplateWithChildrenMapper) {
        this.userRepository = userRepository;
        this.userWorkoutTemplateRepository = userWorkoutTemplateRepository;
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.userDemographicRepository = userDemographicRepository;
        this.userWorkoutTemplateMapper = userWorkoutTemplateMapper;
        this.userWorkoutTemplateWithChildrenMapper = userWorkoutTemplateWithChildrenMapper;
    }

    /**
     * Save a userWorkoutTemplate.
     *
     * @param userWorkoutTemplateDTO the entity to save
     * @return the persisted entity
     */
    public UserWorkoutTemplateDTO save(UserWorkoutTemplateDTO userWorkoutTemplateDTO) {
        log.debug("Request to save UserWorkoutTemplate : {}", userWorkoutTemplateDTO);
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (!user.isPresent()) {
            return null;
        }
        userWorkoutTemplateDTO.setUserDemographicId(userDemographicRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).getId());
        if (userWorkoutTemplateDTO.getId() != null) {
            UserWorkoutTemplate dbUserWorkoutTemplate = userWorkoutTemplateRepository.findOne(user.get().getLogin(), userWorkoutTemplateDTO.getId());
            if (dbUserWorkoutTemplate == null) {
                userWorkoutTemplateDTO.setCreatedOn(LocalDate.now());
                userWorkoutTemplateDTO.setLastUpdated(LocalDate.now());
                userWorkoutTemplateDTO.setId(null);
            } else {
                userWorkoutTemplateDTO.setLastUpdated(LocalDate.now());
            }
        } else {
            userWorkoutTemplateDTO.setCreatedOn(LocalDate.now());
            userWorkoutTemplateDTO.setLastUpdated(LocalDate.now());
        }
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateMapper.userWorkoutTemplateDTOToUserWorkoutTemplate(userWorkoutTemplateDTO);
        userWorkoutTemplate = userWorkoutTemplateRepository.save(userWorkoutTemplate);

        userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        if (userWorkoutTemplateDTO.getWorkoutTemplateId() != null) {
            userWorkoutTemplateDTO.setWorkoutTemplateName(workoutTemplateRepository.findOne(userWorkoutTemplateDTO.getWorkoutTemplateId()).getName());
        }
        return userWorkoutTemplateDTO;
    }

    /**
     * Get all the userWorkoutTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWorkoutTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorkoutTemplates");
        Page<UserWorkoutTemplate> result = userWorkoutTemplateRepository.findAll(SecurityUtils.getCurrentUserLogin(), pageable);
        return result.map(userWorkoutTemplateMapper::userWorkoutTemplateToUserWorkoutTemplateDTO);
    }

    /**
     * Get one userWorkoutTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserWorkoutTemplateWithChildrenDTO findOne(Long id) {
        log.debug("Request to get UserWorkoutTemplate : {}", id);
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateRepository.findOne(SecurityUtils.getCurrentUserLogin(), id);
        return userWorkoutTemplateWithChildrenMapper.userWorkoutTemplateToUserWorkoutTemplateWithChildrenDTO(userWorkoutTemplate);
    }

    /**
     * Delete the  userWorkoutTemplate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWorkoutTemplate : {}", id);
        if (userWorkoutTemplateRepository.findOne(SecurityUtils.getCurrentUserLogin(), id).getId() != null) {
            removeUserWorkoutTemplateFromRelatedItems(id);
            userWorkoutTemplateRepository.delete(id);
        }
    }

    public void removeUserWorkoutTemplateFromRelatedItems(Long id) {
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateRepository.findOne(id);
        if (userWorkoutTemplate != null) {
            UserDemographic userDemographic = userWorkoutTemplate.getUserDemographic();
            userDemographic.removeUserWorkoutTemplate(userWorkoutTemplate);
            userDemographicRepository.save(userDemographic);

            if (userWorkoutTemplate.getWorkoutTemplate() != null) {
                WorkoutTemplate workoutTemplate = userWorkoutTemplate.getWorkoutTemplate();
                workoutTemplate.removeUserWorkoutTemplate(userWorkoutTemplate);
                workoutTemplateRepository.save(workoutTemplate);
            }
        }
    }
}
