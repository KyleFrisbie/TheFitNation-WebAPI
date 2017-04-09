package com.thefitnation.service;

import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.repository.ExerciseInstanceRepository;
import com.thefitnation.repository.ExerciseInstanceSetRepository;
import com.thefitnation.repository.UserExerciseInstanceSetRepository;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.ExerciseInstanceSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing ExerciseInstanceSet.
 */
@Service
@Transactional
public class ExerciseInstanceSetService {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceSetService.class);

    private final ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    private final ExerciseInstanceRepository exerciseInstanceRepository;

    private final UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    private final ExerciseInstanceSetMapper exerciseInstanceSetMapper;

    public ExerciseInstanceSetService(ExerciseInstanceSetRepository exerciseInstanceSetRepository, ExerciseInstanceRepository exerciseInstanceRepository, UserExerciseInstanceSetRepository userExerciseInstanceSetRepository, ExerciseInstanceSetMapper exerciseInstanceSetMapper) {
        this.exerciseInstanceSetRepository = exerciseInstanceSetRepository;
        this.exerciseInstanceRepository = exerciseInstanceRepository;
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
        this.exerciseInstanceSetMapper = exerciseInstanceSetMapper;
    }

    /**
     * Save a exerciseInstanceSet.
     *
     * @param exerciseInstanceSetDTO the entity to save
     * @return the persisted entity
     */
    public ExerciseInstanceSetDTO save(ExerciseInstanceSetDTO exerciseInstanceSetDTO) {
        log.debug("Request to save ExerciseInstanceSet : {}", exerciseInstanceSetDTO);
        ExerciseInstanceSet exerciseInstanceSet = exerciseInstanceSetMapper.exerciseInstanceSetDTOToExerciseInstanceSet(exerciseInstanceSetDTO);
        exerciseInstanceSet = exerciseInstanceSetRepository.save(exerciseInstanceSet);
        addExerciseInstanceSetToParent(exerciseInstanceSet);
        ExerciseInstanceSetDTO result = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
        return result;
    }

    public void addExerciseInstanceSetToParent(ExerciseInstanceSet exerciseInstanceSet) {
        ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne((exerciseInstanceSet.getExerciseInstance()).getId());
        exerciseInstance.addExerciseInstanceSet(exerciseInstanceSet);
        exerciseInstanceRepository.save(exerciseInstance);
    }

    /**
     *  Get all the exerciseInstanceSets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExerciseInstanceSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseInstanceSets");
        Page<ExerciseInstanceSet> result = exerciseInstanceSetRepository.findAll(pageable);
        return result.map(exerciseInstanceSet -> exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet));
    }

    /**
     *  Get one exerciseInstanceSet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ExerciseInstanceSetDTO findOne(Long id) {
        log.debug("Request to get ExerciseInstanceSet : {}", id);
        ExerciseInstanceSet exerciseInstanceSet = exerciseInstanceSetRepository.findOne(id);
        ExerciseInstanceSetDTO exerciseInstanceSetDTO = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
        return exerciseInstanceSetDTO;
    }

    /**
     *  Delete the  exerciseInstanceSet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExerciseInstanceSet : {}", id);
        removeExerciseInstanceSetFromRelatedItems(id);
        exerciseInstanceSetRepository.delete(id);
    }

    public void removeExerciseInstanceSetFromRelatedItems(Long id) {
        ExerciseInstanceSet exerciseInstanceSet  = exerciseInstanceSetRepository.findOne(id);
        if (exerciseInstanceSet != null) {
            log.debug("Request to remove ExerciseInstanceSet from ExerciseInstance : {}", exerciseInstanceSet.getId());
            ExerciseInstance exerciseInstance = exerciseInstanceSet.getExerciseInstance();
            exerciseInstance.removeExerciseInstanceSet(exerciseInstanceSet);
            for (UserExerciseInstanceSet userExerciseInstanceSet :
                exerciseInstanceSet.getUserExerciseInstanceSets()) {
                userExerciseInstanceSet.setExerciseInstanceSet(null);
                userExerciseInstanceSetRepository.save(userExerciseInstanceSet);
            }
            exerciseInstanceRepository.save(exerciseInstance);
        }
    }
}
