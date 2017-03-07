package com.thefitnation.service;

import com.thefitnation.domain.UserExerciseInstanceSet;
import com.thefitnation.repository.UserExerciseInstanceSetRepository;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
import com.thefitnation.service.mapper.UserExerciseInstanceSetMapper;
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
 * Service Implementation for managing UserExerciseInstanceSet.
 */
@Service
@Transactional
public class UserExerciseInstanceSetService {

    private final Logger log = LoggerFactory.getLogger(UserExerciseInstanceSetService.class);
    
    private final UserExerciseInstanceSetRepository userExerciseInstanceSetRepository;

    private final UserExerciseInstanceSetMapper userExerciseInstanceSetMapper;

    public UserExerciseInstanceSetService(UserExerciseInstanceSetRepository userExerciseInstanceSetRepository, UserExerciseInstanceSetMapper userExerciseInstanceSetMapper) {
        this.userExerciseInstanceSetRepository = userExerciseInstanceSetRepository;
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
        UserExerciseInstanceSetDTO result = userExerciseInstanceSetMapper.userExerciseInstanceSetToUserExerciseInstanceSetDTO(userExerciseInstanceSet);
        return result;
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
        userExerciseInstanceSetRepository.delete(id);
    }
}
