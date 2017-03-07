package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.ExerciseInstanceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ExerciseInstance and its DTO ExerciseInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExerciseInstanceMapper {

    @Mapping(source = "workoutInstance.id", target = "workoutInstanceId")
    @Mapping(source = "exercise.id", target = "exerciseId")
    @Mapping(source = "exercise.name", target = "exerciseName")
    @Mapping(source = "repUnit.id", target = "repUnitId")
    @Mapping(source = "repUnit.name", target = "repUnitName")
    @Mapping(source = "effortUnit.id", target = "effortUnitId")
    @Mapping(source = "effortUnit.name", target = "effortUnitName")
    ExerciseInstanceDTO exerciseInstanceToExerciseInstanceDTO(ExerciseInstance exerciseInstance);

    List<ExerciseInstanceDTO> exerciseInstancesToExerciseInstanceDTOs(List<ExerciseInstance> exerciseInstances);

    @Mapping(source = "workoutInstanceId", target = "workoutInstance")
    @Mapping(target = "userExerciseInstances", ignore = true)
    @Mapping(target = "exerciseInstanceSets", ignore = true)
    @Mapping(source = "exerciseId", target = "exercise")
    @Mapping(source = "repUnitId", target = "repUnit")
    @Mapping(source = "effortUnitId", target = "effortUnit")
    ExerciseInstance exerciseInstanceDTOToExerciseInstance(ExerciseInstanceDTO exerciseInstanceDTO);

    List<ExerciseInstance> exerciseInstanceDTOsToExerciseInstances(List<ExerciseInstanceDTO> exerciseInstanceDTOs);

    default WorkoutInstance workoutInstanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkoutInstance workoutInstance = new WorkoutInstance();
        workoutInstance.setId(id);
        return workoutInstance;
    }

    default Exercise exerciseFromId(Long id) {
        if (id == null) {
            return null;
        }
        Exercise exercise = new Exercise();
        exercise.setId(id);
        return exercise;
    }

    default Unit unitFromId(Long id) {
        if (id == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(id);
        return unit;
    }
}
