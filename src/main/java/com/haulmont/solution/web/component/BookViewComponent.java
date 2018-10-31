package com.haulmont.solution.web.component;

import com.haulmont.solution.backend.entity.BookEntity;
import com.haulmont.solution.web.presentation.view.BookView;
import com.haulmont.solution.web.presentation.window.BookDialogWindow;
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
public class BookViewComponent extends AbstractComponent<BookEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookViewComponent.class);

    private BookDialogWindow bookWindow;

    @Autowired
    public void setBookWindow(BookDialogWindow bookWindow) {
        this.bookWindow = bookWindow;
    }

    private final BookView bookView;

    public BookViewComponent(@Lazy BookView bookView) {
        this.bookView = bookView;
    }

    @Override
    public void showEditorWindow(BookEntity bookEntity) {
        bookWindow.setEntity(bookEntity);

        UI.getCurrent().addWindow(bookWindow);
        Window window = UI.getCurrent().getWindows().iterator().next();
        window.addCloseListener(e -> bookView.refresh());
    }
}
