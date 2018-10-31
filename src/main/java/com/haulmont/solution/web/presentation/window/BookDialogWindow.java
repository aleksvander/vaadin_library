package com.haulmont.solution.web.presentation.window;

import com.haulmont.solution.backend.entity.AuthorEntity;
import com.haulmont.solution.backend.entity.BookEntity;
import com.haulmont.solution.backend.entity.GenreEntity;
import com.haulmont.solution.backend.service.AuthorService;
import com.haulmont.solution.backend.service.BookService;
import com.haulmont.solution.backend.service.GenreService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.NativeSelect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ViewScope
@SpringComponent
public class BookDialogWindow extends AbstractDialogWindow<BookEntity> {
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String GENRE = "genre";
    private static final String PUBLISHER = "publisher";
    private static final String PUBLICATION_YEAR = "publicationYear";
    private static final String CITY = "city";

    private BookEntity bookEntity;
    private final BeanFieldGroup<BookEntity> binder = new BeanFieldGroup<BookEntity>(BookEntity.class);

    private final BookService bookService;

    private GenreService genreService;
    private AuthorService authorService;

    @Autowired
    public void setGenreService(GenreService genreService)
    {
        this.genreService = genreService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService)
    {
        this.authorService = authorService;
    }

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BookDialogWindow.class);

    @Autowired
    public BookDialogWindow(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void setEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
        binder.setItemDataSource(bookEntity);

        // Buffer the form content
        binder.setBuffered(true);

        binder.setItemDataSource(bookEntity);
        LOGGER.debug("set new entity: {} , in book dialog window", bookEntity);
    }

    @Override
    protected BeanFieldGroup<BookEntity> getBinder()
    {
        return binder;
    }

    @Override
    protected void createFields() {
        fields.add(binder.buildAndBind("Title", NAME));
        fields.add(binder.buildAndBind("Publisher", PUBLISHER));
        fields.add(binder.buildAndBind("Publication year", PUBLICATION_YEAR));
        fields.add(binder.buildAndBind("City", CITY));
    }

    @Override
    protected void createComponents() {
        BeanItemContainer<AuthorEntity> authorEntityBeanItemContainer = new BeanItemContainer<>(AuthorEntity.class, authorService.getAllAuthors());
        NativeSelect authorSelector = createNativeSelector(authorEntityBeanItemContainer, "Author", "lastName");
        binder.bind(authorSelector, AUTHOR);
        fields.add(authorSelector);

        BeanItemContainer<GenreEntity> genreEntityBeanItemContainer = new BeanItemContainer<>(GenreEntity.class, genreService.getAllGenres());
        NativeSelect genreSelector = createNativeSelector(genreEntityBeanItemContainer, "Genre", "name");
        binder.bind(genreSelector, GENRE);
        fields.add(genreSelector);
    }

    @Override
    protected void doSave()
    {
        bookService.saveBook(bookEntity);
    }
}
