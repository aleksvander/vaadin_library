package com.haulmont.solution.web.presentation.view;

import com.haulmont.solution.backend.entity.GenreEntity;
import com.haulmont.solution.backend.service.GenreService;
import com.haulmont.solution.web.component.GenreViewComponent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@SpringView(name = GenreView.VIEW_NAME)
public class GenreView extends AbstractView implements View {

    public static final String VIEW_NAME = "genre";
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreView.class);

    private GenreViewComponent genreViewComponent;
    private GenreService genreService;

    @Autowired
    public void setGenreViewComponent(GenreViewComponent genreViewComponent) {
        this.genreViewComponent = genreViewComponent;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void refresh()
    {
        Collection<GenreEntity> genres = genreService.getAllGenres();
        LOGGER.info("get genres: {}", genres);
        grid.setContainerDataSource(new BeanItemContainer<>(GenreEntity.class, genres));
    }

    @Override
    protected void add()
    {
        genreViewComponent.showEditorWindow(new GenreEntity());
    }

    @Override
    protected void edit()
    {
        Object selectedRowEntity = grid.getSelectedRow();
        if (selectedRowEntity != null) {
            GenreEntity genreEntity = (GenreEntity) selectedRowEntity;
            genreViewComponent.showEditorWindow(genreEntity);
        }
    }

    @Override
    protected void delete()
    {
        Object selectedRowEntity = grid.getSelectedRow();
        if (selectedRowEntity != null) {
            try {
                genreService.removeGenre((GenreEntity) selectedRowEntity);
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
