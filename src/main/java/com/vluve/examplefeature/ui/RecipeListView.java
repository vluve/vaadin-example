package com.vluve.examplefeature.ui;

import com.vluve.base.ui.component.ViewToolbar;
import com.vluve.demoapp.recipes.Recipe;
import com.vluve.demoapp.recipes.RecipeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import org.springframework.data.domain.PageRequest;

@Route("/recipes")
@PageTitle("Recipes")
@Menu(order = 3, icon = "vaadin:book", title = "Recipes")
public class RecipeListView extends Div {

    private final RecipeService recipeService;

    public RecipeListView(RecipeService recipeService) {
        this.recipeService = recipeService;
        initializeUI();
    }

    private void initializeUI() {
        TextField searchField = new TextField();
        searchField.setPlaceholder("Поиск...");
        searchField.setWidth("20em");

        Button filterBtn = new Button("Фильтры");

        HorizontalLayout topBar = new HorizontalLayout(searchField, filterBtn);
        topBar.setAlignItems(FlexComponent.Alignment.CENTER);
        topBar.setSpacing(true);

        addClassNames(LumoUtility.Padding.MEDIUM,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Gap.MEDIUM);

        add(new ViewToolbar("Рецепты", topBar));

        recipeService.list(PageRequest.of(0, 50)).forEach(r -> add(createRecipeCard(r)));

    }

    private HorizontalLayout createRecipeCard(Recipe recipe) {

        Image img = new Image(recipe.getImageUrl(), recipe.getTitle());
        img.setWidth("120px");
        img.setHeight("120px");
        img.getStyle().set("border-radius", "12px").set("object-fit", "cover");

        Span title = new Span(recipe.getTitle());
        title.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);

        Span author = new Span("by " + recipe.getAuthor());
        author.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);

        VerticalLayout textBlock = new VerticalLayout(title, author);
        textBlock.setSpacing(false);
        textBlock.setPadding(false);

        HorizontalLayout time = new HorizontalLayout(
                new Icon(VaadinIcon.CLOCK),
                new Span(recipe.getCookTimeMinutes() + " min")
        );

        HorizontalLayout cost = new HorizontalLayout(
                new Icon(VaadinIcon.MONEY),
                new Span(String.format("%.2f ₽", recipe.getCost()))
        );

        HorizontalLayout nutritions = new HorizontalLayout(
                badge(recipe.getCalories() + " kcal", "#FFE082"),
                badge(recipe.getProtein() + "p", "#C5E1A5"),
                badge(recipe.getFats() + "f", "#EF9A9A")
        );

        HorizontalLayout rating = new HorizontalLayout();
        for (int i = 1; i <= 5; i++) {
            Span star = new Span("☆");
            star.getStyle().set("font-size", "20px").set("cursor", "pointer");
            int value = i;
            star.addClickListener(e -> updateStars(rating, value));
            rating.add(star);
        }

        Button saveBtn = new Button("Сохранить", e -> {
            recipeService.save(recipe);
        });

        VerticalLayout right = new VerticalLayout(time, cost, nutritions, rating, saveBtn);
        right.setSpacing(false);
        right.setPadding(false);
        right.setAlignItems(FlexComponent.Alignment.END);

        HorizontalLayout row = new HorizontalLayout(img, textBlock, right);
        row.setWidthFull();
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        row.addClassNames(LumoUtility.Padding.SMALL,
                LumoUtility.Border.BOTTOM,
                LumoUtility.BorderColor.CONTRAST_20);

        return row;
    }

    private Span badge(String text, String color) {
        Span s = new Span(text);
        s.getStyle()
                .set("background-color", color)
                .set("padding", "4px 8px")
                .set("border-radius", "8px")
                .set("font-size", "11px")
                .set("font-weight", "500");
        return s;
    }

    private void updateStars(HorizontalLayout rating, int starsFilled) {
        for (int i = 0; i < rating.getComponentCount(); i++) {
            Span star = (Span) rating.getComponentAt(i);
            star.setText(i < starsFilled ? "★" : "☆");
        }
    }
}
