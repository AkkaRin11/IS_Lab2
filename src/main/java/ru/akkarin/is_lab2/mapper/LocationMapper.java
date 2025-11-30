package ru.akkarin.is_lab2.mapper;

import ru.akkarin.is_lab2.domain.Location;
import ru.akkarin.is_lab2.domain.Person;
import ru.akkarin.is_lab2.dto.LocationDTO;
import ru.akkarin.is_lab2.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    LocationDTO toDto(Location location);
    Location toEntity(LocationDTO locationDTO);
}
