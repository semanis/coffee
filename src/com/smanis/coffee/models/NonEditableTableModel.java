package com.smanis.coffee.models;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
      public NonEditableTableModel(Vector data, Vector columns) {
          super(data, columns);
      }
    
      public boolean isCellEditable(int row, int column){  
          return false;  
      }
}