package com.thefitnation.service;

import com.thefitnation.domain.*;
import com.thefitnation.repository.*;
import com.thefitnation.security.*;
import com.thefitnation.service.dto.*;
import com.thefitnation.service.mapper.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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

    public ExerciseInstanceSetService(ExerciseInstanceSetRepository exerciseInstanceSetRepository,
                                      ExerciseInstanceRepository exerciseInstanceRepository,
                                      UserExerciseInstanceSetRepository userExerciseInstanceSetRepository,
                                      ExerciseInstanceSetMapper exerciseInstanceSetMapper) {
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
        return exerciseInstanceSetMapper.exerciseInstanceSetToExerciseInstanceSetDTO(exerciseInstanceSet);
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

        String login = SecurityUtils.getCurrentUserLogin();

        Page<ExerciseInstanceSet> result = exerciseInstanceSetRepository.findAll(login, pageable);
        return result.map(exerciseInstanceSetMapper::exerciseInstanceSetToExerciseInstanceSetDTO);
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

    private void removeExerciseInstanceSetFromRelatedItems(Long id) {
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

    private void addExerciseInstanceSetToParent(ExerciseInstanceSet exerciseInstanceSet) {
        ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne((exerciseInstanceSet.getExerciseInstance()).getId());
        exerciseInstance.addExerciseInstanceSet(exerciseInstanceSet);
        exerciseInstanceRepository.save(exerciseInstance);
    }
}
