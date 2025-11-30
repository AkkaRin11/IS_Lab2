package ru.akkarin.is_lab2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.akkarin.is_lab2.domain.User;
import ru.akkarin.is_lab2.dto.UserDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
