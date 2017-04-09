package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserExerciseInstanceDTO;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;
import com.thefitnation.service.dto.UserWorkoutInstanceDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper for the entity UserWorkoutInstance and its DTO UserWorkoutInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserWorkoutInstanceMapper {

    @Mapping(source = "userWorkoutTemplate.id", target = "userWorkoutTemplateId")
    @Mapping(source = "workoutInstance.id", target = "workoutInstanceId")
    @Mapping(source = "workoutInstance.name", target = "workoutInstanceName")
    @Mapping(source = "userExerciseInstances", target = "userExerciseInstances")
    UserWorkoutInstanceDTO userWorkoutInstanceToUserWorkoutInstanceDTO(UserWorkoutInstance userWorkoutInstance);

    List<UserWorkoutInstanceDTO> userWorkoutInstancesToUserWorkoutInstanceDTOs(List<UserWorkoutInstance> userWorkoutInstances);

    @Mapping(source = "userWorkoutTemplateId", target = "userWorkoutTemplate")
    @Mapping(source = "workoutInstanceId", target = "workoutInstance")
    @Mapping(source = "userExerciseInstances", target = "userExerciseInstances")
    UserWorkoutInstance userWorkoutInstanceDTOToUserWorkoutInstance(UserWorkoutInstanceDTO userWorkoutInstanceDTO);

    List<UserWorkoutInstance> userWorkoutInstanceDTOsToUserWorkoutInstances(List<UserWorkoutInstanceDTO> userWorkoutInstanceDTOs);

    @Mapping(source = "userWorkoutInstance.id", target = "userWorkoutInstanceId")
    @Mapping(source = "exerciseInstance.id", target = "exerciseInstanceId")
    @Mapping(source = "userExerciseInstanceSets", target = "userExerciseInstanceSets")
    UserExerciseInstanceDTO userExerciseInstanceToUserExerciseInstanceDTO(UserExerciseInstance userExerciseInstance);

    default UserExerciseInstance userExerciseInstanceDTOToUserExerciseInstance(UserExerciseInstanceDTO userExerciseInstanceDTO) {
        UserExerciseInstanceMapper userExerciseInstanceMapper = Mappers.getMapper(UserExerciseInstanceMapper.class);
        return userExerciseInstanceMapper.userExerciseInstanceDTOToUserExerciseInstance(userExerciseInstanceDTO);
    }

    @Mapping(source = "userExerciseInstance.id", target = "userExerciseInstanceId")
    @Mapping(source = "exerciseInstanceSet.id", target = "exerciseInstanceSetId")
    UserExerciseInstanceSetDTO userExerciseInstanceSetToUserExerciseInstanceSetDTO(UserExerciseInstanceSet userExerciseInstanceSet);

    default UserExerciseInstanceSet userExerciseInstanceSetDTOToUserExerciseInstanceSet(UserExerciseInstanceSetDTO userExerciseInstanceSetDTO) {
        UserExerciseInstanceSetMapper userExerciseInstanceSetMapper = Mappers.getMapper(UserExerciseInstanceSetMapper.class);
        return userExerciseInstanceSetMapper.userExerciseInstanceSetDTOToUserExerciseInstanceSet(userExerciseInstanceSetDTO);
    }

    default UserWorkoutTemplate userWorkoutTemplateFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserWorkoutTemplate userWorkoutTemplate = new UserWorkoutTemplate();
        userWorkoutTemplate.setId(id);
        return userWorkoutTemplate;
    }

    default WorkoutInstance workoutInstanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkoutInstance workoutInstance = new WorkoutInstance();
        workoutInstance.setId(id);
        return workoutInstance;
    }
}
