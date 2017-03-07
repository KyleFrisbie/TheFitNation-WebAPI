package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserWorkoutTemplateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserWorkoutTemplate and its DTO UserWorkoutTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserWorkoutTemplateMapper {

    @Mapping(source = "userDemographic.id", target = "userDemographicId")
    @Mapping(source = "workoutTemplate.id", target = "workoutTemplateId")
    @Mapping(source = "workoutTemplate.name", target = "workoutTemplateName")
    UserWorkoutTemplateDTO userWorkoutTemplateToUserWorkoutTemplateDTO(UserWorkoutTemplate userWorkoutTemplate);

    List<UserWorkoutTemplateDTO> userWorkoutTemplatesToUserWorkoutTemplateDTOs(List<UserWorkoutTemplate> userWorkoutTemplates);

    @Mapping(source = "userDemographicId", target = "userDemographic")
    @Mapping(source = "workoutTemplateId", target = "workoutTemplate")
    @Mapping(target = "userWorkoutInstances", ignore = true)
    UserWorkoutTemplate userWorkoutTemplateDTOToUserWorkoutTemplate(UserWorkoutTemplateDTO userWorkoutTemplateDTO);

    List<UserWorkoutTemplate> userWorkoutTemplateDTOsToUserWorkoutTemplates(List<UserWorkoutTemplateDTO> userWorkoutTemplateDTOs);

    default UserDemographic userDemographicFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDemographic userDemographic = new UserDemographic();
        userDemographic.setId(id);
        return userDemographic;
    }

    default WorkoutTemplate workoutTemplateFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkoutTemplate workoutTemplate = new WorkoutTemplate();
        workoutTemplate.setId(id);
        return workoutTemplate;
    }
}
