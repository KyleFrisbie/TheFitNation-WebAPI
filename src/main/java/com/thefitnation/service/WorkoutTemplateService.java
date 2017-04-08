package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.security.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import java.time.*;
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
    private final UserDemographicService userDemographicService;

    public WorkoutTemplateService(WorkoutTemplateRepository workoutTemplateRepository, WorkoutTemplateMapper workoutTemplateMapper, UserDemographicService userDemographicService) {
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.workoutTemplateMapper = workoutTemplateMapper;
        this.userDemographicService = userDemographicService;
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

        workoutTemplate.setUserDemographic(userDemographicService.findOneByLogin(SecurityUtils.getCurrentUserLogin()));
        workoutTemplate.setCreatedOn(LocalDate.now());
        workoutTemplate.setLastUpdated(LocalDate.now());

        workoutTemplate = workoutTemplateRepository.save(workoutTemplate);
        WorkoutTemplateDTO result = workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
        return result;
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
        if (isLoggedInUser(workoutTemplateDTO)) {
            workoutTemplate.setLastUpdated(LocalDate.now());
            workoutTemplate = workoutTemplateRepository.save(workoutTemplate);
        }
        return workoutTemplateMapper.workoutTemplateToWorkoutTemplateDTO(workoutTemplate);
    }

    private boolean isLoggedInUser(WorkoutTemplateDTO workoutTemplateDTO) {
        return workoutTemplateDTO.getUserDemographicId() == userDemographicService.findOneByLogin(SecurityUtils.getCurrentUserLogin()).getId();
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
    public Page<WorkoutTemplateDTO> findAllByLogin(Pageable pageable) {
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
    public boolean delete(Long id) {
        log.debug("Request to delete WorkoutTemplate : {}", id);

        if (this.isLoggedInUser(this.findOne(id))) {
            workoutTemplateRepository.delete(id);
            return true;
        }
        return false;
    }
}
