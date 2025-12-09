package com.vluve.examplefeature.ui;

import com.vluve.demoapp.pantry.Ingredient;
import com.vluve.demoapp.pantry.IngredientService;
import com.vluve.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Route("/pantry")
@PageTitle("Pantry")
@Menu(order = 2, icon = "vaadin:package", title = "Pantry")
public class PantryView extends Main {

    private final IngredientService ingredientService;

    private final Grid<Ingredient> grid = new Grid<>(Ingredient.class, false);

    public PantryView(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
        initializeUI();

    }

    private void initializeUI() {
        Button addBtn = new Button("Добавить…", e -> openAddDialog());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        grid.addColumn(Ingredient::getName).setHeader("Название");

        grid.addComponentColumn(ing -> {
            NumberField qty = new NumberField();
            qty.setValue((double) ing.getQuantity());
            qty.setReadOnly(true);
            qty.setWidth("5em");

            Button minus = new Button("-", e -> changeQty(ing, -1));
            Button plus = new Button("+", e -> changeQty(ing, +1));

            minus.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            plus.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

            return new HorizontalLayout(minus, qty, plus);
        }).setHeader("Количество");

        grid.addColumn(i -> i.getExpirationDate() != null
                        ? dateFormatter.format(i.getExpirationDate())
                        : "—")
                .setHeader("Годен до");

        grid.setItems(q ->
                ingredientService
                        .list(PageRequest.of(q.getPage(), q.getPageSize()))
                        .stream()
        );

        setSizeFull();
        addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Gap.SMALL
        );

        add(new ViewToolbar("Кладовая", ViewToolbar.group(addBtn)));
        add(grid);
    }

    private void changeQty(Ingredient ing, int delta) {
        int newQty = Math.max(0, ing.getQuantity() + delta);
        ing.setQuantity(newQty);
        ingredientService.save(ing);
        grid.getDataProvider().refreshAll();

        Notification.show("Количество обновлено", 1200, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void openAddDialog() {
        Dialog dialog = new Dialog();

        TextField name = new TextField("Название");
        NumberField qty = new NumberField("Количество");
        qty.setStep(1);

        DatePicker packaged = new DatePicker("Дата упаковки");
        DatePicker shelfLife = new DatePicker("Срок годности");
        DatePicker expiration = new DatePicker("Истекает");

        Button cancel = new Button("Отмена", e -> dialog.close());
        Button ok = new Button("ОК", e -> {
            Ingredient ing = new Ingredient();
            ing.setName(name.getValue());
            ing.setQuantity(qty.getValue() != null ? qty.getValue().intValue() : 0);
            ing.setPackagedDate(packaged.getValue());
            ing.setShelfLife(shelfLife.getValue());
            ing.setExpirationDate(expiration.getValue());

            ingredientService.save(ing);
            grid.getDataProvider().refreshAll();

            dialog.close();

            Notification.show("Ингредиент добавлен", 2000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.add(name, qty, packaged, shelfLife, expiration,
                new HorizontalLayout(cancel, ok));

        dialog.open();
    }
}
