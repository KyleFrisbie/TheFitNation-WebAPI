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
 * Service Implementation for managing UserExerciseInstance.
 */
@Service
@Transactional
public class UserExerciseInstanceService {

    private final Logger log = LoggerFactory.getLogger(UserExerciseInstanceService.class);

    private final UserExerciseInstanceRepository userExerciseInstanceRepository;
    private final UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;
    private final UserWorkoutInstanceRepository userWorkoutInstanceRepository;
    private final ExerciseInstanceRepository exerciseInstanceRepository;
    private final UserExerciseInstanceMapper userExerciseInstanceMapper;
    private final UserExerciseInstanceSetMapper userExerciseInstanceSetMapper;
    private final UserExerciseInstanceSetService userExerciseInstanceSetService;
    private final UserRepository userRepository;

    public UserExerciseInstanceService(UserExerciseInstanceRepository userExerciseInstanceRepository, UserExerciseInstanceSetRepository userExerciseInstanceSetRepository, UserWorkoutInstanceRepository userWorkoutInstanceRepository, ExerciseInstanceRepository exerciseInstanceRepository, UserExerciseInstanceMapper userExerciseInstanceMapper, UserExerciseInstanceSetMapper userExerciseInstanceSetMapper, UserExerciseInstanceSetService userExerciseInstanceSetService, UserRepository userRepository) {
        this.userExerciseInstanceRepository = userExerciseInstanceRepository;
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
        this.userWorkoutInstanceRepository = userWorkoutInstanceRepository;
        this.exerciseInstanceRepository = exerciseInstanceRepository;
        this.userExerciseInstanceMapper = userExerciseInstanceMapper;
        this.userExerciseInstanceSetMapper = userExerciseInstanceSetMapper;
        this.userExerciseInstanceSetService = userExerciseInstanceSetService;
        this.userRepository = userRepository;
    }

    /**
     * Save a userExerciseInstance.
     *
     * @param userExerciseInstanceDTO the entity to save
     * @return the persisted entity
     */
    public UserExerciseInstanceDTO save(UserExerciseInstanceDTO userExerciseInstanceDTO) {
        log.debug("Request to save UserExerciseInstance : {}", userExerciseInstanceDTO);

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (!user.isPresent()) {
            return null;
        }
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceRepository.findOne(user.get().getLogin(), userExerciseInstanceDTO.getUserWorkoutInstanceId());
        if (userWorkoutInstance == null) {
            return null;
        }
        UserExerciseInstance userExerciseInstance = userExerciseInstanceMapper.userExerciseInstanceDTOToUserExerciseInstance(userExerciseInstanceDTO);

        removeDereferencedUserExerciseInstanceSets(userExerciseInstance);

        userExerciseInstance.setUserExerciseInstanceSets(new HashSet<>());
        userExerciseInstance = userExerciseInstanceRepository.save(userExerciseInstance);

        addUserExerciseInstanceToParent(userExerciseInstance);

        List<UserExerciseInstanceSetDTO> userExerciseInstanceSetDTOs = userExerciseInstanceDTO.getUserExerciseInstanceSets();

        if (userExerciseInstanceSetDTOs != null && userExerciseInstanceSetDTOs.size() > 0) {
            List<UserExerciseInstanceSet> userExerciseInstanceSets = userExerciseInstanceSetMapper.userExerciseInstanceSetDTOsToUserExerciseInstanceSets(userExerciseInstanceSetDTOs);

            for (UserExerciseInstanceSet userExerciseInstanceSet : userExerciseInstanceSets) {
                userExerciseInstanceSet.setUserExerciseInstance(userExerciseInstance);
            }

            userExerciseInstanceSets = userExerciseInstanceSetRepository.save(userExerciseInstanceSets);

            userExerciseInstance.setUserExerciseInstanceSets(new HashSet<>(userExerciseInstanceSets));
        }
        return userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
    }

    /**
     * Get all the userExerciseInstances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserExerciseInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExerciseInstances");
        Page<UserExerciseInstance> result = userExerciseInstanceRepository.findAll(SecurityUtils.getCurrentUserLogin(), pageable);
        return result.map(userExerciseInstanceMapper::userExerciseInstanceToUserExerciseInstanceDTO);
    }

    /**
     * Get one userExerciseInstance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserExerciseInstanceDTO findOne(Long id) {
        log.debug("Request to get UserExerciseInstance : {}", id);
        UserExerciseInstance userExerciseInstance = userExerciseInstanceRepository.findOne(SecurityUtils.getCurrentUserLogin(), id);
        return userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
    }

    /**
     * Delete the  userExerciseInstance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExerciseInstance : {}", id);

        if (userExerciseInstanceRepository.findOne(SecurityUtils.getCurrentUserLogin(), id) != null) {
            removeUserExerciseInstanceFromRelatedItems(id);
            userExerciseInstanceRepository.delete(id);
        }
    }


    private void removeUserExerciseInstanceFromRelatedItems(Long id) {
        UserExerciseInstance userExerciseInstance = userExerciseInstanceRepository.findOne(id);
        if (userExerciseInstance != null) {
            UserWorkoutInstance userWorkoutInstance = userExerciseInstance.getUserWorkoutInstance();
            userWorkoutInstance.removeUserExerciseInstance(userExerciseInstance);
            userWorkoutInstanceRepository.save(userWorkoutInstance);

            if (userExerciseInstance.getExerciseInstance() != null) {
                ExerciseInstance exerciseInstance = userExerciseInstance.getExerciseInstance();
                exerciseInstance.removeUserExerciseInstance(userExerciseInstance);
                exerciseInstanceRepository.save(exerciseInstance);
            }
        }
    }

    private void addUserExerciseInstanceToParent(UserExerciseInstance userExerciseInstance) {
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceRepository.findOne((userExerciseInstance.getUserWorkoutInstance()).getId());
        userWorkoutInstance.addUserExerciseInstance(userExerciseInstance);
        userWorkoutInstanceRepository.save(userWorkoutInstance);

        if (userExerciseInstance.getExerciseInstance() != null) {
            ExerciseInstance exerciseInstance = exerciseInstanceRepository.findOne((userExerciseInstance.getExerciseInstance()).getId());
            exerciseInstance.addUserExerciseInstance(userExerciseInstance);
            exerciseInstanceRepository.save(exerciseInstance);
        }
    }

    private void removeDereferencedUserExerciseInstanceSets(UserExerciseInstance userExerciseInstance) {
        if (userExerciseInstance.getId() != null) {
            UserExerciseInstance dbUserExerciseInstance = userExerciseInstanceRepository.findOne(userExerciseInstance.getId());
            if (dbUserExerciseInstance != null) {
                Set<UserExerciseInstanceSet> updatedUserExerciseInstanceSets = userExerciseInstance.getUserExerciseInstanceSets();
                ArrayList<UserExerciseInstanceSet> removedUserExerciseInstanceSets = new ArrayList<>();
                for (UserExerciseInstanceSet userExerciseInstanceSet : dbUserExerciseInstance.getUserExerciseInstanceSets()) {
                    if (!updatedUserExerciseInstanceSets.contains(userExerciseInstanceSet)) {
                        removedUserExerciseInstanceSets.add(userExerciseInstanceSet);
                    }
                }

                for (UserExerciseInstanceSet userExerciseInstanceSet : removedUserExerciseInstanceSets) {
                    userExerciseInstanceSetService.delete(userExerciseInstanceSet.getId());
                }
            }
        }
    }
}
