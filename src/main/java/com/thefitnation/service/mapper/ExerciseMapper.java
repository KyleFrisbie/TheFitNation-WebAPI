package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.ExerciseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Exercise and its DTO ExerciseDTO.
 */
@Mapper(componentModel = "spring", uses = {MuscleMapper.class, })
public interface ExerciseMapper {

    @Mapping(source = "skillLevel.id", target = "skillLevelId")
    @Mapping(source = "skillLevel.level", target = "skillLevelLevel")
    @Mapping(source = "exerciseFamily.id", target = "exerciseFamilyId")
    @Mapping(source = "exerciseFamily.name", target = "exerciseFamilyName")
    ExerciseDTO exerciseToExerciseDTO(Exercise exercise);

    List<ExerciseDTO> exercisesToExerciseDTOs(List<Exercise> exercises);

    @Mapping(source = "skillLevelId", target = "skillLevel")
    @Mapping(target = "exerciseInstances", ignore = true)
    @Mapping(source = "exerciseFamilyId", target = "exerciseFamily")
    Exercise exerciseDTOToExercise(ExerciseDTO exerciseDTO);

    List<Exercise> exerciseDTOsToExercises(List<ExerciseDTO> exerciseDTOs);

    default SkillLevel skillLevelFromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillLevel skillLevel = new SkillLevel();
        skillLevel.setId(id);
        return skillLevel;
    }

    default Muscle muscleFromId(Long id) {
        if (id == null) {
            return null;
        }
        Muscle muscle = new Muscle();
        muscle.setId(id);
        return muscle;
    }

    default ExerciseFamily exerciseFamilyFromId(Long id) {
        if (id == null) {
            return null;
        }
        ExerciseFamily exerciseFamily = new ExerciseFamily();
        exerciseFamily.setId(id);
        return exerciseFamily;
    }
}
