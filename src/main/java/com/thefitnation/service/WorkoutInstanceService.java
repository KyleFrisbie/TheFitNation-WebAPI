package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.service.dto.ExerciseInstanceDTO;
import com.thefitnation.service.dto.WorkoutInstanceDTO;
import com.thefitnation.service.mapper.ExerciseInstanceMapper;
import com.thefitnation.service.mapper.WorkoutInstanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service Implementation for managing WorkoutInstance.
 */
@Service
@Transactional
public class WorkoutInstanceService {

    private final Logger log = LoggerFactory.getLogger(WorkoutInstanceService.class);

    private final WorkoutTemplateRepository workoutTemplateRepository;

    private final WorkoutInstanceRepository workoutInstanceRepository;

    private final ExerciseInstanceRepository exerciseInstanceRepository;

    private final UserWorkoutInstanceRepository userWorkoutInstanceRepository;

    private final UserExerciseInstanceRepository userExerciseInstanceRepository;

    private final WorkoutInstanceMapper workoutInstanceMapper;

    private final ExerciseInstanceMapper exerciseInstanceMapper;

    private final ExerciseInstanceService exerciseInstanceService;

    public WorkoutInstanceService(WorkoutTemplateRepository workoutTemplateRepository, WorkoutInstanceRepository workoutInstanceRepository, ExerciseInstanceRepository exerciseInstanceRepository, UserWorkoutInstanceRepository userWorkoutInstanceRepository, UserExerciseInstanceRepository userExerciseInstanceRepository, WorkoutInstanceMapper workoutInstanceMapper, ExerciseInstanceMapper exerciseInstanceMapper, ExerciseInstanceService exerciseInstanceService) {
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.workoutInstanceRepository = workoutInstanceRepository;
        this.exerciseInstanceRepository = exerciseInstanceRepository;
        this.userWorkoutInstanceRepository = userWorkoutInstanceRepository;
        this.userExerciseInstanceRepository = userExerciseInstanceRepository;
        this.workoutInstanceMapper = workoutInstanceMapper;
        this.exerciseInstanceMapper = exerciseInstanceMapper;
        this.exerciseInstanceService = exerciseInstanceService;
    }

    /**
     * Save a workoutInstance.
     *
     * @param workoutInstanceDTO the entity to save
     * @return the persisted entity
     */
    public WorkoutInstanceDTO save(WorkoutInstanceDTO workoutInstanceDTO) {
        log.debug("Request to save WorkoutInstance : {}", workoutInstanceDTO);
        WorkoutInstance workoutInstance = workoutInstanceMapper.workoutInstanceDTOToWorkoutInstance(workoutInstanceDTO);

        removeDereferencedExerciseInstances(workoutInstance);

        workoutInstance.setExerciseInstances(new HashSet<>());
        workoutInstance = workoutInstanceRepository.save(workoutInstance);
        addWorkoutInstanceToParent(workoutInstance);

        List<ExerciseInstanceDTO> exerciseInstanceDTOs = workoutInstanceDTO.getExerciseInstances();

        if (exerciseInstanceDTOs != null && exerciseInstanceDTOs.size() > 0) {
            List<ExerciseInstanceDTO> savedExerciseInstanceDTOs = new ArrayList<>();
            for (ExerciseInstanceDTO exerciseInstanceDTO : exerciseInstanceDTOs) {
                exerciseInstanceDTO.setWorkoutInstanceId(workoutInstance.getId());
                savedExerciseInstanceDTOs.add(exerciseInstanceService.save(exerciseInstanceDTO));
            }
            workoutInstance.setExerciseInstances(new HashSet<>(exerciseInstanceMapper.exerciseInstanceDTOsToExerciseInstances(savedExerciseInstanceDTOs)));
        }

        WorkoutInstanceDTO result = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        return result;
    }

    public void addWorkoutInstanceToParent(WorkoutInstance workoutInstance) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOne((workoutInstance.getWorkoutTemplate()).getId());
        workoutTemplate.addWorkoutInstance(workoutInstance);
        workoutTemplateRepository.save(workoutTemplate);
    }


    public void removeDereferencedExerciseInstances(WorkoutInstance workoutInstance) {
        if (workoutInstance.getId() != null) {
            WorkoutInstance dbWorkoutInstance = workoutInstanceRepository.findOne(workoutInstance.getId());
            if (dbWorkoutInstance != null) {
                Set<ExerciseInstance> updatedExerciseInstanceSets = workoutInstance.getExerciseInstances();
                for (ExerciseInstance exerciseInstance : dbWorkoutInstance.getExerciseInstances()) {
                    if (!updatedExerciseInstanceSets.contains(exerciseInstance)) {
                        for (UserExerciseInstance userExerciseInstance : exerciseInstance.getUserExerciseInstances()) {
                            userExerciseInstance.setExerciseInstance(null);
                            userExerciseInstanceRepository.save(userExerciseInstance);
                        }
                        exerciseInstanceRepository.delete(exerciseInstance);
                    }
                }
            }
        }
    }

    /**
     *  Get all the workoutInstances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutInstances");
        Page<WorkoutInstance> result = workoutInstanceRepository.findAll(pageable);
        return result.map(workoutInstance -> workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance));
    }

    /**
     *  Get one workoutInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkoutInstanceDTO findOne(Long id) {
        log.debug("Request to get WorkoutInstance : {}", id);
        WorkoutInstance workoutInstance = workoutInstanceRepository.findOne(id);
        WorkoutInstanceDTO workoutInstanceDTO = workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
        return workoutInstanceDTO;
    }

    /**
     *  Delete the  workoutInstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkoutInstance : {}", id);
        removeWorkoutInstanceFromRelatedItems(id);
        workoutInstanceRepository.delete(id);
    }

    public void removeWorkoutInstanceFromRelatedItems(Long id) {
        WorkoutInstance workoutInstance = workoutInstanceRepository.findOne(id);
        if (workoutInstance != null) {
            WorkoutTemplate workoutTemplate = workoutInstance.getWorkoutTemplate();
            workoutTemplate.removeWorkoutInstance(workoutInstance);
            for (UserWorkoutInstance userWorkoutInstance :
                workoutInstance.getUserWorkoutInstances()) {
                userWorkoutInstance.setWorkoutInstance(null);
                userWorkoutInstanceRepository.save(userWorkoutInstance);
            }
            workoutTemplateRepository.save(workoutTemplate);
        }
    }
}
