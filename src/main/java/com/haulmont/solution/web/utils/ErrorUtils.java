package com.haulmont.solution.web.utils;

import com.vaadin.data.Validator;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class util for show notify messages from fields components
 */
public class ErrorUtils
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorUtils.class);

    private ErrorUtils()
    {
    }

    private static class ErrorUtilsHelperHolder
    {
        private static final ErrorUtils instance = new ErrorUtils();
    }

    public static ErrorUtils getInstance()
    {
        return ErrorUtils.ErrorUtilsHelperHolder.instance;
    }

    /**
     * Aggregate error messages from components(fields) and return it
     * @param componentArray list of components for get error messages
     * @return errorList from components
     */
    private List<String> getComponentError(
            final AbstractComponent[] componentArray)
    {
        List<String> errorList = new ArrayList<>();

        for (AbstractComponent component : componentArray)
        {
            ErrorMessage errorMessage = component.getErrorMessage();
            if (errorMessage != null)
            {
                errorList.add(errorMessage.getFormattedHtmlMessage());
            }
        }

        LOGGER.debug("errorList: {}", errorList);

        return errorList;
    }

    /**
     * Generate notify validation message
     * @param ex validation cause for get error message
     */
    public void showValidationError(final Validator.InvalidValueException ex)
    {
        for (Validator.InvalidValueException cause : ex.getCauses())
        {
            String validationMessage = cause.getMessage();
            LOGGER.warn("validationMessage: {}", validationMessage);
            (new Notification("Validation message: ", validationMessage, Type.WARNING_MESSAGE, true)).show(Page.getCurrent());
        }
    }

    /**
     * Overload showComponentErrors(AbstractComponent[])
     * @param componentCollection list of components for get error messages
     */
    public void showComponentErrors(
            final Collection<?> componentCollection)
    {
        AbstractComponent[] componentArray = componentCollection
                .toArray(new AbstractComponent[]{});

        showComponentErrors(componentArray);
    }

    /**
     * Generate notify error and custom validator messages from components
     * @param componentArray list of components for get error messages
     */
    private void showComponentErrors(
            final AbstractComponent[] componentArray)
    {
        String errorMessage = getComponentError(componentArray).toString();
        LOGGER.error("errorMessage: {}", errorMessage);

        Notification notification = new Notification("Error", errorMessage, Type.WARNING_MESSAGE, true);

        notification.show(Page.getCurrent());
    }
}