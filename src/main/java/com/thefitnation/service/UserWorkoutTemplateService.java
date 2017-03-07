package com.thefitnation.service;

import com.thefitnation.domain.UserWorkoutTemplate;
import com.thefitnation.repository.UserWorkoutTemplateRepository;
import com.thefitnation.service.dto.UserWorkoutTemplateDTO;
import com.thefitnation.service.mapper.UserWorkoutTemplateMapper;
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
 * Service Implementation for managing UserWorkoutTemplate.
 */
@Service
@Transactional
public class UserWorkoutTemplateService {

    private final Logger log = LoggerFactory.getLogger(UserWorkoutTemplateService.class);
    
    private final UserWorkoutTemplateRepository userWorkoutTemplateRepository;

    private final UserWorkoutTemplateMapper userWorkoutTemplateMapper;

    public UserWorkoutTemplateService(UserWorkoutTemplateRepository userWorkoutTemplateRepository, UserWorkoutTemplateMapper userWorkoutTemplateMapper) {
        this.userWorkoutTemplateRepository = userWorkoutTemplateRepository;
        this.userWorkoutTemplateMapper = userWorkoutTemplateMapper;
    }

    /**
     * Save a userWorkoutTemplate.
     *
     * @param userWorkoutTemplateDTO the entity to save
     * @return the persisted entity
     */
    public UserWorkoutTemplateDTO save(UserWorkoutTemplateDTO userWorkoutTemplateDTO) {
        log.debug("Request to save UserWorkoutTemplate : {}", userWorkoutTemplateDTO);
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateMapper.userWorkoutTemplateDTOToUserWorkoutTemplate(userWorkoutTemplateDTO);
        userWorkoutTemplate = userWorkoutTemplateRepository.save(userWorkoutTemplate);
        UserWorkoutTemplateDTO result = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        return result;
    }

    /**
     *  Get all the userWorkoutTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserWorkoutTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWorkoutTemplates");
        Page<UserWorkoutTemplate> result = userWorkoutTemplateRepository.findAll(pageable);
        return result.map(userWorkoutTemplate -> userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate));
    }

    /**
     *  Get one userWorkoutTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserWorkoutTemplateDTO findOne(Long id) {
        log.debug("Request to get UserWorkoutTemplate : {}", id);
        UserWorkoutTemplate userWorkoutTemplate = userWorkoutTemplateRepository.findOne(id);
        UserWorkoutTemplateDTO userWorkoutTemplateDTO = userWorkoutTemplateMapper.userWorkoutTemplateToUserWorkoutTemplateDTO(userWorkoutTemplate);
        return userWorkoutTemplateDTO;
    }

    /**
     *  Delete the  userWorkoutTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserWorkoutTemplate : {}", id);
        userWorkoutTemplateRepository.delete(id);
    }
}
