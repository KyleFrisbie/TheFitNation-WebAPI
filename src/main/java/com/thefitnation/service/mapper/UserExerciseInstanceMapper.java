package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserExerciseInstanceDTO;

import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper for the entity UserExerciseInstance and its DTO UserExerciseInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserExerciseInstanceMapper {

    @Mapping(source = "userWorkoutInstance.id", target = "userWorkoutInstanceId")
    @Mapping(source = "exerciseInstance.id", target = "exerciseInstanceId")
    @Mapping(source = "userExerciseInstanceSets", target = "userExerciseInstanceSets")
    UserExerciseInstanceDTO userExerciseInstanceToUserExerciseInstanceDTO(UserExerciseInstance userExerciseInstance);

    List<UserExerciseInstanceDTO> userExerciseInstancesToUserExerciseInstanceDTOs(List<UserExerciseInstance> userExerciseInstances);

    @Mapping(source = "userWorkoutInstanceId", target = "userWorkoutInstance")
    @Mapping(source = "exerciseInstanceId", target = "exerciseInstance")
    @Mapping(source = "userExerciseInstanceSets", target = "userExerciseInstanceSets")
    UserExerciseInstance userExerciseInstanceDTOToUserExerciseInstance(UserExerciseInstanceDTO userExerciseInstanceDTO);

    List<UserExerciseInstance> userExerciseInstanceDTOsToUserExerciseInstances(List<UserExerciseInstanceDTO> userExerciseInstanceDTOs);

    @Mapping(source = "userExerciseInstance.id", target = "userExerciseInstanceId")
    @Mapping(source = "exerciseInstanceSet.id", target = "exerciseInstanceSetId")
    UserExerciseInstanceSetDTO userExerciseInstanceSetToUserExerciseInstanceSetDTO(UserExerciseInstanceSet userExerciseInstanceSet);

    default UserExerciseInstanceSet userExerciseInstanceSetDTOToUserExerciseInstanceSet(UserExerciseInstanceSetDTO userExerciseInstanceSetDTO) {
        UserExerciseInstanceSetMapper userExerciseInstanceSetMapper = Mappers.getMapper(UserExerciseInstanceSetMapper.class);
        return userExerciseInstanceSetMapper.userExerciseInstanceSetDTOToUserExerciseInstanceSet(userExerciseInstanceSetDTO);
    }

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
