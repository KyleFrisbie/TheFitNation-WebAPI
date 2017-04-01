package com.thefitnation.service;

import com.thefitnation.domain.ExerciseInstance;
import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.repository.ExerciseInstanceRepository;
import com.thefitnation.repository.ExerciseInstanceSetRepository;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.ExerciseInstanceSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing ExerciseInstanceSet.
 */
@Service
@Transactional
public class ExerciseInstanceSetService {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceSetService.class);

    private final ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    private final ExerciseInstanceRepository exerciseInstanceRepository;

    private final ExerciseInstanceSetMapper exerciseInstanceSetMapper;

    public ExerciseInstanceSetService(ExerciseInstanceSetRepository exerciseInstanceSetRepository, ExerciseInstanceRepository exerciseInstanceRepository, ExerciseInstanceSetMapper exerciseInstanceSetMapper) {
        this.exerciseInstanceSetRepository = exerciseInstanceSetRepository;
        this.exerciseInstanceRepository = exerciseInstanceRepository;
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
        ExerciseInstanceSetDTO result = exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
        return result;
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
        ExerciseInstanceSet exerciseInstanceSet  = exerciseInstanceSetRepository.findOne(id);
        removeExerciseInstanceSetFromExerciseInstance(exerciseInstanceSet);
        exerciseInstanceSetRepository.delete(exerciseInstanceSet);
    }

    /**
     * Remove exerciseInstanceSet from exerciseInstance by exerciseInstanceSet
     *
     * @param exerciseInstanceSet
     */
    public void removeExerciseInstanceSetFromExerciseInstance(ExerciseInstanceSet exerciseInstanceSet) {
        log.debug("Request to remove ExerciseInstanceSet from ExerciseInstance : {}", exerciseInstanceSet.getId());
        ExerciseInstance exerciseInstance = exerciseInstanceSet.getExerciseInstance();
        exerciseInstance.removeExerciseInstanceSet(exerciseInstanceSet);
        exerciseInstanceRepository.save(exerciseInstance);
    }
}
