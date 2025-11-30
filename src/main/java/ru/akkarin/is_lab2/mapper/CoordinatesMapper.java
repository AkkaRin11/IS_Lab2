package ru.akkarin.is_lab2.mapper;

import ru.akkarin.is_lab2.domain.Coordinates;
import ru.akkarin.is_lab2.domain.Location;
import ru.akkarin.is_lab2.dto.CoordinatesDTO;
import ru.akkarin.is_lab2.dto.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoordinatesMapper {
    CoordinatesDTO toDto(Coordinates coordinates);
    Coordinates toEntity(CoordinatesDTO coordinatesDTO);
}
