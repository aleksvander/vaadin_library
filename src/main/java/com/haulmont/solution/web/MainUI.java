package com.haulmont.solution.web;

import com.haulmont.solution.web.presentation.view.AuthorView;
import com.haulmont.solution.web.presentation.view.BookView;
import com.haulmont.solution.web.presentation.view.GenreView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private final SpringViewProvider viewProvider;

    @Autowired
    public MainUI(SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createButtons("Authors", AuthorView.VIEW_NAME));
        navigationBar.addComponent(createButtons("Genres", GenreView.VIEW_NAME));
        navigationBar.addComponent(createButtons("Books", BookView.VIEW_NAME));

        root.addComponent(navigationBar);
        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.setErrorView(new ErrorView());
        navigator.addProvider(viewProvider);
    }

    private Button createButtons(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener((Button.ClickListener) event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    private class ErrorView extends VerticalLayout implements View {

        private static final String DEFAULT_LABEL_MESSAGE = "Could you click some one of the buttons at the top.";
        private Label message;

        ErrorView() {
            setMargin(true);
            message = new Label(DEFAULT_LABEL_MESSAGE);
            addComponent(message);
            message.addStyleName(ValoTheme.LABEL_COLORED);
        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
        }
    }

}
