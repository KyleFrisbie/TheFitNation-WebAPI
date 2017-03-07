package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserWorkoutInstanceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserWorkoutInstance and its DTO UserWorkoutInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserWorkoutInstanceMapper {

    @Mapping(source = "userWorkoutTemplate.id", target = "userWorkoutTemplateId")
    @Mapping(source = "workoutInstance.id", target = "workoutInstanceId")
    @Mapping(source = "workoutInstance.name", target = "workoutInstanceName")
    UserWorkoutInstanceDTO userWorkoutInstanceToUserWorkoutInstanceDTO(UserWorkoutInstance userWorkoutInstance);

    List<UserWorkoutInstanceDTO> userWorkoutInstancesToUserWorkoutInstanceDTOs(List<UserWorkoutInstance> userWorkoutInstances);

    @Mapping(source = "userWorkoutTemplateId", target = "userWorkoutTemplate")
    @Mapping(source = "workoutInstanceId", target = "workoutInstance")
    @Mapping(target = "userExerciseInstances", ignore = true)
    UserWorkoutInstance userWorkoutInstanceDTOToUserWorkoutInstance(UserWorkoutInstanceDTO userWorkoutInstanceDTO);

    List<UserWorkoutInstance> userWorkoutInstanceDTOsToUserWorkoutInstances(List<UserWorkoutInstanceDTO> userWorkoutInstanceDTOs);

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
