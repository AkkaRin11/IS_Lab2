// ru.akkarin.is_lab2.view.RegisterView
package ru.akkarin.is_lab2.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import ru.akkarin.is_lab2.enm.Role;
import ru.akkarin.is_lab2.service.UserService;

@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView(UserService userService) {
        setAlignItems(Alignment.CENTER);
        setSpacing(true);

        add(new H2("Регистрация"));

        TextField username = new TextField("Имя пользователя");
        username.setRequired(true);

        PasswordField password = new PasswordField("Пароль");
        password.setRequired(true);

        Select<Role> role = new Select<>();
        role.setLabel("Роль");
        role.setItems(Role.USER, Role.ADMIN);
        role.setValue(Role.USER);

        Button registerBtn = new Button("Зарегистрироваться", event -> {
            try {
                userService.register(username.getValue(), password.getValue(), role.getValue());
                Notification.show("Регистрация успешна! Теперь войдите.");
                getUI().ifPresent(ui -> ui.navigate("login"));
            } catch (Exception e) {
                Notification.show("Ошибка: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        FormLayout form = new FormLayout(username, password, role);
        form.setWidth("300px");

        add(form, registerBtn);
    }
}