package com.vluve.examplefeature.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Home")
@Menu(order = 0, icon = "vaadin:home", title = "Home")
public class MainView extends Div {
    public MainView() {
        setText("Hello world");
    }
}
