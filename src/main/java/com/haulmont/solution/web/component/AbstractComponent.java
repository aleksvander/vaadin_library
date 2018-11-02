package com.haulmont.solution.web.component;

/**
 * Controller for components, recommend use this class for interactions between view and dialog window
 * @param <T1> - any Entity class
 */
public abstract class AbstractComponent<T1>
{
    public abstract void showEditorWindow(T1 entity);
}
