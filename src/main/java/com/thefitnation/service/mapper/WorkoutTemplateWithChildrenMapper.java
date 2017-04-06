package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.*;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkoutTemplate and its DTO WorkoutTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkoutTemplateWithChildrenMapper {

    @Mapping(source = "userDemographic.id", target = "userDemographicId")
    @Mapping(source = "skillLevel.id", target = "skillLevelId")
    @Mapping(source = "skillLevel.level", target = "skillLevelLevel")
    @Mapping(source = "workoutInstances", target = "workoutInstances")
    WorkoutTemplateWithChildrenDTO workoutTemplateToWorkoutTemplateWithChildrenDTO(WorkoutTemplate workoutTemplate);

    @Mapping(source = "workoutTemplate.id", target = "workoutTemplateId")
    @Mapping(source = "workoutTemplate.name", target = "workoutTemplateName")
    @Mapping(source = "exerciseInstances", target = "exerciseInstances")
    WorkoutInstanceDTO workoutInstanceToWorkoutInstanceDTO(WorkoutInstance workoutInstance);

    @Mapping(source = "workoutInstance.id", target = "workoutInstanceId")
    @Mapping(source = "exercise.id", target = "exerciseId")
    @Mapping(source = "exercise.name", target = "exerciseName")
    @Mapping(source = "repUnit.id", target = "repUnitId")
    @Mapping(source = "repUnit.name", target = "repUnitName")
    @Mapping(source = "effortUnit.id", target = "effortUnitId")
    @Mapping(source = "effortUnit.name", target = "effortUnitName")
    @Mapping(source = "exerciseInstanceSets", target = "exerciseInstanceSets")
    ExerciseInstanceDTO exerciseInstanceToExerciseInstanceDTO(ExerciseInstance exerciseInstance);

    @Mapping(source = "exerciseInstance.id", target = "exerciseInstanceId")
    ExerciseInstanceSetDTO exerciseInstanceSetToExerciseInstanceSetDTO(ExerciseInstanceSet exerciseInstanceSet);

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
