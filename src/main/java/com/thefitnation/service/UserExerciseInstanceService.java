package com.thefitnation.service;

import com.thefitnation.domain.UserExerciseInstance;
import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.repository.UserExerciseInstanceRepository;
import com.thefitnation.repository.UserExerciseInstanceSetRepository;
import com.thefitnation.service.dto.UserExerciseInstanceDTO;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.UserExerciseInstanceMapper;
import com.thefitnation.service.mapper.UserExerciseInstanceSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * Service Implementation for managing UserExerciseInstance.
 */
@Service
@Transactional
public class UserExerciseInstanceService {

    private final Logger log = LoggerFactory.getLogger(UserExerciseInstanceService.class);

    private final UserExerciseInstanceRepository userExerciseInstanceRepository;

    private final UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    private final UserExerciseInstanceMapper userExerciseInstanceMapper;

    private final UserExerciseInstanceSetMapper userExerciseInstanceSetMapper;

    public UserExerciseInstanceService(UserExerciseInstanceRepository userExerciseInstanceRepository, UserExerciseInstanceSetRepository userExerciseInstanceSetRepository, UserExerciseInstanceMapper userExerciseInstanceMapper, UserExerciseInstanceSetMapper userExerciseInstanceSetMapper) {
        this.userExerciseInstanceRepository = userExerciseInstanceRepository;
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
        this.userExerciseInstanceMapper = userExerciseInstanceMapper;
        this.userExerciseInstanceSetMapper = userExerciseInstanceSetMapper;
    }

    /**
     * Save a userExerciseInstance.
     *
     * @param userExerciseInstanceDTO the entity to save
     * @return the persisted entity
     */
    public UserExerciseInstanceDTO save(UserExerciseInstanceDTO userExerciseInstanceDTO) {
        log.debug("Request to save UserExerciseInstance : {}", userExerciseInstanceDTO);
        UserExerciseInstance userExerciseInstance = userExerciseInstanceMapper.userExerciseInstanceDTOToUserExerciseInstance(userExerciseInstanceDTO);
        userExerciseInstance = userExerciseInstanceRepository.save(userExerciseInstance);

        // Associate all children with the new parent and save them to the DB
        List<UserExerciseInstanceSetDTO> userExerciseInstanceSetDTOs = userExerciseInstanceDTO.getUserExerciseInstanceSets();
        List<UserExerciseInstanceSet> userExerciseInstanceSets = userExerciseInstanceSetMapper.userExerciseInstanceSetDTOsToUserExerciseInstanceSets(userExerciseInstanceSetDTOs);

        for (UserExerciseInstanceSet userExerciseInstanceSet : userExerciseInstanceSets) {
            userExerciseInstanceSet.setUserExerciseInstance(userExerciseInstance);
        }

        userExerciseInstanceSets = userExerciseInstanceSetRepository.save(userExerciseInstanceSets);

        userExerciseInstance.setUserExerciseInstanceSets(new HashSet<>(userExerciseInstanceSets));
        UserExerciseInstanceDTO result = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
        return result;
    }

    /**
     *  Get all the userExerciseInstances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserExerciseInstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExerciseInstances");
        Page<UserExerciseInstance> result = userExerciseInstanceRepository.findAll(pageable);
        return result.map(userExerciseInstance -> userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance));
    }

    /**
     *  Get one userExerciseInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserExerciseInstanceDTO findOne(Long id) {
        log.debug("Request to get UserExerciseInstance : {}", id);
        UserExerciseInstance userExerciseInstance = userExerciseInstanceRepository.findOne(id);
        UserExerciseInstanceDTO userExerciseInstanceDTO = userExerciseInstanceMapper.userExerciseInstanceToUserExerciseInstanceDTO(userExerciseInstance);
        return userExerciseInstanceDTO;
    }

    /**
     *  Delete the  userExerciseInstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExerciseInstance : {}", id);
        userExerciseInstanceRepository.delete(id);
    }
}
