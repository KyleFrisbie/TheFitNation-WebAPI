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
 * Service Implementation for managing UserWorkoutInstance.
 */
@Service
@Transactional
public class UserWorkoutInstanceService {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutInstanceService.class);

    private final UserWorkoutInstanceRepository userWorkoutInstanceRepository;
    private final UserWorkoutTemplateRepository userWorkoutTemplateRepository;
    private final WorkoutInstanceRepository workoutInstanceRepository;
    private final UserWorkoutInstanceMapper userWorkoutInstanceMapper;
    private final UserExerciseInstanceMapper userExerciseInstanceMapper;
    private final UserExerciseInstanceService userExerciseInstanceService;
    private final UserRepository userRepository;

    public UserWorkoutInstanceService(UserWorkoutInstanceRepository userWorkoutInstanceRepository,
                                      UserWorkoutTemplateRepository userWorkoutTemplateRepository,
                                      WorkoutInstanceRepository workoutInstanceRepository,
                                      UserWorkoutInstanceMapper userWorkoutInstanceMapper,
                                      UserExerciseInstanceMapper userExerciseInstanceMapper,
                                      UserExerciseInstanceService userExerciseInstanceService,
                                      UserRepository userRepository) {
        this.userWorkoutInstanceRepository = userWorkoutInstanceRepository;
        this.userWorkoutTemplateRepository = userWorkoutTemplateRepository;
        this.workoutInstanceRepository = workoutInstanceRepository;
        this.userWorkoutInstanceMapper = userWorkoutInstanceMapper;
        this.userExerciseInstanceMapper = userExerciseInstanceMapper;
        this.userExerciseInstanceService = userExerciseInstanceService;
        this.userRepository = userRepository;
    }

    /**
     * Save a userWorkoutInstance.
     *
     * @param userWorkoutInstanceDTO the entity to save
     * @return the persisted entity
     */
    public UserWorkoutInstanceDTO save(UserWorkoutInstanceDTO userWorkoutInstanceDTO) {
        log.debug("Request to save UserWorkoutInstance : {}", userWorkoutInstanceDTO);
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceMapper.userWorkoutInstanceDTOToUserWorkoutInstance(userWorkoutInstanceDTO);


        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (!user.isPresent()) { return null; }
        if (!userWorkoutTemplateRepository
            .findOne(userWorkoutInstanceDTO.getUserWorkoutTemplateId())
            .getUserDemographic()
            .getUser()
            .getLogin().equals(SecurityUtils.getCurrentUserLogin())) {
            return null;
        }

        removeDereferencedUserExerciseInstances(userWorkoutInstance);

        userWorkoutInstance.setUserExerciseInstances(new HashSet<>());
        userWorkoutInstance = userWorkoutInstanceRepository.save(userWorkoutInstance);
        addUserWorkoutInstanceToParent(userWorkoutInstance);

        List<UserExerciseInstanceDTO> userExerciseInstanceDTOs = userWorkoutInstanceDTO.getUserExerciseInstances();

        if (userExerciseInstanceDTOs != null && userExerciseInstanceDTOs.size() > 0) {
            List<UserExerciseInstanceDTO> savedUserExerciseInstanceDTOs = new ArrayList<>();
            for (UserExerciseInstanceDTO userExerciseInstanceDTO : userExerciseInstanceDTOs) {
                userExerciseInstanceDTO.setUserWorkoutInstanceId(userWorkoutInstance.getId());
                savedUserExerciseInstanceDTOs.add(userExerciseInstanceService.save(userExerciseInstanceDTO));
            }
            userWorkoutInstance.setUserExerciseInstances(new HashSet<>(userExerciseInstanceMapper.userExerciseInstanceDTOsToUserExerciseInstances(savedUserExerciseInstanceDTOs)));
        }

        return userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);

    }

    private void addUserWorkoutInstanceToParent(UserWorkoutInstance userWorkoutInstance) {
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateRepository.findOne((userWorkoutInstance.getUserWorkoutTemplate()).getId());
        userWorkoutTemplate.addUserWorkoutInstance(userWorkoutInstance);
        userWorkoutTemplateRepository.save(userWorkoutTemplate);

        if (userWorkoutInstance.getWorkoutInstance() != null) {
            WorkoutInstance workoutInstance = workoutInstanceRepository.findOne((userWorkoutTemplate.getWorkoutTemplate()).getId());
            workoutInstance.addUserWorkoutInstance(userWorkoutInstance);
            workoutInstanceRepository.save(workoutInstance);
        }
    }

    private void removeDereferencedUserExerciseInstances(UserWorkoutInstance userWorkoutInstance) {
        if (userWorkoutInstance.getId() != null) {
            UserWorkoutInstance dbUserWorkoutInstance = userWorkoutInstanceRepository.findOne(userWorkoutInstance.getId());
            if (dbUserWorkoutInstance != null) {
                Set<UserExerciseInstance> updatedUserExerciseInstanceSets = userWorkoutInstance.getUserExerciseInstances();
                ArrayList<UserExerciseInstance> removedUserExerciseInstances = new ArrayList<>();
                for (UserExerciseInstance userExerciseInstance : dbUserWorkoutInstance.getUserExerciseInstances()) {
                    if (!updatedUserExerciseInstanceSets.contains(userExerciseInstance)) {
                        removedUserExerciseInstances.add(userExerciseInstance);
                    }
                }

                for (UserExerciseInstance userExerciseInstance : removedUserExerciseInstances) {
                    userExerciseInstanceService.delete(userExerciseInstance.getId());
                }
            }
        }
    }

    /**
     * Get all the userWorkoutInstances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWorkoutInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorkoutInstances");
        Page<UserWorkoutInstance> result = userWorkoutInstanceRepository.findAll(pageable);
        return result.map(userWorkoutInstanceMapper::userWorkoutInstanceToUserWorkoutInstanceDTO);
    }

    /**
     * Get one userWorkoutInstance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserWorkoutInstanceDTO findOne(Long id) {
        log.debug("Request to get UserWorkoutInstance : {}", id);
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceRepository.findOne(id);
        return userWorkoutInstanceMapper.userWorkoutInstanceToUserWorkoutInstanceDTO(userWorkoutInstance);
    }

    /**
     * Delete the  userWorkoutInstance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWorkoutInstance : {}", id);
        removeUserWorkoutInstanceFromRelatedItems(id);
        userWorkoutInstanceRepository.delete(id);
    }

    public void removeUserWorkoutInstanceFromRelatedItems(Long id) {
        UserWorkoutInstance userWorkoutInstance = userWorkoutInstanceRepository.findOne(id);
        if (userWorkoutInstance != null) {
            UserWorkoutTemplate userWorkoutTemplate = userWorkoutInstance.getUserWorkoutTemplate();
            userWorkoutTemplate.removeUserWorkoutInstance(userWorkoutInstance);
            userWorkoutTemplateRepository.save(userWorkoutTemplate);

            if (userWorkoutInstance.getWorkoutInstance() != null) {
                WorkoutInstance workoutInstance = userWorkoutInstance.getWorkoutInstance();
                workoutInstance.removeUserWorkoutInstance(userWorkoutInstance);
                workoutInstanceRepository.save(workoutInstance);
            }
        }
    }
}
