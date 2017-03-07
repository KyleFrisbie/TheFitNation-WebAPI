package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.AddressDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper {

    AddressDTO addressToAddressDTO(Address address);

    List<AddressDTO> addressesToAddressDTOs(List<Address> addresses);

    @Mapping(target = "location", ignore = true)
    Address addressDTOToAddress(AddressDTO addressDTO);

    List<Address> addressDTOsToAddresses(List<AddressDTO> addressDTOs);
}
