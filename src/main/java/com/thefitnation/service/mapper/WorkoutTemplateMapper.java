package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.WorkoutTemplateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkoutTemplate and its DTO WorkoutTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkoutTemplateMapper {

    @Mapping(source = "userDemographic.id", target = "userDemographicId")
    @Mapping(source = "skillLevel.id", target = "skillLevelId")
    @Mapping(source = "skillLevel.level", target = "skillLevelLevel")
    WorkoutTemplateDTO workoutTemplateToWorkoutTemplateDTO(WorkoutTemplate workoutTemplate);

    List<WorkoutTemplateDTO> workoutTemplatesToWorkoutTemplateDTOs(List<WorkoutTemplate> workoutTemplates);

    @Mapping(source = "userDemographicId", target = "userDemographic")
    @Mapping(target = "workoutInstances", ignore = true)
    @Mapping(target = "userWorkoutTemplates", ignore = true)
    @Mapping(source = "skillLevelId", target = "skillLevel")
    WorkoutTemplate workoutTemplateDTOToWorkoutTemplate(WorkoutTemplateDTO workoutTemplateDTO);

    List<WorkoutTemplate> workoutTemplateDTOsToWorkoutTemplates(List<WorkoutTemplateDTO> workoutTemplateDTOs);

    default UserDemographic userDemographicFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDemographic userDemographic = new UserDemographic();
        userDemographic.setId(id);
        return userDemographic;
    }

    default SkillLevel skillLevelFromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillLevel skillLevel = new SkillLevel();
        skillLevel.setId(id);
        return skillLevel;
    }
}
