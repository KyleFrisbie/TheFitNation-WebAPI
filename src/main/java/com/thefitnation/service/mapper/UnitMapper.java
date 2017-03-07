package com.thefitnation.service.mapper;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.UnitDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Unit and its DTO UnitDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnitMapper {

    UnitDTO unitToUnitDTO(Unit unit);

    List<UnitDTO> unitsToUnitDTOs(List<Unit> units);

    Unit unitDTOToUnit(UnitDTO unitDTO);

    List<Unit> unitDTOsToUnits(List<UnitDTO> unitDTOs);
}
