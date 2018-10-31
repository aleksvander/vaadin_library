package com.haulmont.solution.web.component;

import com.haulmont.solution.backend.entity.AuthorEntity;
import com.haulmont.solution.web.presentation.view.AuthorView;
import com.haulmont.solution.web.presentation.window.AuthorDialogWindow;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@ViewScope
@Component
public class AuthorViewComponent extends AbstractComponent<AuthorEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorViewComponent.class);

    private AuthorDialogWindow authorWindow;

    @Autowired
    public void setAuthorWindow(AuthorDialogWindow authorWindow) {
        this.authorWindow = authorWindow;
    }

    private final AuthorView authorView;

    public AuthorViewComponent(@Lazy AuthorView authorView) {
        this.authorView = authorView;
    }

    @Override
    public void showEditorWindow(AuthorEntity authorEntity) {
        authorWindow.setEntity(authorEntity);

        UI.getCurrent().addWindow(authorWindow);
        Window window = UI.getCurrent().getWindows().iterator().next();
        window.addCloseListener(e -> authorView.refresh());
    }
}
