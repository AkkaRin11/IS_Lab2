// ru.akkarin.is_lab2.view.LoginView
package ru.akkarin.is_lab2.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RequiredArgsConstructor
@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView(AuthenticationManager authenticationManager) {
        setAlignItems(Alignment.CENTER);
        setSpacing(true);

        add(new H2("Вход"));

        TextField username = new TextField("Имя пользователя");
        username.setRequired(true);

        PasswordField password = new PasswordField("Пароль");
        password.setRequired(true);

        Button loginBtn = new Button("Войти", event -> {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username.getValue(), password.getValue())
                );
                getUI().ifPresent(ui -> ui.navigate(""));
            } catch (Exception e) {
                Notification.show("Неверные логин или пароль", 3000, Notification.Position.MIDDLE);
            }
        });

        add(username, password, loginBtn);
    }
}