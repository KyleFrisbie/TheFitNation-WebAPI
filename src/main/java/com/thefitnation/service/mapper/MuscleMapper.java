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

    @Mapping(source = "bodyPart.id", target = "bodyPartId")
    @Mapping(source = "bodyPart.name", target = "bodyPartName")
    MuscleDTO muscleToMuscleDTO(Muscle muscle);

    List<MuscleDTO> musclesToMuscleDTOs(List<Muscle> muscles);

    @Mapping(target = "exercises", ignore = true)
    @Mapping(source = "bodyPartId", target = "bodyPart")
    Muscle muscleDTOToMuscle(MuscleDTO muscleDTO);

    List<Muscle> muscleDTOsToMuscles(List<MuscleDTO> muscleDTOs);

    default BodyPart bodyPartFromId(Long id) {
        if (id == null) {
            return null;
        }
        BodyPart bodyPart = new BodyPart();
        bodyPart.setId(id);
        return bodyPart;
    }
}
