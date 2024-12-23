package com.smanis.coffee.service;

import com.smanis.coffee.IconTableCellRenderer;
import com.smanis.coffee.models.NonEditableTableModel;
import com.smanis.coffee.Utility;
import com.smanis.coffee.forms.RoastLogEdit;
import com.smanis.coffee.models.BeanModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * Singleton Service for Table related methods. Everything dealing with the main
 * JTable that displays the roast log data is encapsulated here.
 *
 * Usage:
 *
 * If making a one-off call:
 *
 * NonEditableTableModel tableModel =
 * TableService.getInstance().getTableModelRoastLogs();
 *
 * If making multiple calls back-to-back:
 *
 * TableService service = TableService.getInstance(); NonEditableTableModel
 * tableModel = service.getInstance().getTableModelRoastLogs(); Vector
 * tableColumns = service.getColumnsRoastLog(); Object obj =
 * service.someCrazyObjectReturning Method();
 *
 * @author semanis
 */
public class TableService {

    // private/static property holds the static instance of the service.
    private static TableService INSTANCE = null;

    private static Hashtable<String, Vector<String>> TABLE_COLUMNS = new Hashtable<String, Vector<String>>();

    // constructor is private, so the class can't be directly instantiated.
    private TableService() {
    }

    /**
     * The only way to get at the service is via this method, thereby enforcing
     * its singleton-ness.
     */
    public static TableService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TableService();
        }

        return INSTANCE;
    }

    public void adjustTableColumnWidths(JTable table) {
        int columnCount = table.getColumnCount();

        for (int column = 0; column < columnCount; column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            String headerValue = (String) tableColumn.getHeaderValue();
            FontMetrics metrics = table.getGraphics().getFontMetrics(new Font("Liberation Sans", Font.PLAIN, 20));
            int headerWidth = metrics.stringWidth(headerValue);
            
//            System.out.println("header: " + headerValue + ", width: " + headerWidth);
//            System.out.println("preferredWidth: " + preferredWidth);
//            System.out.println("=====================================");
            
            if (headerWidth > preferredWidth) {
                preferredWidth = headerWidth;
            }
            
            preferredWidth += 20;
            tableColumn.setPreferredWidth(preferredWidth);
        }

    }

    /**
     * Extracts column names from a ResultSet for a given table name, and
     * returns the column names as a Vector<String>.
     *
     * @param rs A ResultSet.
     *
     * @throws Exception
     */
    public Vector<String> getColumnsFromResultSet(ResultSet rs) throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        Vector<String> columnNames = new Vector<String>();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String[] splitColumnName = columnName.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            String splitName = "";
            for (String word : splitColumnName) {
                if (word.equals("Percentage")) {
                    word = "%";
                }
                // separate each word by a space.
                splitName += (splitName.isEmpty() ? "" : " ") + word;
            }

            columnNames.add(splitName);
        }

        return columnNames;
    }

    public int getColumnIndex(String tableName, String columnName) {
        Vector<String> columnNames = TABLE_COLUMNS.get(tableName);
        int columnIndex = columnNames.indexOf(columnName);

        return columnIndex;
    }

    /**
     * Get column names as a Vector<String> for the specified table name.
     */
    public Vector<String> getColumns(String tableName) {
        return TABLE_COLUMNS.get(tableName);
    }

//    /**
//     * The data table uses a simple Vector of Strings as column header names.
//     */
//    public Vector<String> getTableColumnsBeans() {
//        return getColumns("Beans");
//    }
//
//
//    /**
//     * Gets column names for the RoastLog table as Vector<String>.
//     */
//    public Vector<String> getTableColumnsRoastLog() {
//        return getColumns("RoastLog");
//    }
    public DefaultComboBoxModel getComboboxModelBeans() {
        DefaultComboBoxModel model = null;

        try {
            // Query which returns just the Bean ID and Name.
            ResultSet rs = DataService.getInstance().getBeanIdsAndNames();

            Vector<Object> data = new Vector<Object>();

            while (rs.next()) {
                Float density = rs.getFloat("Density");
                data.add(new BeanModel(rs.getString("Id"), rs.getString("Name"), String.format("%.2f", density)));
            }

            model = new DefaultComboBoxModel(data);

        } catch (Exception e) {
            Logger.getLogger(RoastLogEdit.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return model;
    }

    public DefaultListModel getListModelBeans() {
        DefaultListModel<BeanModel> model = new DefaultListModel<BeanModel>();

        try {
            // Query which returns just the Bean ID and Name.
            ResultSet rs = DataService.getInstance().getBeanIdsAndNames();
            
            while (rs.next()) {
                Float density = rs.getFloat("Density");
                model.addElement(new BeanModel(rs.getString("Id"), rs.getString("Name"), String.format("%.2f", density)));
            }

        } catch (Exception e) {
            Logger.getLogger(RoastLogEdit.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return model;
    }

    public Vector<Vector<Object>> getTableDataBeans() throws Exception {
        ResultSet rs = DataService.getInstance().getBeans();

        Vector<Vector<Object>> dataContainer = new Vector<Vector<Object>>();

        // Enforce an ISP-8601 datetime format.
        DateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        while (rs.next()) {
            Vector<Object> data = new Vector<Object>();

            // Be sure you add the data in the same order as the columns are set in getColumnsBeans() above.
            data.add(rs.getString("Id"));
            data.add(rs.getString("Name"));
            data.add(rs.getString("Origin"));
            data.add(rs.getString("Altitude"));
            data.add(rs.getString("ProcessMethod"));
            data.add(rs.getString("DensityGrams"));
            data.add(rs.getString("Density"));
            data.add(rs.getString("GrindSetting"));
            data.add(rs.getInt("Anaerobic"));
            data.add(rs.getInt("InStock"));
            data.add(rs.getString("Comments"));
            dataContainer.add(data);
        }

        return dataContainer;
    }

//    public Vector<Vector<Object>> getTableDataRoastLogs() throws Exception {
//        ResultSet rs = DataService.getInstance().getRoastLogs();
//
//        Vector<String> columnNames = getColumns("RoastLog");
//        
//        // column names are cached after the first call.
//        if (columnNames == null) {
//            columnNames = getColumnsFromResultSet(rs);
//            TABLE_COLUMNS.put("RoastLog", columnNames);
//        }
//
//        return buildTableDataRoastLogs(rs);
//    }
//
//    public Vector<Vector<Object>> getTableDataRoastLogsByBeanId(String beanId) throws Exception {
//        ResultSet rs = DataService.getInstance().getRoastLogsByBeanId(beanId);
//
//        return buildTableDataRoastLogs(rs);
//    }
    /**
     * The data table accepts data as a Vector of Vectors. Each entry in the
     * parent Vector is a table "row" and the list of values in the child Vector
     * are the data for the row's cells. The child Vector is typed as containing
     * "Object", so we can shove any data type into it and later pull it out and
     * cast it to the correct data type.
     */
//    private Vector<Vector<Object>> buildTableDataRoastLogs(ResultSet rs) throws Exception {
//        //ResultSet rs = DataService.getInstance().getRoastLogs();
//
//        Vector<Vector<Object>> dataContainer = new Vector<Vector<Object>>();
//
//        // Enforce an ISP-8601 datetime format.
//        DateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
//        while (rs.next()) {
//            Vector<Object> data = new Vector<Object>();
//
//            // Be sure you add the data in the same order as the columns are set in getColumnsRoastLog() above.
//            data.add(rs.getString("Id"));
//            data.add(rs.getString("BeanId"));
//            data.add(Utility.sqlDateToString(rs.getDate("RoastStart"), "MM/dd/yyyy hh:mm a"));
//            data.add(rs.getString("BeanName"));
//            data.add(String.format("%.2f", rs.getFloat("Density")));
//            data.add(rs.getString("ChargeTemp"));
//            data.add(Utility.sqlFloatToString(rs.getFloat("GreenWeight"), "%5.1f"));
//            data.add(Utility.sqlFloatToString(rs.getFloat("RoastedWeight"), "%5.1f"));
//            data.add(Utility.sqlFloatToString(rs.getFloat("MoistureLossWeight"), "%5.1f"));
//            data.add(Utility.sqlFloatToString(rs.getFloat("MoistureLossPercentage"), "%5.1f"));
//            data.add(rs.getString("RoastLevel"));
//            data.add(Utility.sqlDateToString(rs.getDate("DryTime"), "hh:mm:ss a"));
//            data.add(Utility.sqlDateToString(rs.getDate("FirstCrackStart"), "hh:mm:ss a"));
//            data.add(Utility.sqlDateToString(rs.getDate("FirstCrackEnd"), "hh:mm:ss a"));
//            data.add(Utility.sqlDateToString(rs.getDate("EndRoast"), "hh:mm:ss a"));
//            data.add(rs.getString("RoastNotes"));
//            data.add(rs.getString("TastingNotes"));
//            data.add(rs.getString("TotalDryTime"));
//            data.add(rs.getString("TotalFirstCrackTime"));
//            data.add(rs.getString("TotalDevelopmentTime"));
//            data.add(rs.getString("TotalRoastTime"));
//
//            dataContainer.add(data);
//        }
//
//        return dataContainer;
//    }
    public NonEditableTableModel getTableModelBeans() {
        Vector<String> tableColumns = this.getColumns("Beans");
        Vector tableData = null;

        try {
            tableData = this.getTableDataBeans();
        } catch (Exception e) {
            tableData = new Vector();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return new NonEditableTableModel(tableData, tableColumns);
    }



    
    /**
     * This builds and returns the fully populated and ready to render
     * DefaultTableModel which backs the main JTable of roast log data. Note
     * that I extended the standard DefaultTableModel and tacked on a sprinkle
     * of logic to make all table cells non-editable. I'm lazy and just don't
     * want to code the headache of letting you also do direct data updates via
     * the JTable....
     *
     * @return
     */
    public NonEditableTableModel getTableModelRoastLogsByBeanId(String beanId) {
        Vector<Vector<Object>> dataContainer = new Vector<Vector<Object>>();
        Vector<String> columnNames = new Vector<String>();

        try {
            ResultSet rs = DataService.getInstance().getRoastLogsByBeanId(beanId);

            columnNames = getColumns("RoastLog");

            // column names are cached after the first call.
            if (columnNames == null) {
                columnNames = getColumnsFromResultSet(rs);
                TABLE_COLUMNS.put("RoastLog", columnNames);
            }

            Vector<Object> data = null;

            while (rs.next()) {
                data = new Vector<Object>();

                // Be sure you add the data in the same order as the columns are in the related SQL statement.
                data.add(rs.getString("Id"));
                data.add(rs.getString("BeanId"));
                data.add(Utility.sqlDateToString(rs.getDate("RoastStart"), "MM/dd/yyyy hh:mm a"));
                data.add(rs.getString("RoastLevel"));
                //data.add(String.format("%.2f", rs.getFloat("Density")));
                data.add(Utility.sqlFloatToString(rs.getFloat("GreenWeight"), "%5.1f"));
                data.add(Utility.sqlFloatToString(rs.getFloat("RoastedWeight"), "%5.1f"));
                data.add(Utility.sqlFloatToString(rs.getFloat("MoistureLossPercentage"), "%5.1f"));
                //data.add(Utility.sqlDateToString(rs.getDate("RoastStart"), "MM/dd/yyyy hh:mm a"));
                data.add(rs.getString("TotalRoastTime"));
                data.add(rs.getString("TotalDryTime"));
                data.add(rs.getString("TotalBrowningTime"));
                data.add(rs.getString("TotalFirstCrackTime"));
                data.add(rs.getString("TotalDevelopmentTime"));

//                data.add(Utility.sqlFloatToString(rs.getFloat("MoistureLossWeight"), "%5.1f"));
                data.add(rs.getString("RoastNotes"));
                data.add(rs.getString("TastingNotes"));

                dataContainer.add(data);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return new NonEditableTableModel(dataContainer, columnNames);
    }

    public void hideColumnsBeans(JTable table) {
        this.hideColumn(table, "Id");
        this.hideColumn(table, "Comments");
    }

    /**
     * Simple method to hide columns in the Roast Log JTable, to reduce visual
     * clutter, hide primary keys, etc.
     *
     * @param table The Roast Lot JTable that will have some of its columns
     * hidden,
     */
    public void hideColumnsRoastLog(JTable table) {
        this.hideColumn(table, "Id");
        this.hideColumn(table, "Bean Id");
        this.hideColumn(table, "Roast Notes");
        this.hideColumn(table, "Tasting Notes");
    }

    /**
     * The actual implementation of hiding a column consists of just setting all
     * of its width-related properties to zero, where this could be reverted on
     * demand at any time by setting a width on these values from code.
     *
     * @param table A reference to the JTable object for which you wan to hide
     * one of its columns.
     *
     * @param columnName The name of the column to be hidden.
     */
    private void hideColumn(JTable table, String columnName) {
        TableColumn col = table.getColumn(columnName);
        col.setPreferredWidth(0);
        col.setMinWidth(0);
        col.setWidth(0);
        col.setMaxWidth(0);
    }

    public void setColumnWidthsBeans(JTable table) {
        this.setColumnWidth(table, "Name", 385);
        this.setColumnWidth(table, "Origin", 420);
        this.setColumnWidth(table, "Altitude", 150);
        this.setColumnWidth(table, "Process Method", 155);
        this.setColumnWidth(table, "Density Grams", 145);
        this.setColumnWidth(table, "Density", 76);
        this.setColumnWidth(table, "Grind Setting", 125);
        this.setColumnWidth(table, "Anaerobic?", 130);
        this.setColumnWidth(table, "In Stock?", 130);
    }

    /**
     * Sets sane default widths for all data columns. The user can still drag
     * columns around but none of those changes are currently (I'm lazy,
     * remember?) being written to application Preferences and restored on load.
     *
     * @param table A reference to the Roast Log Jtable.
     */
    public void setColumnWidthsRoastLog(JTable table) {
        this.setColumnWidth(table, "Name", 385);
        this.setColumnWidth(table, "Density", 100);
        this.setColumnWidth(table, "Roast Level", 270);
        this.setColumnWidth(table, "Green Weight", 160);
        this.setColumnWidth(table, "Roasted Weight", 170);
        this.setColumnWidth(table, "Moisture Loss Percentage", 170);
        this.setColumnWidth(table, "Total Roast Time", 150);

//        this.setColumnWidth(table, "Roast Start", 200);
//        //this.setColumnWidth(table, "Charge Temp", 120);
//        this.setColumnWidth(table, "Difference (g)", 130);
//        this.setColumnWidth(table, "Moisture Loss %", 145);
//        this.setColumnWidth(table, "Dry Time", 190);
//        this.setColumnWidth(table, "FC Start", 190);
//        this.setColumnWidth(table, "FC End", 190);
//        this.setColumnWidth(table, "End Roast", 180);
//        this.setColumnWidth(table, "Total Dry Time", 180);
//        this.setColumnWidth(table, "Total FC Time", 180);
//        this.setColumnWidth(table, "Total Dev. Time", 180);
    }

    /**
     * The actual implementation of the logic to set a column's width.
     *
     * @param table A reference to the Roast Log JTable.
     *
     * @param columnName The name of the column to have its width adjusted.
     *
     * @param width The desired column width sort/kinda in pixelcount.
     */
    private void setColumnWidth(JTable table, String columnName, int width) {
        TableColumn col = table.getColumn(columnName);
        col.setPreferredWidth(width);
        col.setMinWidth(width);
        col.setWidth(width);
    }

    public void setupTableBeans(JTable table) {
        TableService.getInstance().hideColumnsBeans(table);
        TableService.getInstance().setColumnWidthsBeans(table);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setRowHeight(40);

        // auto-select the first row.
        table.setRowSelectionInterval(0, 0);

        IconTableCellRenderer iconRenderer = new IconTableCellRenderer();
        iconRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(8).setCellRenderer(iconRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(iconRenderer);

    }

    /**
     * Performs setup duties for the Roast Log table.
     *
     * @param table A reference to the Roast Log table.
     * @param roastNotes A reference to the roast notes textarea field.
     * @param tastingNotes A reference to the tasting notes textarea field.
     */
    public void setupTableRoastLog(JTable table, JTextArea roastNotes, JTextArea tastingNotes) {

        TableService.getInstance().hideColumnsRoastLog(table);
        // TableService.getInstance().setColumnWidthsRoastLog(table);

        //this.tableRoasts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setRowHeight(40);

// auto-select the first row.
        table.setRowSelectionInterval(0, 0);

        TableModel model = table.getModel();

        roastNotes.setText((String) model.getValueAt(0, getColumnIndex("RoastLog", "Roast Notes")));
        tastingNotes.setText((String) model.getValueAt(0, getColumnIndex("RoastLog", "Tasting Notes")));

        this.adjustTableColumnWidths(table);
    }
}
