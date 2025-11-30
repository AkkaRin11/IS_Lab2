package ru.akkarin.is_lab2.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.PostConstruct;
import ru.akkarin.is_lab2.service.AuthService;
import ru.akkarin.is_lab2.service.UserService;

@Route("login")
@AnonymousAllowed
@CssImport("./styles/shared-styles-login.css")
public class LoginView extends Div {

    private final AuthService authService;
    private final UserService userService;

    public LoginView(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;

        buildUI();
    }

    private void buildUI() {
        Div background = new Div();
        background.addClassName("login-background");

        Div container = new Div();
        container.addClassName("login-container");

        H2 title = new H2("Вход");

        TextField username = new TextField("Имя пользователя");
        username.setRequired(true);

        PasswordField password = new PasswordField("Пароль");
        password.setRequired(true);

        Button loginBtn = new Button("Войти", event -> {
            if (userService.authenticate(username.getValue(), password.getValue())) {
                authService.login(username.getValue());
                getUI().ifPresent(ui -> ui.navigate("main"));
            } else {
                Notification.show("Неверные данные");
            }
        });

        Span goToRegister = new Span("Нет аккаунта? Зарегистрироваться");
        goToRegister.getStyle().set("color", "#0d6efd");
        goToRegister.getStyle().set("text-decoration", "underline");
        goToRegister.getStyle().set("cursor", "pointer");
        goToRegister.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("register")));


        container.add(title, username, password, loginBtn, goToRegister);
        background.add(container);
        add(background);
    }
}
