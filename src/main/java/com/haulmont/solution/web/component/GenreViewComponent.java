package com.haulmont.solution.web.component;

import com.haulmont.solution.backend.entity.GenreEntity;
import com.haulmont.solution.web.presentation.view.GenreView;
import com.haulmont.solution.web.presentation.window.GenreDialogWindow;
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
public class GenreViewComponent extends AbstractComponent<GenreEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreViewComponent.class);

    private GenreDialogWindow genreWindow;

    @Autowired
    public void setGenreWindow(GenreDialogWindow genreWindow) {
        this.genreWindow = genreWindow;
    }

    private final GenreView genreView;

    public GenreViewComponent(@Lazy GenreView genreView) {
        this.genreView = genreView;
    }

    @Override
    public void showEditorWindow(GenreEntity genreEntity) {
        genreWindow.setEntity(genreEntity);

        UI.getCurrent().addWindow(genreWindow);
        Window window = UI.getCurrent().getWindows().iterator().next();
        window.addCloseListener(e -> genreView.refresh());
    }
}
