package com.smanis.coffee.forms;

import com.smanis.coffee.models.BeanModel;
import javax.swing.*;
import java.awt.*;

/**
 * Custom cell renderer used with the bean list component.  This sets the
 * foreground text color differently for out of stock beans.
 */
class CustomListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Color foreground = c.getForeground();
        
        BeanModel bm = (BeanModel)value;

        // Keep the current foreground color if bean is in stock.
        if (bm.isInStock()) {
            c.setForeground(foreground);
        }
        else {
            // highlist out of stock heans differently.
            c.setForeground(Color.gray);
        }
        
        return c;
    }
}