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
    private final UserRepository userRepository;

    public UserExerciseInstanceSetService(UserExerciseInstanceSetRepository userExerciseInstanceSetRepository, ExerciseInstanceSetRepository exerciseInstanceSetRepository, UserExerciseInstanceRepository userExerciseInstanceRepository, UserExerciseInstanceSetMapper userExerciseInstanceSetMapper, UserRepository userRepository) {
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
        this.exerciseInstanceSetRepository = exerciseInstanceSetRepository;
        this.userExerciseInstanceRepository = userExerciseInstanceRepository;
        this.userExerciseInstanceSetMapper = userExerciseInstanceSetMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a userExerciseInstanceSet.
     *
     * @param userExerciseInstanceSetDTO the entity to save
     * @return the persisted entity
     */
    public UserExerciseInstanceSetDTO save(UserExerciseInstanceSetDTO userExerciseInstanceSetDTO) {
        log.debug("Request to save UserExerciseInstanceSet : {}", userExerciseInstanceSetDTO);
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (!user.isPresent()) {
            return null;
        }
        UserExerciseInstance userExerciseInstance = userExerciseInstanceRepository.findOne(user.get().getLogin(), userExerciseInstanceSetDTO.getUserExerciseInstanceId());
        if (userExerciseInstance == null) {
            return null;
        }
        UserExerciseInstanceSet userExerciseInstanceSet = userExerciseInstanceSetMapper.userExerciseInstanceSetDTOToUserExerciseInstanceSet(userExerciseInstanceSetDTO);
        userExerciseInstanceSet = userExerciseInstanceSetRepository.save(userExerciseInstanceSet);
        addUserExerciseInstanceSetToParent(userExerciseInstanceSet);
        return userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
    }

    /**
     * Get all the userExerciseInstanceSets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserExerciseInstanceSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExerciseInstanceSets");
        Page<UserExerciseInstanceSet> result = userExerciseInstanceSetRepository.findAll(SecurityUtils.getCurrentUserLogin(), pageable);
        return result.map(userExerciseInstanceSetMapper::userExerciseInstanceSetToUserExerciseInstanceSetDTO);
    }

    /**
     * Get one userExerciseInstanceSet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserExerciseInstanceSetDTO findOne(Long id) {
        log.debug("Request to get UserExerciseInstanceSet : {}", id);
        UserExerciseInstanceSet userExerciseInstanceSet = userExerciseInstanceSetRepository.findOne(SecurityUtils.getCurrentUserLogin(), id);
        return userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
    }

    /**
     * Delete the  userExerciseInstanceSet by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExerciseInstanceSet : {}", id);
        if (userExerciseInstanceSetRepository.findOne(SecurityUtils.getCurrentUserLogin(), id) != null) {
            removeUserExerciseInstanceSetFromRelatedItems(id);
            userExerciseInstanceSetRepository.delete(id);
        }
    }

    private void removeUserExerciseInstanceSetFromRelatedItems(Long id) {
        UserExerciseInstanceSet userExerciseInstanceSet = userExerciseInstanceSetRepository.findOne(id);
        if (userExerciseInstanceSet != null) {
            log.debug("Request to remove UserExerciseInstanceSet from UserExerciseInstance : {}", userExerciseInstanceSet.getId());
            UserExerciseInstance userExerciseInstance = userExerciseInstanceSet.getUserExerciseInstance();
            userExerciseInstance.removeUserExerciseInstanceSet(userExerciseInstanceSet);
            userExerciseInstanceRepository.save(userExerciseInstance);

            if (userExerciseInstanceSet.getExerciseInstanceSet() != null) {
                ExerciseInstanceSet exerciseInstanceSet = userExerciseInstanceSet.getExerciseInstanceSet();
                exerciseInstanceSet.removeUserExerciseInstanceSet(userExerciseInstanceSet);
                exerciseInstanceSetRepository.save(exerciseInstanceSet);
            }
        }
    }

    private void addUserExerciseInstanceSetToParent(UserExerciseInstanceSet userExerciseInstanceSet) {
        UserExerciseInstance userExerciseInstance = userExerciseInstanceRepository.findOne((userExerciseInstanceSet.getUserExerciseInstance()).getId());
        userExerciseInstance.addUserExerciseInstanceSet(userExerciseInstanceSet);
        userExerciseInstanceRepository.save(userExerciseInstance);

        if (userExerciseInstanceSet.getExerciseInstanceSet() != null) {
            ExerciseInstanceSet exerciseInstanceSet = exerciseInstanceSetRepository.findOne((userExerciseInstanceSet.getExerciseInstanceSet()).getId());
            exerciseInstanceSet.addUserExerciseInstanceSet(userExerciseInstanceSet);
            exerciseInstanceSetRepository.save(exerciseInstanceSet);
        }
    }
}
