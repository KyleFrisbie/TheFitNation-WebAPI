package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.ExerciseFamilyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ExerciseFamily and its DTO ExerciseFamilyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExerciseFamilyMapper {

    ExerciseFamilyDTO exerciseFamilyToExerciseFamilyDTO(ExerciseFamily exerciseFamily);

    List<ExerciseFamilyDTO> exerciseFamiliesToExerciseFamilyDTOs(List<ExerciseFamily> exerciseFamilies);

    @Mapping(target = "exercises", ignore = true)
    ExerciseFamily exerciseFamilyDTOToExerciseFamily(ExerciseFamilyDTO exerciseFamilyDTO);

    List<ExerciseFamily> exerciseFamilyDTOsToExerciseFamilies(List<ExerciseFamilyDTO> exerciseFamilyDTOs);
}
