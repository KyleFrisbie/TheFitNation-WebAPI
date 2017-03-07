package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.BodyPartDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BodyPart and its DTO BodyPartDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BodyPartMapper {

    BodyPartDTO bodyPartToBodyPartDTO(BodyPart bodyPart);

    List<BodyPartDTO> bodyPartsToBodyPartDTOs(List<BodyPart> bodyParts);

    @Mapping(target = "muscles", ignore = true)
    BodyPart bodyPartDTOToBodyPart(BodyPartDTO bodyPartDTO);

    List<BodyPart> bodyPartDTOsToBodyParts(List<BodyPartDTO> bodyPartDTOs);
}
