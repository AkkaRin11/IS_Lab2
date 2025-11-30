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
import ru.akkarin.is_lab2.enm.Role;
import ru.akkarin.is_lab2.service.UserService;

@Route("register")
@AnonymousAllowed
@CssImport("./styles/shared-styles-login.css")
public class RegisterView extends Div {

    private final UserService userService;

    public RegisterView(UserService userService) {
        this.userService = userService;

        Div background = new Div();
        background.addClassName("login-background");

        Div container = new Div();
        container.addClassName("login-container");

        H2 title = new H2("Регистрация");

        TextField username = new TextField("Имя пользователя");
        username.setRequired(true);

        PasswordField password = new PasswordField("Пароль");
        password.setRequired(true);

        PasswordField confirmPassword = new PasswordField("Подтвердите пароль");
        confirmPassword.setRequired(true);

        Button registerBtn = new Button("Зарегистрироваться", e -> {
            String user = username.getValue();
            String pass = password.getValue();
            String confirm = confirmPassword.getValue();

            if (!pass.equals(confirm)) {
                Notification.show("Пароли не совпадают");
                return;
            }

            try {
                userService.registerUser(user, pass);
                Notification.show("Пользователь зарегистрирован");
                getUI().ifPresent(ui -> ui.navigate("login"));
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage());
            }
        });

        Span goToLogin = new Span("Уже есть аккаунт? Войти");
        goToLogin.getStyle().set("color", "#0d6efd");
        goToLogin.getStyle().set("text-decoration", "underline");
        goToLogin.getStyle().set("cursor", "pointer");

        goToLogin.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));


        container.add(title, username, password, confirmPassword, registerBtn, goToLogin);
        background.add(container);
        add(background);
    }
}
