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
 * Service Implementation for managing WorkoutTemplate.
 */
@Service
@Transactional
public class WorkoutTemplateService {

    private final Logger log = LoggerFactory.getLogger(WorkoutTemplateService.class);

    private final UserRepository userRepository;
    private final UserDemographicRepository userDemographicRepository;
    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final UserWorkoutTemplateRepository userWorkoutTemplateRepository;
    private final WorkoutTemplateMapper workoutTemplateMapper;
    private final WorkoutTemplateWithChildrenMapper workoutTemplateWithChildrenMapper;

    /**
     * Constructor
     * @param userRepository for getting user data
     * @param userDemographicRepository for getting UserDemographic data
     * @param workoutTemplateRepository for getting WorkoutTemplate data
     * @param userWorkoutTemplateRepository for getting User data
     * @param workoutTemplateMapper to map WorkoutTemplate to and from WorkoutTemplateDTO
     * @param workoutTemplateWithChildrenMapper to map WorkoutTemplateWithChildren to and from WorkoutTemplateWithChildrenDTO
     */
    public WorkoutTemplateService(UserRepository userRepository, UserDemographicRepository userDemographicRepository, WorkoutTemplateRepository workoutTemplateRepository, UserWorkoutTemplateRepository userWorkoutTemplateRepository, WorkoutTemplateMapper workoutTemplateMapper, WorkoutTemplateWithChildrenMapper workoutTemplateWithChildrenMapper) {
        this.userRepository = userRepository;
        this.userDemographicRepository = userDemographicRepository;
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.userWorkoutTemplateRepository = userWorkoutTemplateRepository;
        this.workoutTemplateMapper = workoutTemplateMapper;
        this.workoutTemplateWithChildrenMapper = workoutTemplateWithChildrenMapper;
    }

    /**
     * Save a workoutTemplate.
     *
     * @param workoutTemplateDTO the entity to save
     * @return the persisted entity
     */
    public WorkoutTemplateDTO save(WorkoutTemplateDTO workoutTemplateDTO) {
        log.debug("Request to save WorkoutTemplate : {}", workoutTemplateDTO);

        if (workoutTemplateDTO.getUserDemographicId() == null) {
            Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

            if (user.isPresent()) {
                WorkoutTemplate workoutTemplate = workoutTemplateMapper.workoutTemplateDTOToWorkoutTemplate(workoutTemplateDTO);
                workoutTemplate.setUserDemographic(userDemographicRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()));
                workoutTemplate.setCreatedOn(LocalDate.now());
                workoutTemplate.setLastUpdated(LocalDate.now());
                workoutTemplate = workoutTemplateRepository.save(workoutTemplate);
                return workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
            }
        }
        return null;
    }

    /**
     * Update a workoutTemplate.
     *
     * @param workoutTemplateDTO the entity to save
     * @return the persisted entity
     */
    public WorkoutTemplateDTO update(WorkoutTemplateDTO workoutTemplateDTO) {
        log.debug("Request to update WorkoutTemplate : {}", workoutTemplateDTO);


        WorkoutTemplate workoutTemplate = workoutTemplateMapper.workoutTemplateDTOToWorkoutTemplate(workoutTemplateDTO);
        workoutTemplate.setLastUpdated(LocalDate.now());
        workoutTemplate = workoutTemplateRepository.save(workoutTemplate);
        return workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
    }

    /**
     *  Get all the workoutTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutTemplates by current logged in user.");
        String login = SecurityUtils.getCurrentUserLogin();
        Page<WorkoutTemplate> result = workoutTemplateRepository.findAllByCurrentLoggedInUser(login, pageable);
        return result.map(workoutTemplateMapper::workoutTemplateToWorkoutTemplateDTO);
    }

    /**
     *  Get one workoutTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkoutTemplateWithChildrenDTO findOne(Long id) {
        log.debug("Request to get WorkoutTemplate : {}", id);
        String login = SecurityUtils.getCurrentUserLogin();
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOne(login, id);
        WorkoutTemplateWithChildrenDTO workoutTemplateDTO = workoutTemplateWithChildrenMapper.workoutTemplateToWorkoutTemplateWithChildrenDTO(workoutTemplate);
        return workoutTemplateDTO;
    }

    /**
     *  Delete the  workoutTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkoutTemplate : {}", id);
        if (workoutTemplateRepository.findOne(SecurityUtils.getCurrentUserLogin(), id).getId() != null) {
            removeWorkoutTemplateFromRelatedItems(id);
            workoutTemplateRepository.delete(id);
        }
    }

    public void removeWorkoutTemplateFromRelatedItems(Long id) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOne(id);
        if (workoutTemplate != null) {
            log.debug("Request to remove ExerciseInstance from WorkoutInstance : {}", workoutTemplate.getId());
            UserDemographic userDemographic = workoutTemplate.getUserDemographic();
            userDemographic.removeWorkoutTemplate(workoutTemplate);
            for (UserWorkoutTemplate userWorkoutTemplate :
                workoutTemplate.getUserWorkoutTemplates()) {
                userWorkoutTemplate.setWorkoutTemplate(null);
                userWorkoutTemplateRepository.save(userWorkoutTemplate);
            }
            userDemographicRepository.save(userDemographic);
        }
    }
}
