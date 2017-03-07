package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.GymDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Gym and its DTO GymDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GymMapper {

    @Mapping(source = "location.id", target = "locationId")
    GymDTO gymToGymDTO(Gym gym);

    List<GymDTO> gymsToGymDTOs(List<Gym> gyms);

    @Mapping(target = "userDemographics", ignore = true)
    @Mapping(source = "locationId", target = "location")
    Gym gymDTOToGym(GymDTO gymDTO);

    List<Gym> gymDTOsToGyms(List<GymDTO> gymDTOs);

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
