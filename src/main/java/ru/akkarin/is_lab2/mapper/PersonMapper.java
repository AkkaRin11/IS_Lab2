package ru.akkarin.is_lab2.mapper;

import ru.akkarin.is_lab2.domain.Person;
import ru.akkarin.is_lab2.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    PersonDTO toDto(Person person);
    Person toEntity(PersonDTO personDTO);
}
