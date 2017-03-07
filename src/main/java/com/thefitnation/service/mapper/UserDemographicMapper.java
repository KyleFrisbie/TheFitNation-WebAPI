package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserDemographicDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserDemographic and its DTO UserDemographicDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, GymMapper.class, })
public interface UserDemographicMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "skillLevel.id", target = "skillLevelId")
    @Mapping(source = "skillLevel.level", target = "skillLevelLevel")
    UserDemographicDTO userDemographicToUserDemographicDTO(UserDemographic userDemographic);

    List<UserDemographicDTO> userDemographicsToUserDemographicDTOs(List<UserDemographic> userDemographics);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "userWeights", ignore = true)
    @Mapping(target = "workoutTemplates", ignore = true)
    @Mapping(target = "userWorkoutTemplates", ignore = true)
    @Mapping(source = "skillLevelId", target = "skillLevel")
    UserDemographic userDemographicDTOToUserDemographic(UserDemographicDTO userDemographicDTO);

    List<UserDemographic> userDemographicDTOsToUserDemographics(List<UserDemographicDTO> userDemographicDTOs);

    default Gym gymFromId(Long id) {
        if (id == null) {
            return null;
        }
        Gym gym = new Gym();
        gym.setId(id);
        return gym;
    }

    default SkillLevel skillLevelFromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillLevel skillLevel = new SkillLevel();
        skillLevel.setId(id);
        return skillLevel;
    }
}
