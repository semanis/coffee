package com.smanis.coffee.forms;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


//DefaultTableModel model = new DefaultTableModel();
//JTable table = new JTable(model);
//
//IconTableCellRenderer iconRenderer = new IconTableCellRenderer();
//table.getColumnModel().getColumn(columnIndex).setCellRenderer(iconRenderer); // Replace columnIndex with the actual column index


public class IconTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Integer val = null;
        
        if(value instanceof Integer && value != null) {
            val = (Integer)value;
        }
        
        ImageIcon icon = null;
        
        setIcon(val == null || val == 0 ? null : new ImageIcon(IconTableCellRenderer.class.getResource("/com/smanis/coffee/assets/checkmark.png")));
        setText(null);

        value = null;
        
        // Apply the default cell renderer style
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return this;
    }
}