package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserExerciseInstanceSetDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserExerciseInstanceSet and its DTO UserExerciseInstanceSetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserExerciseInstanceSetMapper {

    @Mapping(source = "userExerciseInstance.id", target = "userExerciseInstanceId")
    @Mapping(source = "exerciseInstanceSet.id", target = "exerciseInstanceSetId")
    UserExerciseInstanceSetDTO userExerciseInstanceSetToUserExerciseInstanceSetDTO(UserExerciseInstanceSet userExerciseInstanceSet);

    List<UserExerciseInstanceSetDTO> userExerciseInstanceSetsToUserExerciseInstanceSetDTOs(List<UserExerciseInstanceSet> userExerciseInstanceSets);

    @Mapping(source = "userExerciseInstanceId", target = "userExerciseInstance")
    @Mapping(source = "exerciseInstanceSetId", target = "exerciseInstanceSet")
    UserExerciseInstanceSet userExerciseInstanceSetDTOToUserExerciseInstanceSet(UserExerciseInstanceSetDTO userExerciseInstanceSetDTO);

    List<UserExerciseInstanceSet> userExerciseInstanceSetDTOsToUserExerciseInstanceSets(List<UserExerciseInstanceSetDTO> userExerciseInstanceSetDTOs);

    default UserExerciseInstance userExerciseInstanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExerciseInstance userExerciseInstance = new UserExerciseInstance();
        userExerciseInstance.setId(id);
        return userExerciseInstance;
    }

    default ExerciseInstanceSet exerciseInstanceSetFromId(Long id) {
        if (id == null) {
            return null;
        }
        ExerciseInstanceSet exerciseInstanceSet = new ExerciseInstanceSet();
        exerciseInstanceSet.setId(id);
        return exerciseInstanceSet;
    }
}
