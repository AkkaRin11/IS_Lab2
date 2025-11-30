package ru.akkarin.is_lab2.service;

import com.vaadin.flow.server.VaadinSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.akkarin.is_lab2.domain.User;
import ru.akkarin.is_lab2.enm.Role;
import ru.akkarin.is_lab2.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private static final String USER_ATTRIBUTE = "user";
    private static final String ROLE_ATTRIBUTE = "role";

    private final UserRepository userRepository;

    public void login(String username) {
        if (username != null && !username.isBlank()) {
            VaadinSession.getCurrent().setAttribute(USER_ATTRIBUTE, username);
            Role role = determineRole(username);
            VaadinSession.getCurrent().setAttribute(ROLE_ATTRIBUTE, role);
        }
    }

    private Role determineRole(String username) {
        return userRepository.findByUsername(username)
                .map(User::getRole)
                .orElse(Role.USER);
    }


    public void logout() {
        VaadinSession currentSession = VaadinSession.getCurrent();
        if (currentSession != null) {
            currentSession.setAttribute(USER_ATTRIBUTE, null);
            currentSession.setAttribute(ROLE_ATTRIBUTE, null);
            currentSession.close();
            if (currentSession.getSession() != null) {
                currentSession.getSession().invalidate();
            }
        }
    }

    public Optional<String> getCurrentUsername() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session != null) {
            return Optional.ofNullable((String) session.getAttribute(USER_ATTRIBUTE));
        }
        return Optional.empty();
    }

    public Optional<Role> getCurrentUserRole() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session != null) {
            return Optional.ofNullable((Role) session.getAttribute(ROLE_ATTRIBUTE));
        }
        return Optional.empty();
    }

    public boolean isAuthenticated() {
        return getCurrentUsername().isPresent();
    }

    public boolean isAdmin() {
        return getCurrentUserRole().map(role -> role == Role.ADMIN).orElse(false);
    }
}
