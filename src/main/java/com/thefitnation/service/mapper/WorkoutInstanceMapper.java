package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.WorkoutInstanceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkoutInstance and its DTO WorkoutInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkoutInstanceMapper {

    @Mapping(source = "workoutTemplate.id", target = "workoutTemplateId")
    @Mapping(source = "workoutTemplate.name", target = "workoutTemplateName")
    WorkoutInstanceDTO workoutInstanceToWorkoutInstanceDTO(WorkoutInstance workoutInstance);

    List<WorkoutInstanceDTO> workoutInstancesToWorkoutInstanceDTOs(List<WorkoutInstance> workoutInstances);

    @Mapping(source = "workoutTemplateId", target = "workoutTemplate")
    @Mapping(target = "userWorkoutInstances", ignore = true)
    @Mapping(target = "exerciseInstances", ignore = true)
    WorkoutInstance workoutInstanceDTOToWorkoutInstance(WorkoutInstanceDTO workoutInstanceDTO);

    List<WorkoutInstance> workoutInstanceDTOsToWorkoutInstances(List<WorkoutInstanceDTO> workoutInstanceDTOs);

    default WorkoutTemplate workoutTemplateFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkoutTemplate workoutTemplate = new WorkoutTemplate();
        workoutTemplate.setId(id);
        return workoutTemplate;
    }
}
