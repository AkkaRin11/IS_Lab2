package ru.akkarin.is_lab2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akkarin.is_lab2.domain.User;
import ru.akkarin.is_lab2.enm.Role;
import ru.akkarin.is_lab2.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticate(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(user -> user.getPassword().equals(hashPassword(password)))
                .orElse(false);
    }

    @Override
    @Transactional
    public void registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashPassword(password));
        user.setRole(Role.USER);

        userRepository.save(user);
    }
}

