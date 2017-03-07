package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ExerciseInstanceSet and its DTO ExerciseInstanceSetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExerciseInstanceSetMapper {

    @Mapping(source = "exerciseInstance.id", target = "exerciseInstanceId")
    ExerciseInstanceSetDTO exerciseInstanceSetToExerciseInstanceSetDTO(ExerciseInstanceSet exerciseInstanceSet);

    List<ExerciseInstanceSetDTO> exerciseInstanceSetsToExerciseInstanceSetDTOs(List<ExerciseInstanceSet> exerciseInstanceSets);

    @Mapping(source = "exerciseInstanceId", target = "exerciseInstance")
    @Mapping(target = "userExerciseInstanceSets", ignore = true)
    ExerciseInstanceSet exerciseInstanceSetDTOToExerciseInstanceSet(ExerciseInstanceSetDTO exerciseInstanceSetDTO);

    List<ExerciseInstanceSet> exerciseInstanceSetDTOsToExerciseInstanceSets(List<ExerciseInstanceSetDTO> exerciseInstanceSetDTOs);

    default ExerciseInstance exerciseInstanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        ExerciseInstance exerciseInstance = new ExerciseInstance();
        exerciseInstance.setId(id);
        return exerciseInstance;
    }
}
