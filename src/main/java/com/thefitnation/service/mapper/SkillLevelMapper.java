package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.SkillLevelDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SkillLevel and its DTO SkillLevelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillLevelMapper {

    SkillLevelDTO skillLevelToSkillLevelDTO(SkillLevel skillLevel);

    List<SkillLevelDTO> skillLevelsToSkillLevelDTOs(List<SkillLevel> skillLevels);

    SkillLevel skillLevelDTOToSkillLevel(SkillLevelDTO skillLevelDTO);

    List<SkillLevel> skillLevelDTOsToSkillLevels(List<SkillLevelDTO> skillLevelDTOs);
}
