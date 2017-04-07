package com.thefitnation.service;

import com.thefitnation.domain.ExerciseInstanceSet;
import com.thefitnation.domain.UserExerciseInstance;
import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.repository.ExerciseInstanceSetRepository;
import com.thefitnation.repository.UserExerciseInstanceRepository;
import com.thefitnation.repository.UserExerciseInstanceSetRepository;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.UserExerciseInstanceSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing UserExerciseInstanceSet.
 */
@Service
@Transactional
public class UserExerciseInstanceSetService {

    private final Logger log = LoggerFactory.getLogger(UserExerciseInstanceSetService.class);

    private final UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    private final ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    private final UserExerciseInstanceRepository userExerciseInstanceRepository;

    private final UserExerciseInstanceSetMapper userExerciseInstanceSetMapper;

    public UserExerciseInstanceSetService(UserExerciseInstanceSetRepository userExerciseInstanceSetRepository, ExerciseInstanceSetRepository exerciseInstanceSetRepository, UserExerciseInstanceRepository userExerciseInstanceRepository, UserExerciseInstanceSetMapper userExerciseInstanceSetMapper) {
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
        this.exerciseInstanceSetRepository = exerciseInstanceSetRepository;
        this.userExerciseInstanceRepository = userExerciseInstanceRepository;
        this.userExerciseInstanceSetMapper = userExerciseInstanceSetMapper;
    }

    /**
     * Save a userExerciseInstanceSet.
     *
     * @param userExerciseInstanceSetDTO the entity to save
     * @return the persisted entity
     */
    public UserExerciseInstanceSetDTO save(UserExerciseInstanceSetDTO userExerciseInstanceSetDTO) {
        log.debug("Request to save UserExerciseInstanceSet : {}", userExerciseInstanceSetDTO);
        UserExerciseInstanceSet userExerciseInstanceSet = userExerciseInstanceSetMapper.userExerciseInstanceSetDTOToUserExerciseInstanceSet(userExerciseInstanceSetDTO);
        userExerciseInstanceSet = userExerciseInstanceSetRepository.save(userExerciseInstanceSet);
addUserExerciseInstanceSetToParent(userExerciseInstanceSet);
        UserExerciseInstanceSetDTO result = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
        return result;
    }

    public void addUserExerciseInstanceSetToParent(UserExerciseInstanceSet userExerciseInstanceSet) {
        UserExerciseInstance userExerciseInstance = userExerciseInstanceRepository.findOne((userExerciseInstanceSet.getUserExerciseInstance()).getId());
        userExerciseInstance.addUserExerciseInstanceSet(userExerciseInstanceSet);
        userExerciseInstanceRepository.save(userExerciseInstance);

        if(userExerciseInstanceSet.getExerciseInstanceSet() != null) {
            ExerciseInstanceSet exerciseInstanceSet = exerciseInstanceSetRepository.findOne((userExerciseInstanceSet.getExerciseInstanceSet()).getId());
            exerciseInstanceSet.addUserExerciseInstanceSet(userExerciseInstanceSet);
            exerciseInstanceSetRepository.save(exerciseInstanceSet);
        }
    }

    /**
     *  Get all the userExerciseInstanceSets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserExerciseInstanceSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExerciseInstanceSets");
        Page<UserExerciseInstanceSet> result = userExerciseInstanceSetRepository.findAll(pageable);
        return result.map(userExerciseInstanceSet -> userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet));
    }

    /**
     *  Get one userExerciseInstanceSet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserExerciseInstanceSetDTO findOne(Long id) {
        log.debug("Request to get UserExerciseInstanceSet : {}", id);
        UserExerciseInstanceSet userExerciseInstanceSet = userExerciseInstanceSetRepository.findOne(id);
        UserExerciseInstanceSetDTO userExerciseInstanceSetDTO = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
        return userExerciseInstanceSetDTO;
    }

    /**
     *  Delete the  userExerciseInstanceSet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExerciseInstanceSet : {}", id);
        removeUserExerciseInstanceSetFromRelatedItems(id);
        userExerciseInstanceSetRepository.delete(id);
    }

    public void removeUserExerciseInstanceSetFromRelatedItems(Long id) {
        UserExerciseInstanceSet userExerciseInstanceSet  = userExerciseInstanceSetRepository.findOne(id);
        if (userExerciseInstanceSet != null) {
            log.debug("Request to remove UserExerciseInstanceSet from UserExerciseInstance : {}", userExerciseInstanceSet.getId());
            UserExerciseInstance userExerciseInstance = userExerciseInstanceSet.getUserExerciseInstance();
            userExerciseInstance.removeUserExerciseInstanceSet(userExerciseInstanceSet);
            userExerciseInstanceRepository.save(userExerciseInstance);

            if(userExerciseInstanceSet.getExerciseInstanceSet() != null) {
                ExerciseInstanceSet exerciseInstanceSet = userExerciseInstanceSet.getExerciseInstanceSet();
                exerciseInstanceSet.removeUserExerciseInstanceSet(userExerciseInstanceSet);
                exerciseInstanceSetRepository.save(exerciseInstanceSet);
            }
        }
    }
}
