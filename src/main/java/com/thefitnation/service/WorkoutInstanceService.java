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
    private final UserRepository userRepository;

    public WorkoutInstanceService(WorkoutTemplateRepository workoutTemplateRepository,
                                  WorkoutInstanceRepository workoutInstanceRepository,
                                  ExerciseInstanceRepository exerciseInstanceRepository,
                                  UserWorkoutInstanceRepository userWorkoutInstanceRepository,
                                  UserExerciseInstanceRepository userExerciseInstanceRepository,
                                  WorkoutInstanceMapper workoutInstanceMapper,
                                  ExerciseInstanceMapper exerciseInstanceMapper,
                                  ExerciseInstanceService exerciseInstanceService,
                                  UserRepository userRepository) {
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.workoutInstanceRepository = workoutInstanceRepository;
        this.exerciseInstanceRepository = exerciseInstanceRepository;
        this.userWorkoutInstanceRepository = userWorkoutInstanceRepository;
        this.userExerciseInstanceRepository = userExerciseInstanceRepository;
        this.workoutInstanceMapper = workoutInstanceMapper;
        this.exerciseInstanceMapper = exerciseInstanceMapper;
        this.exerciseInstanceService = exerciseInstanceService;
        this.userRepository = userRepository;
    }

    /**x
     * Save a workoutInstance.
     *
     * @param workoutInstanceDTO the entity to save
     * @return the persisted entity
     */
    public WorkoutInstanceDTO save(WorkoutInstanceDTO workoutInstanceDTO) {
        log.debug("Request to save WorkoutInstance : {}", workoutInstanceDTO);

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (user.isPresent()) {
            if (!workoutTemplateRepository
                .findOne(workoutInstanceDTO.getWorkoutTemplateId())
                .getUserDemographic()
                .getUser()
                .getLogin().equals(SecurityUtils.getCurrentUserLogin())) {
                return null;
            }
            if (workoutInstanceDTO.getWorkoutTemplateId() == null) {
                WorkoutInstance workoutInstance = workoutInstanceMapper.workoutInstanceDTOToWorkoutInstance(workoutInstanceDTO);
                workoutInstance.setCreatedOn(LocalDate.now());
                workoutInstance.setLastUpdated(LocalDate.now());

                removeDereferenceExerciseInstances(workoutInstance);
                workoutInstance.setExerciseInstances(new HashSet<>());
                workoutInstance = workoutInstanceRepository.save(workoutInstance);
                addWorkoutInstanceToParent(workoutInstance);

                List<ExerciseInstanceDTO> exerciseInstanceDTOs = workoutInstanceDTO.getExerciseInstances();
                exerciseInstanceDTOExists(workoutInstance, exerciseInstanceDTOs);
                return workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
            } else {
                WorkoutInstance workoutInstance = workoutInstanceMapper.workoutInstanceDTOToWorkoutInstance(workoutInstanceDTO);

                workoutInstance.setLastUpdated(LocalDate.now());

                removeDereferenceExerciseInstances(workoutInstance);
                workoutInstance.setExerciseInstances(new HashSet<>());
                workoutInstance = workoutInstanceRepository.save(workoutInstance);
                addWorkoutInstanceToParent(workoutInstance);

                List<ExerciseInstanceDTO> exerciseInstanceDTOs = workoutInstanceDTO.getExerciseInstances();

                exerciseInstanceDTOExists(workoutInstance, exerciseInstanceDTOs);

                return workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
            }
        }
        return null;
    }

    /**
     *  Get all the workoutInstances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkoutInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutInstances by logged in user");
        String login = SecurityUtils.getCurrentUserLogin();
        Page<WorkoutInstance> result = workoutInstanceRepository.findAll(login, pageable);
        return result.map(workoutInstanceMapper::workoutInstanceToWorkoutInstanceDTO);
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
        String login = SecurityUtils.getCurrentUserLogin();
        WorkoutInstance workoutInstance = workoutInstanceRepository.findOne(login, id);
        return workoutInstanceMapper.workoutInstanceToWorkoutInstanceDTO(workoutInstance);
    }

    /**
     *  Delete the  workoutInstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkoutInstance : {}", id);
        if (workoutInstanceRepository.findOne(SecurityUtils.getCurrentUserLogin(), id).getId() != null){
            removeWorkoutInstanceFromRelatedItems(id);
            workoutInstanceRepository.delete(id);
        }
    }

    private void exerciseInstanceDTOExists(WorkoutInstance workoutInstance, List<ExerciseInstanceDTO> exerciseInstanceDTOs) {
        if (exerciseInstanceDTOs != null && exerciseInstanceDTOs.size() > 0) {
            List<ExerciseInstanceDTO> savedExerciseInstanceDTOs = new ArrayList<>();
            for (ExerciseInstanceDTO exerciseInstanceDTO : exerciseInstanceDTOs) {
                exerciseInstanceDTO.setWorkoutInstanceId(workoutInstance.getId());
                savedExerciseInstanceDTOs.add(exerciseInstanceService.save(exerciseInstanceDTO));
            }
            workoutInstance.setExerciseInstances(new HashSet<>(exerciseInstanceMapper.exerciseInstanceDTOsToExerciseInstances(savedExerciseInstanceDTOs)));
        }
    }

    private void removeWorkoutInstanceFromRelatedItems(Long id) {
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

    private void addWorkoutInstanceToParent(WorkoutInstance workoutInstance) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findOne((workoutInstance.getWorkoutTemplate()).getId());
        workoutTemplate.addWorkoutInstance(workoutInstance);
        workoutTemplateRepository.save(workoutTemplate);
    }

    private void removeDereferenceExerciseInstances(WorkoutInstance workoutInstance) {
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
}
