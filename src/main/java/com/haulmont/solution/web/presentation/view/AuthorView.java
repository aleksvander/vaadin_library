package com.haulmont.solution.web.presentation.view;

import com.haulmont.solution.backend.entity.AuthorEntity;
import com.haulmont.solution.backend.service.AuthorService;
import com.haulmont.solution.web.component.AuthorViewComponent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@SpringView(name = AuthorView.VIEW_NAME)
public class AuthorView extends AbstractView implements View {

    public static final String VIEW_NAME = "author";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorView.class);

    private AuthorViewComponent authorViewComponent;
    private AuthorService authorService;

    @Autowired
    public void setAuthorViewComponent(AuthorViewComponent authorViewComponent) {
        this.authorViewComponent = authorViewComponent;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void refresh()
    {
        Collection<AuthorEntity> authors = authorService.getAllAuthors();
        LOGGER.info("get authors: {}", authors);
        grid.setContainerDataSource(new BeanItemContainer<>(AuthorEntity.class, authors));
    }

    @Override
    protected void add()
    {
        authorViewComponent.showEditorWindow(new AuthorEntity());
    }

    @Override
    protected void edit()
    {
        Object selectedRowEntity = grid.getSelectedRow();
        if (selectedRowEntity != null) {
            AuthorEntity authorEntity = (AuthorEntity) selectedRowEntity;
            authorViewComponent.showEditorWindow(authorEntity);
        }
    }

    @Override
    protected void delete()
    {
        Object selectedRowEntity = grid.getSelectedRow();
        if (selectedRowEntity != null) {
            try {
                authorService.removeAuthor((AuthorEntity) selectedRowEntity);
            } catch (ConstraintViolationException e)
            {
                generateErrorMessage(e);
            }
            refresh();
        }
    }

    @Override
    public void doEnter(ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {

    }
}
