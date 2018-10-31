package com.haulmont.solution.web.presentation.window;

import com.haulmont.solution.web.utils.ErrorUtils;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog window abstract class
 * @param <T>
 */
public abstract class AbstractDialogWindow<T> extends Window
{
    private final static String OK_BUTTON = "Save";
    private final static String CANCEL_BUTTON = "Cancel";

    private VerticalLayout fieldsLayout = new VerticalLayout();
    private ErrorUtils errorUtils = ErrorUtils.getInstance();
    private BeanFieldGroup<T> binder;
    final List<Field<?>> fields = new ArrayList<>();

    AbstractDialogWindow()
    {
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);

        setSizeUndefined();
        center();

        Button okButton = new Button(OK_BUTTON);
        Button cancelButton = new Button(CANCEL_BUTTON);

        okButton.addClickListener(e -> commitChanges());
        cancelButton.addClickListener(e -> revertChanges());

        content.addComponents(fieldsLayout, okButton, cancelButton);
        setContent(content);
    }

    /**
     * Main entry point, need set entity for show dialog point
     * @param entity set bean to use as data source
     */
    public abstract void setEntity(T entity);
    protected abstract BeanFieldGroup<?> getBinder();

    /**
     * Post initializer
     */
    @PostConstruct
    void init()
    {
        initializeBind();
        createFields();
        createComponents();
        initializeComponents();
    }

    /**
     * Initialize configuration binder
     */
    private void initializeBind()
    {
        binder = (BeanFieldGroup<T>) getBinder();
        binder.setReadOnly(false);
        setFieldFactoryOnBinder();
    }

    protected abstract void createFields();

    protected void createComponents()
    {
    }

    /**
     * Add fields in component
     */
    private void initializeComponents() {
        for (Field<?> field : fields) {
            fieldsLayout.addComponent(field);
        }
    }

    /**
     * Fix for Vaadin7 nullable default value
     */
    private void setFieldFactoryOnBinder()
    {
        binder.setFieldFactory(new DefaultFieldGroupFieldFactory()
        {
            /**
             * Override createAbstractTextField for replace "NULL" to ""
             * @param fieldType required fieldType
             * @param <T> all possible field type
             * @return extended from AbstractTextField return field without "NULL"
             */
            @Override
            protected <T extends AbstractTextField> T createAbstractTextField(
                    Class<T> fieldType)
            {
                T field = super.createAbstractTextField(fieldType);
                field.setNullRepresentation("");
                return field;
            }
        });
    }

    /**
     * Native selector need for select value from lined table on window form
     * @param beanItemContainer Set container like Bean Data Source for generate model
     * @param capacity Set capacity property
     * @param fieldName Sets the item caption property
     * @return NativeSelect it's dropdown box like field
     */
    NativeSelect createNativeSelector(final BeanItemContainer beanItemContainer, final String capacity, final String fieldName)
    {
        NativeSelect nativeSelect = new NativeSelect(capacity);
        nativeSelect.setContainerDataSource(beanItemContainer);
        nativeSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        nativeSelect.setItemCaptionPropertyId(fieldName);
        return nativeSelect;
    }

    /**
     * "Save" contract
     */
    protected abstract void doSave();

    private void revertChanges()
    {
        binder.discard();
        close();
    }

    private void commitChanges()
    {
        try
        {
            doValidateFields();

            binder.commit();
            binder.setBuffered(false);

            doSave();

            close();
        }
        catch (FieldGroup.CommitException e)
        {
            errorUtils.showComponentErrors(binder.getFields());
        }
        catch (Validator.InvalidValueException ex)
        {
            errorUtils.showValidationError(ex);
        }
    }

    /**
     * Field validator
     * @throws Validator.InvalidValueException validation exception
     */
    private void doValidateFields() throws Validator.InvalidValueException
    {
        for (Field<?> field : binder.getFields())
        {
            field.validate();
        }
    }
}
