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
    private final SkillLevelRepository skillLevelRepository;
    private final WorkoutInstanceService workoutInstanceService;

    /**
     * Constructor
     * @param userRepository                    for getting user data
     * @param userDemographicRepository         for getting UserDemographic data
     * @param workoutTemplateRepository         for getting WorkoutTemplate data
     * @param userWorkoutTemplateRepository     for getting User data
     * @param workoutTemplateMapper             to map WorkoutTemplate to and from WorkoutTemplateDTO
     * @param workoutTemplateWithChildrenMapper to map WorkoutTemplateWithChildren to and from WorkoutTemplateWithChildrenDTO
     * @param skillLevelRepository
     * @param workoutInstanceService
     */
    public WorkoutTemplateService(UserRepository userRepository,
                                  UserDemographicRepository userDemographicRepository,
                                  WorkoutTemplateRepository workoutTemplateRepository,
                                  UserWorkoutTemplateRepository userWorkoutTemplateRepository,
                                  WorkoutTemplateMapper workoutTemplateMapper,
                                  WorkoutTemplateWithChildrenMapper workoutTemplateWithChildrenMapper, SkillLevelRepository skillLevelRepository, WorkoutInstanceService workoutInstanceService) {
        this.userRepository = userRepository;
        this.userDemographicRepository = userDemographicRepository;
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.userWorkoutTemplateRepository = userWorkoutTemplateRepository;
        this.workoutTemplateMapper = workoutTemplateMapper;
        this.workoutTemplateWithChildrenMapper = workoutTemplateWithChildrenMapper;
        this.skillLevelRepository = skillLevelRepository;
        this.workoutInstanceService = workoutInstanceService;
    }

    /**
     * Save a workoutTemplate.
     *
     * @param workoutTemplateDTO the entity to save
     * @return the persisted entity
     */
    public WorkoutTemplateDTO save(WorkoutTemplateDTO workoutTemplateDTO) {
        log.debug("Request to save WorkoutTemplate : {}", workoutTemplateDTO);
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (!user.isPresent()) {
            return null;
        }
        workoutTemplateDTO.setUserDemographicId(userDemographicRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).getId());
        if (workoutTemplateDTO.getId() != null) {
            WorkoutTemplate dbWorkoutTemplate = workoutTemplateRepository.findOne(user.get().getLogin(), workoutTemplateDTO.getId());
            if (dbWorkoutTemplate == null) {
                workoutTemplateDTO.setCreatedOn(LocalDate.now());
                workoutTemplateDTO.setLastUpdated(LocalDate.now());
                workoutTemplateDTO.setId(null);
            } else {
                workoutTemplateDTO.setLastUpdated(LocalDate.now());
            }
        } else {
            workoutTemplateDTO.setCreatedOn(LocalDate.now());
            workoutTemplateDTO.setLastUpdated(LocalDate.now());
        }
        WorkoutTemplate workoutTemplate = workoutTemplateMapper.workoutTemplateDTOToWorkoutTemplate(workoutTemplateDTO);
        workoutTemplate = workoutTemplateRepository.save(workoutTemplate);

        workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        workoutTemplateDTO.setSkillLevelLevel(skillLevelRepository.findOne(workoutTemplateDTO.getSkillLevelId()).getLevel());
        return workoutTemplateDTO;
    }

    /**
     * Get all the workoutTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutTemplates by current logged in user.");
        String login = SecurityUtils.getCurrentUserLogin();
        Page<WorkoutTemplate> result = workoutTemplateRepository.findAll(login, pageable);
        return result.map(workoutTemplateMapper::workoutTemplateToWorkoutTemplateDTO);
    }

    /**
     * Get one workoutTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WorkoutTemplateWithChildrenDTO findOne(Long id) {
        log.debug("Request to get WorkoutTemplate : {}", id);
        String login = SecurityUtils.getCurrentUserLogin();
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOne(login, id);
        return workoutTemplateWithChildrenMapper.workoutTemplateToWorkoutTemplateWithChildrenDTO(workoutTemplate);
    }

    /**
     * Delete the  workoutTemplate by id.
     *
     * @param id the id of the entity
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
            Set<WorkoutInstance> workoutInstances = new HashSet<>(workoutTemplate.getWorkoutInstances());
            for (Iterator<WorkoutInstance> iterator = workoutInstances.iterator(); iterator.hasNext();) {
                WorkoutInstance workoutInstance = iterator.next();
                iterator.remove();
                workoutInstanceService.delete(workoutInstance.getId());
            }
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
