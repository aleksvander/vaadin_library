package com.haulmont.solution.web.presentation.window;

import com.haulmont.solution.backend.entity.GenreEntity;
import com.haulmont.solution.backend.service.GenreService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ViewScope
@SpringComponent
public class GenreDialogWindow extends AbstractDialogWindow<GenreEntity> {
    private static final String NAME = "name";

    private GenreEntity genreEntity;
    private final BeanFieldGroup<GenreEntity> binder = new BeanFieldGroup<>(GenreEntity.class);

    private final GenreService genreService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GenreDialogWindow.class);

    @Autowired
    public GenreDialogWindow(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void setEntity(GenreEntity genreEntity) {
        this.genreEntity = genreEntity;
        binder.setItemDataSource(genreEntity);

        // Buffer the form content
        binder.setBuffered(true);

        binder.setItemDataSource(genreEntity);
        LOGGER.debug("set new entity: {} , in genre dialog window", genreEntity);
    }

    @Override
    protected BeanFieldGroup<GenreEntity> getBinder()
    {
        return binder;
    }

    @Override
    protected void createFields() {
        fields.add(binder.buildAndBind("Name", NAME));
    }

    @Override
    protected void doSave()
    {
        genreService.saveGenre(genreEntity);
    }
}
