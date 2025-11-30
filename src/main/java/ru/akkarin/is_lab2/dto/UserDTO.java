package ru.akkarin.is_lab2.dto;

import jakarta.persistence.*;
import lombok.Data;
import ru.akkarin.is_lab2.enm.Role;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
