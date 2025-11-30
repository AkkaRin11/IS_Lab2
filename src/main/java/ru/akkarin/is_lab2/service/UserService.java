package ru.akkarin.is_lab2.service;

import ru.akkarin.is_lab2.enm.Role;

public interface UserService {
    void register(String username, String rawPassword, Role role);
}
