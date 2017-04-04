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
 * Service Implementation for managing WorkoutTemplate.
 */
@Service
@Transactional
public class WorkoutTemplateService {

    private final Logger log = LoggerFactory.getLogger(WorkoutTemplateService.class);

    private final WorkoutTemplateRepository workoutTemplateRepository;

    private final WorkoutTemplateMapper workoutTemplateMapper;

    public WorkoutTemplateService(WorkoutTemplateRepository workoutTemplateRepository, WorkoutTemplateMapper workoutTemplateMapper) {
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.workoutTemplateMapper = workoutTemplateMapper;
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
     *  <p>Get all the workoutTemplates.</p>
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutTemplateDTO> findAllByCurrentLoggedInUser(Pageable pageable) {
        log.debug("Request to get all WorkoutTemplates by current logged in user.");
        Page<WorkoutTemplate> result = workoutTemplateRepository.findAllByCurrentLoggedInUser(pageable);
        return result.map(workoutTemplateMapper::workoutTemplateToWorkoutTemplateDTO);
    }

    /**
     *  Get one workoutTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkoutTemplateDTO findOne(Long id) {
        log.debug("Request to get WorkoutTemplate : {}", id);
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOne(id);
        WorkoutTemplateDTO workoutTemplateDTO = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        return workoutTemplateDTO;
    }

    /**
     *  Delete the  workoutTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkoutTemplate : {}", id);
        workoutTemplateRepository.delete(id);
    }


}
