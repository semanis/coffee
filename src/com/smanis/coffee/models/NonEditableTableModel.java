package com.smanis.coffee.models;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {

    public NonEditableTableModel(Vector<Vector<Object>> data, Vector<String> columns) {
        super(data, columns);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
