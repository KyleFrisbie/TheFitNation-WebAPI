package com.thefitnation.service;

import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.domain.WorkoutInstance;
import com.thefitnation.repository.ExerciseInstanceRepository;
import com.thefitnation.repository.ExerciseInstanceSetRepository;
import com.thefitnation.repository.UserExerciseInstanceSetRepository;
import com.thefitnation.repository.WorkoutInstanceRepository;
import com.thefitnation.service.dto.ExerciseInstanceDTO;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.ExerciseInstanceMapper;
import com.thefitnation.service.mapper.ExerciseInstanceSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private final UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    private final ExerciseInstanceMapper exerciseInstanceMapper;

    private final ExerciseInstanceSetMapper exerciseInstanceSetMapper;

    public ExerciseInstanceService(ExerciseInstanceRepository exerciseInstanceRepository, WorkoutInstanceRepository workoutInstanceRepository, ExerciseInstanceSetRepository exerciseInstanceSetRepository, UserExerciseInstanceSetRepository userExerciseInstanceSetRepository, ExerciseInstanceMapper exerciseInstanceMapper, ExerciseInstanceSetMapper exerciseInstanceSetMapper) {
        this.exerciseInstanceRepository = exerciseInstanceRepository;
        this.workoutInstanceRepository = workoutInstanceRepository;
        this.exerciseInstanceSetRepository = exerciseInstanceSetRepository;
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
        this.exerciseInstanceMapper = exerciseInstanceMapper;
        this.exerciseInstanceSetMapper = exerciseInstanceSetMapper;
    }

    /**
     * Save a exerciseInstance.
     *
     * @param exerciseInstanceDTO the entity to save
     * @return the persisted entity
     */
    public ExerciseInstanceDTO save(ExerciseInstanceDTO exerciseInstanceDTO) {
        log.debug("Request to save ExerciseInstance : {}", exerciseInstanceDTO);
        ExerciseInstance exerciseInstance = exerciseInstanceMapper.exerciseInstanceDTOToExerciseInstance(exerciseInstanceDTO);

        removeDereferencedExerciseInstanceSets(exerciseInstance);

        exerciseInstance.setExerciseInstanceSets(null);
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

        ExerciseInstanceDTO result = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        return result;
    }

    public void addExerciseInstanceToParent(ExerciseInstance exerciseInstance) {
        WorkoutInstance workoutInstance = workoutInstanceRepository.findOne((exerciseInstance.getWorkoutInstance()).getId());
        workoutInstance.addExerciseInstance(exerciseInstance);
        workoutInstanceRepository.save(workoutInstance);
    }

    public void removeDereferencedExerciseInstanceSets(ExerciseInstance exerciseInstance) {
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

    /**
     * Get all the exerciseInstances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExerciseInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseInstances");
        Page<ExerciseInstance> result = exerciseInstanceRepository.findAll(pageable);
        return result.map(exerciseInstance -> exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance));
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
        ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne(id);
        ExerciseInstanceDTO exerciseInstanceDTO = exerciseInstanceMapper.exerciseInstanceToExerciseInstanceDTO(exerciseInstance);
        return exerciseInstanceDTO;
    }

    /**
     * Delete the  exerciseInstance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExerciseInstance : {}", id);
        ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne(id);
        removeExerciseInstanceFromWorkoutInstance(exerciseInstance);
        exerciseInstanceRepository.delete(exerciseInstance);
    }


    public void removeExerciseInstanceFromWorkoutInstance(ExerciseInstance exerciseInstance) {
        log.debug("Request to remove ExerciseInstance from WorkoutInstance : {}", exerciseInstance.getId());
        WorkoutInstance workoutInstance = exerciseInstance.getWorkoutInstance();
        workoutInstance.removeExerciseInstance(exerciseInstance);
        workoutInstanceRepository.save(workoutInstance);
    }
}
