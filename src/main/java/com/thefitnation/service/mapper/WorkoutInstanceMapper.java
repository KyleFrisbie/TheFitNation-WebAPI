package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.ExerciseInstanceDTO;
import com.thefitnation.service.dto.ExerciseInstanceSetDTO;
import com.thefitnation.service.dto.WorkoutInstanceDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;

/**
 * Mapper for the entity WorkoutInstance and its DTO WorkoutInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkoutInstanceMapper {

    @Mapping(source = "workoutTemplate.id", target = "workoutTemplateId")
    @Mapping(source = "workoutTemplate.name", target = "workoutTemplateName")
    @Mapping(source = "exerciseInstances", target = "exerciseInstanceDTOs")
    WorkoutInstanceDTO workoutInstanceToWorkoutInstanceDTO(WorkoutInstance workoutInstance);

    List<WorkoutInstanceDTO> workoutInstancesToWorkoutInstanceDTOs(List<WorkoutInstance> workoutInstances);

    @Mapping(source = "workoutTemplateId", target = "workoutTemplate")
    @Mapping(target = "userWorkoutInstances", ignore = true)
    @Mapping(source = "exerciseInstanceDTOs", target = "exerciseInstances")
    WorkoutInstance workoutInstanceDTOToWorkoutInstance(WorkoutInstanceDTO workoutInstanceDTO);

    List<WorkoutInstance> workoutInstanceDTOsToWorkoutInstances(List<WorkoutInstanceDTO> workoutInstanceDTOs);

    @Mapping(source = "workoutInstance.id", target = "workoutInstanceId")
    @Mapping(source = "exercise.id", target = "exerciseId")
    @Mapping(source = "exercise.name", target = "exerciseName")
    @Mapping(source = "repUnit.id", target = "repUnitId")
    @Mapping(source = "repUnit.name", target = "repUnitName")
    @Mapping(source = "effortUnit.id", target = "effortUnitId")
    @Mapping(source = "effortUnit.name", target = "effortUnitName")
    @Mapping(source = "exerciseInstanceSets", target = "exerciseInstanceSetDTOs")
    ExerciseInstanceDTO exerciseInstanceToExerciseInstanceDTO(ExerciseInstance exerciseInstance);

    default ExerciseInstance ExerciseInstanceDTOToExerciseInstance(ExerciseInstanceDTO exerciseInstanceDTO) {
        if (exerciseInstanceDTO == null) {
            return null;
        }

        ExerciseInstanceMapper exerciseInstanceMapper = Mappers.getMapper(ExerciseInstanceMapper.class);

        ExerciseInstance exerciseInstance = exerciseInstanceMapper.exerciseInstanceDTOToExerciseInstance(exerciseInstanceDTO);

        return exerciseInstance;
    }

    @Mapping(source = "exerciseInstance.id", target = "exerciseInstanceId")
    ExerciseInstanceSetDTO exerciseInstanceSetToExerciseInstanceSetDTO(ExerciseInstanceSet exerciseInstanceSet);

    default ExerciseInstanceSet ExerciseInstanceSetDTOToExerciseInstanceSet(ExerciseInstanceSetDTO exerciseInstanceSetDTO) {

        ExerciseInstanceMapper exerciseInstanceMapper = Mappers.getMapper(ExerciseInstanceMapper.class);
        return exerciseInstanceMapper.ExerciseInstanceSetDTOToExerciseInstanceSet(exerciseInstanceSetDTO);
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
