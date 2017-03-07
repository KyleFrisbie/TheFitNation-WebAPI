package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.LocationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationMapper {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetAddressOne", target = "addressStreetAddressOne")
    LocationDTO locationToLocationDTO(Location location);

    List<LocationDTO> locationsToLocationDTOs(List<Location> locations);

    @Mapping(target = "gym", ignore = true)
    @Mapping(source = "addressId", target = "address")
    Location locationDTOToLocation(LocationDTO locationDTO);

    List<Location> locationDTOsToLocations(List<LocationDTO> locationDTOs);

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
