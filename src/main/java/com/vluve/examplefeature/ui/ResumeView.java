package com.vluve.examplefeature.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


// Страницу с резюме я не подвязывал к БД, все данные лежат здесь
@Route("/resume")
@PageTitle("My Resume")
@Menu(order = 4, icon = "vaadin:user", title = "Resume")
public class ResumeView extends Div {

    public ResumeView() {
        setSizeFull();
        addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Gap.MEDIUM);

        // Заголовок
        H3 name = new H3("Найденов Владислав");
        name.addClassNames(LumoUtility.FontSize.XXXLARGE, LumoUtility.FontWeight.BOLD);

        Span specialization = new Span("Java Backend Developer | Spring");
        specialization.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.LARGE);

        HorizontalLayout header = new HorizontalLayout(name, specialization);
        header.setWidthFull();
        header.setAlignItems(HorizontalLayout.Alignment.CENTER);
        header.setJustifyContentMode(HorizontalLayout.JustifyContentMode.BETWEEN);


        // Контакты
        HorizontalLayout contacts = new HorizontalLayout();
        contacts.setSpacing(true);
        contacts.add(
                contactItem(VaadinIcon.ENVELOPE, "vladislav.naidyonov@yandex.ru"),
                contactItem(VaadinIcon.PHONE, "8 (920) 578-55-61"),
                contactItem(VaadinIcon.LINK, "@vlu_vtshnik")
        );

        // Опыт работы
        VerticalLayout experienceBlock = new VerticalLayout();
        experienceBlock.add(new H4("Опыт работы"));
        experienceBlock.add(
                experienceItem("JavaFX-разработчик", "БГТУ им. В. Г. Шухова, кафедра БЖД", LocalDate.of(2023,12,1), LocalDate.of(2025,12,9),
                        "Разрабатывал на заказ десктоп-приложение для оценки производственных рисков и формирования документации. " +
                                "Back-end на SQLite, пользовательский интерфейс на JavaFX."),
                experienceItem("Java-разработчик", "ИП Иванченко Д", LocalDate.of(2024,4,1), LocalDate.of(2025,12,9),
                        "Формировал техническое задание на разработку по целям заказчика, проектировал структуру.\n" +
                                "БД и API-запросов.\n" +
                                "Разрабатывал и обслуживал серверную часть приложения на Java Spring Web + PostgreSQL.\n" +
                                "Разрабатывал клиентскую часть приложения на Android (Java + Android Studio)")
        );


        // Образование
        VerticalLayout educationBlock = new VerticalLayout();
        educationBlock.add(new H4("Образование"));
        educationBlock.add(
                educationItem("Кафедра \"Программного обеспечения вычислительной техники и автоматизированных\n" +
                        "систем\", Информатика и вычислительная техника (Бакалавр)", "Белгородский государственный технологический университет\n" +
                        "имени В.Г. Шухова, Белгород", 2020, 2024),
                educationItem("Кафедра \"Программного обеспечения вычислительной техники и автоматизированных\n" +
                        "систем\", Информатика и вычислительная техника (Магистр)", "Белгородский государственный технологический университет\n" +
                        "имени В.Г. Шухова, Белгород", 2024, 2026)
        );


        // Навыки
        VerticalLayout skillsBlock = new VerticalLayout();
        skillsBlock.add(new H4("Навыки"));

        List<Skill> skills = Arrays.asList(
                new Skill("Java", 0.8),
                new Skill("Spring Boot", 0.65),
                new Skill("Vaadin", 0.4),
                new Skill("SQL/PostgreSQL", 0.7),
                new Skill("Git", 0.6),
                new Skill("Docker", 0.4)
        );

        VerticalLayout skillItems = new VerticalLayout();
        skillItems.setSpacing(true);
        for (Skill skill : skills) {
            HorizontalLayout skillLayout = new HorizontalLayout();
            skillLayout.setWidthFull();
            Span label = new Span(skill.name);
            ProgressBar bar = new ProgressBar(0, 1, skill.level);
            bar.setWidth("200px");
            bar.setHeight("10px");
            bar.getStyle().set("border-radius", "5px");
            skillLayout.add(label, bar);
            skillLayout.setAlignItems(HorizontalLayout.Alignment.CENTER);
            skillLayout.setJustifyContentMode(HorizontalLayout.JustifyContentMode.START);
            skillItems.add(skillLayout);
        }
        skillsBlock.add(skillItems);


        add(header, contacts, experienceBlock, educationBlock, skillsBlock);
    }

    // Методы и подклассы для данных
    private HorizontalLayout contactItem(VaadinIcon icon, String value) {
        Icon ic = icon.create();
        ic.setSize("18px");
        Span text = new Span(value);
        HorizontalLayout layout = new HorizontalLayout(ic, text);
        layout.setSpacing(true);
        layout.setAlignItems(HorizontalLayout.Alignment.CENTER);
        return layout;
    }

    private VerticalLayout experienceItem(String title, String company, LocalDate start, LocalDate end, String description) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);

        Span role = new Span(title + " @ " + company);
        role.getStyle().set("font-weight", "600");
        Span dates = new Span(start + " — " + end);
        dates.getStyle().set("color", "#666").set("font-size", "0.9em");
        Span desc = new Span(description);
        desc.getStyle().set("color", "#333");

        layout.add(role, dates, desc);
        layout.getStyle().set("border-left", "4px solid #4CAF50").set("padding-left", "8px").set("margin-bottom", "8px");
        return layout;
    }

    private VerticalLayout educationItem(String degree, String institution, int startYear, int endYear) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);

        Span deg = new Span(degree);
        deg.getStyle().set("font-weight", "600");
        Span inst = new Span(institution);
        inst.getStyle().set("color", "#666").set("font-size", "0.9em");
        Span dates = new Span(startYear + " — " + endYear);
        dates.getStyle().set("color", "#333").set("font-size", "0.9em");

        layout.add(deg, inst, dates);
        layout.getStyle().set("border-left", "4px solid #2196F3").set("padding-left", "8px").set("margin-bottom", "8px");
        return layout;
    }

    private static class Skill {
        String name;
        double level; // от 0 до 1
        Skill(String name, double level) {
            this.name = name;
            this.level = level;
        }
    }
}
