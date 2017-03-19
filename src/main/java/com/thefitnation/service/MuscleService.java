package com.thefitnation.service;

import com.thefitnation.domain.Muscle;
import com.thefitnation.repository.MuscleRepository;
import com.thefitnation.service.dto.MuscleDTO;
import com.thefitnation.service.mapper.MuscleMapper;
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
 * Service Implementation for managing Muscle.
 */
@Service
@Transactional
public class MuscleService {

    private final Logger log = LoggerFactory.getLogger(MuscleService.class);

    private final MuscleRepository muscleRepository;

    private final MuscleMapper muscleMapper;

    public MuscleService(MuscleRepository muscleRepository, MuscleMapper muscleMapper) {
        this.muscleRepository = muscleRepository;
        this.muscleMapper = muscleMapper;
    }

    /**
     * Save a muscle.
     *
     * @param muscleDTO the entity to save
     * @return the persisted entity
     */
    public MuscleDTO save(MuscleDTO muscleDTO) {
        log.debug("Request to save Muscle : {}", muscleDTO);
        Muscle muscle = muscleMapper.muscleDTOToMuscle(muscleDTO);
        muscle = muscleRepository.save(muscle);
        MuscleDTO result = muscleMapper.muscleToMuscleDTO(muscle);
        return result;
    }

    /**
     *  Get all the muscles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MuscleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Muscles");
        Page<Muscle> result = muscleRepository.findAll(pageable);
        return result.map(muscle -> muscleMapper.muscleToMuscleDTO(muscle));
    }

    /**
     *  Get one muscle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MuscleDTO findOne(Long id) {
        log.debug("Request to get Muscle : {}", id);
        Muscle muscle = muscleRepository.findOne(id);
        MuscleDTO muscleDTO = muscleMapper.muscleToMuscleDTO(muscle);
        return muscleDTO;
    }

    /**
     *  Get one muscle by id.
     *
     *  @param muscleName the name of the  muscle entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MuscleDTO findOne(String muscleName) {
        log.debug("Request to get Muscle : {}", muscleName);
        Muscle muscle = muscleRepository.findByNameIgnoreCase(muscleName);
        return muscleMapper.muscleToMuscleDTO(muscle);
    }

    /**
     *  Delete the  muscle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Muscle : {}", id);
        muscleRepository.delete(id);
    }
}
