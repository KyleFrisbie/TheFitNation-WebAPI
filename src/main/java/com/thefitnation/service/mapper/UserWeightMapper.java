package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UserWeightDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserWeight and its DTO UserWeightDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserWeightMapper {

    @Mapping(source = "userDemographic.id", target = "userDemographicId")
    UserWeightDTO userWeightToUserWeightDTO(UserWeight userWeight);

    List<UserWeightDTO> userWeightsToUserWeightDTOs(List<UserWeight> userWeights);

    @Mapping(source = "userDemographicId", target = "userDemographic")
    UserWeight userWeightDTOToUserWeight(UserWeightDTO userWeightDTO);

    List<UserWeight> userWeightDTOsToUserWeights(List<UserWeightDTO> userWeightDTOs);

    default UserDemographic userDemographicFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDemographic userDemographic = new UserDemographic();
        userDemographic.setId(id);
        return userDemographic;
    }
}
