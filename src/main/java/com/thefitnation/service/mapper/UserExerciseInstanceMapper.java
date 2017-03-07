package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserExerciseInstanceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserExerciseInstance and its DTO UserExerciseInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserExerciseInstanceMapper {

    @Mapping(source = "userWorkoutInstance.id", target = "userWorkoutInstanceId")
    @Mapping(source = "exerciseInstance.id", target = "exerciseInstanceId")
    UserExerciseInstanceDTO userExerciseInstanceToUserExerciseInstanceDTO(UserExerciseInstance userExerciseInstance);

    List<UserExerciseInstanceDTO> userExerciseInstancesToUserExerciseInstanceDTOs(List<UserExerciseInstance> userExerciseInstances);

    @Mapping(source = "userWorkoutInstanceId", target = "userWorkoutInstance")
    @Mapping(source = "exerciseInstanceId", target = "exerciseInstance")
    @Mapping(target = "userExerciseInstanceSets", ignore = true)
    UserExerciseInstance userExerciseInstanceDTOToUserExerciseInstance(UserExerciseInstanceDTO userExerciseInstanceDTO);

    List<UserExerciseInstance> userExerciseInstanceDTOsToUserExerciseInstances(List<UserExerciseInstanceDTO> userExerciseInstanceDTOs);

    default UserWorkoutInstance userWorkoutInstanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserWorkoutInstance userWorkoutInstance = new UserWorkoutInstance();
        userWorkoutInstance.setId(id);
        return userWorkoutInstance;
    }

    default ExerciseInstance exerciseInstanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        ExerciseInstance exerciseInstance = new ExerciseInstance();
        exerciseInstance.setId(id);
        return exerciseInstance;
    }
}
