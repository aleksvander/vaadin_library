package com.haulmont.solution.web.presentation.view;

import com.haulmont.solution.web.ui.FilteredGrid;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractView extends VerticalLayout implements View
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractView.class);

    static final String ERROR_MESSAGE_YOU_CANT_DELETE_THIS_ROW = "You can't delete this row. Error message: %s";

    private static final String ADD = "Add";
    private static final String EDIT = "Edit";
    private static final String DELETE = "Delete";

    final Grid grid = new FilteredGrid();
    private final CssLayout actions = new CssLayout();

    private final Button addAction = new Button(ADD);
    private final Button editAction = new Button(EDIT);
    private final Button deleteAction = new Button(DELETE);

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        initListeners();
        initLayout();
        refresh();

        doEnter(viewChangeEvent);

        addComponents(actions, grid);
    }

    private void initLayout() {
        addAction.addStyleName(ValoTheme.BUTTON_SMALL);
        editAction.addStyleName(ValoTheme.BUTTON_SMALL);
        deleteAction.addStyleName(ValoTheme.BUTTON_SMALL);

        actions.addComponents(addAction, editAction, deleteAction);

        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }

    private void initListeners() {
        addAction.addClickListener(e -> add());
        editAction.addClickListener(e -> edit());
        deleteAction.addClickListener(e -> delete());
    }

    void initSearch(BeanItemContainer<?> dataSource)
    {
        ((FilteredGrid) grid).setFilterGrid(dataSource);
    }

    public abstract void doEnter(ViewChangeListener.ViewChangeEvent viewChangeEvent);

    public abstract void refresh();

    protected abstract void add();

    protected abstract void edit();

    protected abstract void delete();

    void generateErrorMessage(ConstraintViolationException e)
    {
        final String errorMessage = String.format(ERROR_MESSAGE_YOU_CANT_DELETE_THIS_ROW, e.getSQLException().getMessage());
        LOGGER.error(errorMessage);
        Notification.show(errorMessage);
    }
}
