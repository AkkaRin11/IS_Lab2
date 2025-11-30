package ru.akkarin.is_lab2.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.RequiredArgsConstructor;
import ru.akkarin.is_lab2.domain.ImportHistory;
import ru.akkarin.is_lab2.service.AuthService;
import ru.akkarin.is_lab2.service.ImportHistoryService;
import ru.akkarin.is_lab2.service.ImportService;

import java.util.List;

@CssImport("./styles/shared-styles-import.css")
@Route("import")
@AnonymousAllowed
public class ImportView extends VerticalLayout implements BeforeEnterObserver {

    private final ImportHistoryService historyService;
    private final ImportService importService;
    private final AuthService authService;

    private Grid<ImportHistory> historyGrid;

    public ImportView(ImportService importService, AuthService authService, ImportHistoryService historyService) {
        this.importService = importService;
        this.authService = authService;
        this.historyService = historyService;

        setSizeFull();
        setPadding(false);
        setMargin(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Div backgroundDiv = new Div();
        backgroundDiv.addClassName("import-background");
        backgroundDiv.setSizeFull();

        Div container = new Div();
        container.addClassName("import-container");

        H2 title = new H2("Импорт объектов из XML");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".xml");
        upload.setMaxFiles(1);

        Button importButton = new Button("Импортировать");
        importButton.addClassName("import-button");
        importButton.setEnabled(false);

        Button backButton = new Button("Назад");
        backButton.addClassName("back-button");
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("main")));

        Div actions = new Div(importButton, backButton);
        actions.addClassName("import-actions");

        upload.addSucceededListener(event -> importButton.setEnabled(true));

        importButton.addClickListener(e -> {
            try {
                String username = authService.getCurrentUsername().orElse("unknown");
                int countAdded = importService.importFromXml(buffer.getInputStream());
                historyService.recordHistory(username, true, countAdded);

                Notification notification = new Notification(
                        "Импорт выполнен успешно! Добавлено объектов: " + countAdded, 3000
                );
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();

                refreshHistoryGrid();
            } catch (Exception ex) {
                String username = authService.getCurrentUsername().orElse("unknown");
                historyService.recordHistory(username, false, 0);

                Notification notification = new Notification(
                        "Ошибка при импорте: " + ex.getMessage(), 5000
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                ex.printStackTrace();

                refreshHistoryGrid();
            }
        });

        container.add(title, upload, actions);

        historyGrid = new Grid<>(ImportHistory.class, false);
        historyGrid.addColumn(ImportHistory::getId).setHeader("ID").setAutoWidth(true);
        historyGrid.addColumn(ImportHistory::getUsername).setHeader("Пользователь").setAutoWidth(true);
        historyGrid.addColumn(ImportHistory::getStatus).setHeader("Статус").setAutoWidth(true);
        historyGrid.addColumn(ImportHistory::getImportedCount).setHeader("Добавлено объектов").setAutoWidth(true).setFlexGrow(1);
        historyGrid.addColumn(ImportHistory::getTimestamp).setHeader("Время").setAutoWidth(true);

        historyGrid.setWidthFull();
        refreshHistoryGrid();

        container.add(historyGrid);
        backgroundDiv.add(container);
        add(backgroundDiv);
    }

    private void refreshHistoryGrid() {
        String username = authService.getCurrentUsername().orElse("unknown");
        boolean isAdmin = authService.isAdmin();

        List<ImportHistory> histories = isAdmin ? historyService.findAllHistories()
                : historyService.findByUsername(username);
        historyGrid.setItems(histories);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!authService.isAuthenticated()) {
            beforeEnterEvent.forwardTo("login");
        }
    }
}
