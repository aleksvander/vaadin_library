package com.haulmont.solution.web.presentation.view;

import com.haulmont.solution.backend.entity.BookEntity;
import com.haulmont.solution.backend.projection.BookViewProjection;
import com.haulmont.solution.backend.service.BookService;
import com.haulmont.solution.web.component.BookViewComponent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@SpringView(name = BookView.VIEW_NAME)
public class BookView extends AbstractView implements View {

    public static final String VIEW_NAME = "book";
    private static final Logger LOGGER = LoggerFactory.getLogger(BookView.class);
    BeanItemContainer<BookViewProjection> dataSource = null;

    private BookViewComponent bookViewComponent;
    private BookService bookService;

    @Autowired
    public void setBookViewComponent(BookViewComponent bookViewComponent) {
        this.bookViewComponent = bookViewComponent;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void refresh() {
        Collection<BookEntity> books = bookService.getAllBooks();
        LOGGER.info("get books: {}", books);

        Collection<BookViewProjection> projectionViewBooks = bookService.getProjectionViewBooks();
        dataSource = new BeanItemContainer<>(BookViewProjection.class, projectionViewBooks);

        grid.setContainerDataSource(dataSource);
    }

    @Override
    protected void add() {
        bookViewComponent.showEditorWindow(new BookEntity());
    }

    @Override
    protected void edit() {
        BookEntity bookEntity = getBookEntityBySelectRow();
        if (bookEntity != null)
            bookViewComponent.showEditorWindow(bookEntity);
    }

    private BookEntity getBookEntityBySelectRow() {
        Object selectedRowEntity = grid.getSelectedRow();
        BookEntity bookEntity = null;

        if (selectedRowEntity != null) {
            if (selectedRowEntity instanceof BookViewProjection) {
                bookEntity = bookService.getBookById(((BookViewProjection) selectedRowEntity).getId());
            } else if (selectedRowEntity instanceof BookEntity) {
                bookEntity = (BookEntity) selectedRowEntity;
            }
        }

        return bookEntity;
    }

    @Override
    protected void delete() {
        BookEntity bookEntity = getBookEntityBySelectRow();
        try {
            bookService.removeBook(bookEntity);
        } catch (ConstraintViolationException e) {
            final String errorMessage = String.format("You can delete this row. Error message: %s", e.getSQLException().getMessage());
            LOGGER.error(errorMessage);
            Notification.show(errorMessage);
        }
        refresh();
    }

    @Override
    public void doEnter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        initSearch(dataSource);
    }
}
