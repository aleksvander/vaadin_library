package com.haulmont.solution.web.presentation.window;

import com.haulmont.solution.backend.entity.AuthorEntity;
import com.haulmont.solution.backend.service.AuthorService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ViewScope
@SpringComponent
public class AuthorDialogWindow extends AbstractDialogWindow<AuthorEntity> {
    private static final String FIRST_NAME = "name";
    private static final String LAST_NAME = "lastName";
    private static final String MIDDLE_NAME = "middleName";

    private AuthorEntity authorEntity;
    private final BeanFieldGroup<AuthorEntity> binder = new BeanFieldGroup<>(AuthorEntity.class);

    private final AuthorService authorService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AuthorDialogWindow.class);

    @Autowired
    public AuthorDialogWindow(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void setEntity(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
        binder.setItemDataSource(authorEntity);

        // Buffer the form content
        binder.setBuffered(true);

        binder.setItemDataSource(authorEntity);
        LOGGER.debug("set new entity: {} , in author dialog window", authorEntity);
    }

    @Override
    protected BeanFieldGroup<AuthorEntity> getBinder()
    {
        return binder;
    }

    @Override
    protected void createFields() {
        fields.add(binder.buildAndBind("Last Name", LAST_NAME));
        fields.add(binder.buildAndBind("Name", FIRST_NAME));
        fields.add(binder.buildAndBind("Middle Name", MIDDLE_NAME));
    }

    @Override
    protected void doSave()
    {
        authorService.saveAuthor(authorEntity);
    }
}
