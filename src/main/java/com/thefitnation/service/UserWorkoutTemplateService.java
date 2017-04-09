package com.thefitnation.service;

import com.thefitnation.domain.User;
import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.repository.UserRepository;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.repository.WorkoutTemplateRepository;
import com.thefitnation.security.SecurityUtils;
import com.thefitnation.service.dto.UserWorkoutTemplateDTO;
import com.thefitnation.service.dto.UserWorkoutTemplateWithChildrenDTO;
import com.thefitnation.service.mapper.UserWorkoutTemplateMapper;
import com.thefitnation.service.mapper.UserWorkoutTemplateWithChildrenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public UserWorkoutTemplateService(UserRepository userRepository, UserWorkoutTemplateRepository userWorkoutTemplateRepository, WorkoutTemplateRepository workoutTemplateRepository, UserDemographicRepository userDemographicRepository, UserWorkoutTemplateMapper userWorkoutTemplateMapper, UserWorkoutTemplateWithChildrenMapper userWorkoutTemplateWithChildrenMapper) {
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
        if (userWorkoutTemplateDTO.getUserDemographicId() == null) {
            Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
            if (user.isPresent()) {
                UserDemographic userDemographic = userDemographicRepository.findOneByUserWithEagerRelationships(user.get().getId());
                userWorkoutTemplateDTO.setUserDemographicId(userDemographic.getId());
            }
        }
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateMapper.userWorkoutTemplateDTOToUserWorkoutTemplate(userWorkoutTemplateDTO);
        userWorkoutTemplate = userWorkoutTemplateRepository.save(userWorkoutTemplate);
        UserWorkoutTemplateDTO result = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        return result;
    }

    /**
     *  Get all the userWorkoutTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWorkoutTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorkoutTemplates");
        Page<UserWorkoutTemplate> result = userWorkoutTemplateRepository.findAll(pageable);
        return result.map(userWorkoutTemplate -> userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate));
    }

    /**
     *  Get one userWorkoutTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserWorkoutTemplateWithChildrenDTO findOne(Long id) {
        log.debug("Request to get UserWorkoutTemplate : {}", id);
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateRepository.findOne(id);
        UserWorkoutTemplateWithChildrenDTO userWorkoutTemplateDTO = userWorkoutTemplateWithChildrenMapper.userWorkoutTemplateToUserWorkoutTemplateWithChildrenDTO(userWorkoutTemplate);
        return userWorkoutTemplateDTO;
    }

    /**
     *  Delete the  userWorkoutTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWorkoutTemplate : {}", id);
        removeUserWorkoutTemplateFromRelatedItems(id);
        userWorkoutTemplateRepository.delete(id);
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
