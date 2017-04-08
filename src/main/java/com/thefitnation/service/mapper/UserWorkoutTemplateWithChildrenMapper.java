package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity UserWorkoutTemplate and its DTO UserWorkoutTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserWorkoutTemplateWithChildrenMapper {

    @Mapping(source = "userDemographic.id", target = "userDemographicId")
    @Mapping(source = "workoutTemplate.id", target = "workoutTemplateId")
    @Mapping(source = "workoutTemplate.name", target = "workoutTemplateName")
    @Mapping(source = "userWorkoutInstances", target = "userWorkoutInstances")
    UserWorkoutTemplateWithChildrenDTO userWorkoutTemplateToUserWorkoutTemplateWithChildrenDTO(UserWorkoutTemplate userWorkoutTemplate);

    @Mapping(source = "userWorkoutTemplate.id", target = "userWorkoutTemplateId")
    @Mapping(source = "workoutInstance.id", target = "workoutInstanceId")
    @Mapping(source = "workoutInstance.name", target = "workoutInstanceName")
    @Mapping(source = "userExerciseInstances", target = "userExerciseInstances")
    UserWorkoutInstanceDTO userWorkoutInstanceToUserWorkoutInstanceDTO(UserWorkoutInstance userWorkoutInstance);

    @Mapping(source = "userWorkoutInstance.id", target = "userWorkoutInstanceId")
    @Mapping(source = "exerciseInstance.id", target = "exerciseInstanceId")
    @Mapping(source = "userExerciseInstanceSets", target = "userExerciseInstanceSets")
    UserExerciseInstanceDTO userExerciseInstanceToUserExerciseInstanceDTO(UserExerciseInstance userExerciseInstance);

    @Mapping(source = "userExerciseInstance.id", target = "userExerciseInstanceId")
    @Mapping(source = "exerciseInstanceSet.id", target = "exerciseInstanceSetId")
    UserExerciseInstanceSetDTO userExerciseInstanceSetToUserExerciseInstanceSetDTO(UserExerciseInstanceSet userExerciseInstanceSet);

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
