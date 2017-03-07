package com.thefitnation.service;

import com.thefitnation.domain.SkillLevel;
import com.thefitnation.repository.SkillLevelRepository;
import com.thefitnation.service.dto.SkillLevelDTO;
import com.thefitnation.service.mapper.SkillLevelMapper;
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
 * Service Implementation for managing SkillLevel.
 */
@Service
@Transactional
public class SkillLevelService {

    private final Logger log = LoggerFactory.getLogger(SkillLevelService.class);
    
    private final SkillLevelRepository skillLevelRepository;

    private final SkillLevelMapper skillLevelMapper;

    public SkillLevelService(SkillLevelRepository skillLevelRepository, SkillLevelMapper skillLevelMapper) {
        this.skillLevelRepository = skillLevelRepository;
        this.skillLevelMapper = skillLevelMapper;
    }

    /**
     * Save a skillLevel.
     *
     * @param skillLevelDTO the entity to save
     * @return the persisted entity
     */
    public SkillLevelDTO save(SkillLevelDTO skillLevelDTO) {
        log.debug("Request to save SkillLevel : {}", skillLevelDTO);
        SkillLevel skillLevel = skillLevelMapper.skillLevelDTOToSkillLevel(skillLevelDTO);
        skillLevel = skillLevelRepository.save(skillLevel);
        SkillLevelDTO result = skillLevelMapper.skillLevelToSkillLevelDTO(skillLevel);
        return result;
    }

    /**
     *  Get all the skillLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SkillLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkillLevels");
        Page<SkillLevel> result = skillLevelRepository.findAll(pageable);
        return result.map(skillLevel -> skillLevelMapper.skillLevelToSkillLevelDTO(skillLevel));
    }

    /**
     *  Get one skillLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SkillLevelDTO findOne(Long id) {
        log.debug("Request to get SkillLevel : {}", id);
        SkillLevel skillLevel = skillLevelRepository.findOne(id);
        SkillLevelDTO skillLevelDTO = skillLevelMapper.skillLevelToSkillLevelDTO(skillLevel);
        return skillLevelDTO;
    }

    /**
     *  Delete the  skillLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SkillLevel : {}", id);
        skillLevelRepository.delete(id);
    }
}
