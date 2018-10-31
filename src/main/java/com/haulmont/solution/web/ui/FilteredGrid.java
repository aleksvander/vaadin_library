package com.haulmont.solution.web.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

/**
 * Class which extend Grid functional
 * * added field filter for textfield;
 */
public class FilteredGrid extends Grid {

    /**
     * Add filter on grid on each head row
     * @param beanType java bean
     */
    public void setFilterGrid(BeanItemContainer<?> beanType) {
        HeaderRow filterRow = this.appendHeaderRow();
        for (Column column : getColumns()) {
            HeaderCell cellFilter = filterRow.getCell(column.getPropertyId());
            cellFilter.setComponent(createFieldFilter(beanType, column));
        }
    }

    /**
     * Create textfield filter
     * @param container bean
     * @param column A column in the grid, for get Property id
     * @return filter like TextField
     */
    private TextField createFieldFilter(final BeanItemContainer<?> container, final Column column) {
        TextField filter = new TextField();
        filter.setImmediate(true);
        filter.addTextChangeListener((FieldEvents.TextChangeListener) event -> {
            String newValue = event.getText();
            container.removeContainerFilters(column.getPropertyId());
            if (newValue != null && !newValue.isEmpty()) {
                container.addContainerFilter(new SimpleStringFilter(column.getPropertyId(), newValue, true, false));
            }
            recalculateColumnWidths();
        });
        return filter;
    }
}
