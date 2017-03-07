package com.thefitnation.service;

import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.repository.ExerciseInstanceSetRepository;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.ExerciseInstanceSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ExerciseInstanceSet.
 */
@Service
@Transactional
public class ExerciseInstanceSetService {

    private final Logger log = LoggerFactory.getLogger(ExerciseInstanceSetService.class);
    
    private final ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    private final ExerciseInstanceSetMapper exerciseInstanceSetMapper;

    public ExerciseInstanceSetService(ExerciseInstanceSetRepository exerciseInstanceSetRepository, ExerciseInstanceSetMapper exerciseInstanceSetMapper) {
        this.exerciseInstanceSetRepository = exerciseInstanceSetRepository;
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
        exerciseInstanceSetRepository.delete(id);
    }
}
