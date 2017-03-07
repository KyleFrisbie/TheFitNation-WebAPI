package com.thefitnation.service;

import com.thefitnation.domain.BodyPart;
import com.thefitnation.repository.BodyPartRepository;
import com.thefitnation.service.dto.BodyPartDTO;
import com.thefitnation.service.mapper.BodyPartMapper;
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
 * Service Implementation for managing BodyPart.
 */
@Service
@Transactional
public class BodyPartService {

    private final Logger log = LoggerFactory.getLogger(BodyPartService.class);
    
    private final BodyPartRepository bodyPartRepository;

    private final BodyPartMapper bodyPartMapper;

    public BodyPartService(BodyPartRepository bodyPartRepository, BodyPartMapper bodyPartMapper) {
        this.bodyPartRepository = bodyPartRepository;
        this.bodyPartMapper = bodyPartMapper;
    }

    /**
     * Save a bodyPart.
     *
     * @param bodyPartDTO the entity to save
     * @return the persisted entity
     */
    public BodyPartDTO save(BodyPartDTO bodyPartDTO) {
        log.debug("Request to save BodyPart : {}", bodyPartDTO);
        BodyPart bodyPart = bodyPartMapper.bodyPartDTOToBodyPart(bodyPartDTO);
        bodyPart = bodyPartRepository.save(bodyPart);
        BodyPartDTO result = bodyPartMapper.bodyPartToBodyPartDTO(bodyPart);
        return result;
    }

    /**
     *  Get all the bodyParts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BodyPartDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BodyParts");
        Page<BodyPart> result = bodyPartRepository.findAll(pageable);
        return result.map(bodyPart -> bodyPartMapper.bodyPartToBodyPartDTO(bodyPart));
    }

    /**
     *  Get one bodyPart by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BodyPartDTO findOne(Long id) {
        log.debug("Request to get BodyPart : {}", id);
        BodyPart bodyPart = bodyPartRepository.findOne(id);
        BodyPartDTO bodyPartDTO = bodyPartMapper.bodyPartToBodyPartDTO(bodyPart);
        return bodyPartDTO;
    }

    /**
     *  Delete the  bodyPart by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BodyPart : {}", id);
        bodyPartRepository.delete(id);
    }
}
