package ru.akkarin.is_lab2.service;

public interface UserService {
    boolean authenticate(String username, String password);
    void registerUser(String username, String password);
}
