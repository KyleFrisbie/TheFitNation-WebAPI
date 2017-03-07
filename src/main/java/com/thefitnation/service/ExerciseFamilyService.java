package com.thefitnation.service;

import com.thefitnation.domain.ExerciseFamily;
import com.thefitnation.repository.ExerciseFamilyRepository;
import com.thefitnation.service.dto.ExerciseFamilyDTO;
import com.thefitnation.service.mapper.ExerciseFamilyMapper;
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
 * Service Implementation for managing ExerciseFamily.
 */
@Service
@Transactional
public class ExerciseFamilyService {

    private final Logger log = LoggerFactory.getLogger(ExerciseFamilyService.class);
    
    private final ExerciseFamilyRepository exerciseFamilyRepository;

    private final ExerciseFamilyMapper exerciseFamilyMapper;

    public ExerciseFamilyService(ExerciseFamilyRepository exerciseFamilyRepository, ExerciseFamilyMapper exerciseFamilyMapper) {
        this.exerciseFamilyRepository = exerciseFamilyRepository;
        this.exerciseFamilyMapper = exerciseFamilyMapper;
    }

    /**
     * Save a exerciseFamily.
     *
     * @param exerciseFamilyDTO the entity to save
     * @return the persisted entity
     */
    public ExerciseFamilyDTO save(ExerciseFamilyDTO exerciseFamilyDTO) {
        log.debug("Request to save ExerciseFamily : {}", exerciseFamilyDTO);
        ExerciseFamily exerciseFamily = exerciseFamilyMapper.exerciseFamilyDTOToExerciseFamily(exerciseFamilyDTO);
        exerciseFamily = exerciseFamilyRepository.save(exerciseFamily);
        ExerciseFamilyDTO result = exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(exerciseFamily);
        return result;
    }

    /**
     *  Get all the exerciseFamilies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExerciseFamilyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseFamilies");
        Page<ExerciseFamily> result = exerciseFamilyRepository.findAll(pageable);
        return result.map(exerciseFamily -> exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(exerciseFamily));
    }

    /**
     *  Get one exerciseFamily by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ExerciseFamilyDTO findOne(Long id) {
        log.debug("Request to get ExerciseFamily : {}", id);
        ExerciseFamily exerciseFamily = exerciseFamilyRepository.findOne(id);
        ExerciseFamilyDTO exerciseFamilyDTO = exerciseFamilyMapper.exerciseFamilyToExerciseFamilyDTO(exerciseFamily);
        return exerciseFamilyDTO;
    }

    /**
     *  Delete the  exerciseFamily by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExerciseFamily : {}", id);
        exerciseFamilyRepository.delete(id);
    }
}
