package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.security.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

/**
 * Service Implementation for managing ExerciseInstance.
 */
@Service
@Transactional
public class ExerciseInstanceService {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceService.class);
    private final ExerciseInstanceRepository exerciseInstanceRepository;
    private final WorkoutInstanceRepository workoutInstanceRepository;
    private final ExerciseInstanceSetRepository exerciseInstanceSetRepository;
    private final UserExerciseInstanceRepository userExerciseInstanceRepository;
    private final UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;
    private final ExerciseInstanceMapper exerciseInstanceMapper;
    private final ExerciseInstanceSetMapper exerciseInstanceSetMapper;
    private final UserRepository userRepository;
    private final ExerciseInstanceSetService exerciseInstanceSetService;

    public ExerciseInstanceService(ExerciseInstanceRepository exerciseInstanceRepository,
                                   WorkoutInstanceRepository workoutInstanceRepository,
                                   ExerciseInstanceSetRepository exerciseInstanceSetRepository,
                                   UserExerciseInstanceRepository userExerciseInstanceRepository,
                                   UserExerciseInstanceSetRepository userExerciseInstanceSetRepository,
                                   ExerciseInstanceMapper exerciseInstanceMapper,
                                   ExerciseInstanceSetMapper exerciseInstanceSetMapper,
                                   UserRepository userRepository, ExerciseInstanceSetService exerciseInstanceSetService) {
        this.exerciseInstanceRepository = exerciseInstanceRepository;
        this.workoutInstanceRepository = workoutInstanceRepository;
        this.exerciseInstanceSetRepository = exerciseInstanceSetRepository;
        this.userExerciseInstanceRepository = userExerciseInstanceRepository;
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
        this.exerciseInstanceMapper = exerciseInstanceMapper;
        this.exerciseInstanceSetMapper = exerciseInstanceSetMapper;
        this.userRepository = userRepository;
        this.exerciseInstanceSetService = exerciseInstanceSetService;
    }

    /**
     * Save a exerciseInstance.
     *
     * @param exerciseInstanceDTO the entity to save
     * @return the persisted entity
     */
    public ExerciseInstanceDTO save(ExerciseInstanceDTO exerciseInstanceDTO) {
        log.debug("Request to save ExerciseInstance : {}", exerciseInstanceDTO);

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (user.isPresent()) {
            ExerciseInstance exerciseInstance = exerciseInstanceMapper.exerciseInstanceDTOToExerciseInstance(exerciseInstanceDTO);
            removeDereferenceExerciseInstanceSets(exerciseInstance);
            exerciseInstance.setExerciseInstanceSets(new HashSet<>());
            exerciseInstance = exerciseInstanceRepository.save(exerciseInstance);
            addExerciseInstanceToParent(exerciseInstance);
            List<ExerciseInstanceSetDTO> exerciseInstanceSetDTOs = exerciseInstanceDTO.getExerciseInstanceSets();

            if (exerciseInstanceSetDTOs != null && exerciseInstanceSetDTOs.size() > 0) {
                List<ExerciseInstanceSet> exerciseInstanceSets = exerciseInstanceSetMapper.exerciseInstanceSetDTOsToExerciseInstanceSets(exerciseInstanceSetDTOs);
                for (ExerciseInstanceSet exerciseInstanceSet : exerciseInstanceSets) {
                    exerciseInstanceSet.setExerciseInstance(exerciseInstance);
                }

                exerciseInstanceSets = exerciseInstanceSetRepository.save(exerciseInstanceSets);
                exerciseInstance.setExerciseInstanceSets(new HashSet<>(exerciseInstanceSets));
            }
            return exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        }
        return null;
    }

    /**
     * Get all the exerciseInstances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExerciseInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseInstances");
        String login = SecurityUtils.getCurrentUserLogin();
        Page<ExerciseInstance> result = exerciseInstanceRepository.findAll(login, pageable);
        return result.map(exerciseInstanceMapper::exerciseInstanceToExerciseInstanceDTO);
    }

    /**
     * Get one exerciseInstance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ExerciseInstanceDTO findOne(Long id) {
        log.debug("Request to get ExerciseInstance : {}", id);
        String login = SecurityUtils.getCurrentUserLogin();
        ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne(login, id);
        return exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
    }

    /**
     * Delete the  exerciseInstance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExerciseInstance : {}", id);
        if (exerciseInstanceRepository.findOne(SecurityUtils.getCurrentUserLogin(), id) != null){
            removeExerciseInstanceFromRelatedItems(id);
            exerciseInstanceRepository.delete(id);
        }
    }

    private void removeExerciseInstanceFromRelatedItems(Long id) {
        ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne(id);
        if (exerciseInstance != null) {
            Set<ExerciseInstanceSet> exerciseInstanceSets = new HashSet<>(exerciseInstance.getExerciseInstanceSets());
            for (Iterator<ExerciseInstanceSet> iterator = exerciseInstanceSets.iterator(); iterator.hasNext();) {
                ExerciseInstanceSet exerciseInstanceSet = iterator.next();
                iterator.remove();
                exerciseInstanceSetService.delete(exerciseInstanceSet.getId());
            }
            WorkoutInstance workoutInstance = exerciseInstance.getWorkoutInstance();
            workoutInstance.removeExerciseInstance(exerciseInstance);
            for (UserExerciseInstance userExerciseInstance :
                exerciseInstance.getUserExerciseInstances()) {
                userExerciseInstance.setExerciseInstance(null);
                userExerciseInstanceRepository.save(userExerciseInstance);
            }
            workoutInstanceRepository.save(workoutInstance);
        }
    }

    private void addExerciseInstanceToParent(ExerciseInstance exerciseInstance) {
        WorkoutInstance workoutInstance = workoutInstanceRepository.findOne((exerciseInstance.getWorkoutInstance()).getId());
        workoutInstance.addExerciseInstance(exerciseInstance);
        workoutInstanceRepository.save(workoutInstance);
    }

    private void removeDereferenceExerciseInstanceSets(ExerciseInstance exerciseInstance) {
        if (exerciseInstance.getId() != null) {
            ExerciseInstance dbExerciseInstance = exerciseInstanceRepository.findOne(exerciseInstance.getId());
            if (dbExerciseInstance != null) {
                Set<ExerciseInstanceSet> updatedExerciseInstanceSets = exerciseInstance.getExerciseInstanceSets();
                for (ExerciseInstanceSet exerciseInstanceSet : dbExerciseInstance.getExerciseInstanceSets()) {
                    if (!updatedExerciseInstanceSets.contains(exerciseInstanceSet)) {
                        for (UserExerciseInstanceSet userExerciseInstanceSet : exerciseInstanceSet.getUserExerciseInstanceSets()) {
                            userExerciseInstanceSet.setExerciseInstanceSet(null);
                            userExerciseInstanceSetRepository.save(userExerciseInstanceSet);
                        }
                        exerciseInstanceSetRepository.delete(exerciseInstanceSet);
                    }
                }
            }
        }
    }
}
