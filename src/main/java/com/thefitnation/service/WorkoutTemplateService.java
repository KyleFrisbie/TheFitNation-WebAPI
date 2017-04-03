package com.thefitnation.service;

import com.thefitnation.domain.UserDemographic;
import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.domain.WorkoutTemplate;
import com.thefitnation.repository.UserDemographicRepository;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.repository.WorkoutTemplateRepository;
import com.thefitnation.service.dto.WorkoutTemplateDTO;
import com.thefitnation.service.dto.WorkoutTemplateWithChildrenDTO;
import com.thefitnation.service.mapper.WorkoutTemplateMapper;
import com.thefitnation.service.mapper.WorkoutTemplateWithChildrenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing WorkoutTemplate.
 */
@Service
@Transactional
public class WorkoutTemplateService {

    private final Logger log = LoggerFactory.getLogger(WorkoutTemplateService.class);

    private final UserDemographicRepository userDemographicRepository;

    private final WorkoutTemplateRepository workoutTemplateRepository;

    private final UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    private final WorkoutTemplateMapper workoutTemplateMapper;

    private final WorkoutTemplateWithChildrenMapper workoutTemplateWithChildrenMapper;

    public WorkoutTemplateService(UserDemographicRepository userDemographicRepository, WorkoutTemplateRepository workoutTemplateRepository, UserWorkoutTemplateRepository userWorkoutTemplateRepository, WorkoutTemplateMapper workoutTemplateMapper, WorkoutTemplateWithChildrenMapper workoutTemplateWithChildrenMapper) {
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
        WorkoutTemplate workoutTemplate = workoutTemplateMapper.workoutTemplateDTOToWorkoutTemplate(workoutTemplateDTO);
        workoutTemplate = workoutTemplateRepository.save(workoutTemplate);
        WorkoutTemplateDTO result = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        return result;
    }

    /**
     *  Get all the workoutTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutTemplates");
        Page<WorkoutTemplate> result = workoutTemplateRepository.findAll(pageable);
        return result.map(workoutTemplate -> workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate));
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
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOne(id);
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
        removeWorkoutTemplateFromRelatedItems(id);
        workoutTemplateRepository.delete(id);
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
