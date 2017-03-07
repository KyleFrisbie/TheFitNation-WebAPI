package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.MuscleDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Muscle and its DTO MuscleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MuscleMapper {

    MuscleDTO muscleToMuscleDTO(Muscle muscle);

    List<MuscleDTO> musclesToMuscleDTOs(List<Muscle> muscles);

    @Mapping(target = "exercises", ignore = true)
    Muscle muscleDTOToMuscle(MuscleDTO muscleDTO);

    List<Muscle> muscleDTOsToMuscles(List<MuscleDTO> muscleDTOs);
}
