package com.smanis.coffee.service;

import com.smanis.coffee.models.NonEditableTableModel;
import com.smanis.coffee.Utility;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
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

    // constructor is private, so the class can't be directly instantiated.s
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

    /**
     * The column layout for the Beans table.
     */
    public Vector getColumnsBeans() {
        Vector v = new Vector();

        v.add("Id");
        v.add("Name");
        v.add("Origin");
        v.add("Altitude");
        v.add("Process Method");
        v.add("Density Grams");
        v.add("Density");
        v.add("In Stock?");
        v.add("Comments");

        return v;
    }

    /**
     * The data table uses a simple Vector of Strings as column header names.
     */
    public Vector getColumnsRoastLog() {
        Vector v = new Vector();

        v.add("Id");
        v.add("Bean Id");
        v.add("Bean Name");
        v.add("Density");
        v.add("Charge Temp");
        v.add("Green Weight (g)");
        v.add("Roasted Weight (g)");
        v.add("Difference (g)");
        v.add("Moisture Loss %");
        v.add("Roast Level");
        v.add("Roast Start");
        v.add("Dry Time");
        v.add("FC Start");
        v.add("FC End");
        v.add("End Roast");
        v.add("Roast Notes");
        v.add("Tasting Notes");
        v.add("Total Dry Time");
        v.add("Total FC Time");
        v.add("Total Dev. Time");
        v.add("Total Roast Time");

        return v;
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
            data.add(rs.getInt("InStock") == 1 ? "*" : "");
            data.add(rs.getString("Comments"));
            dataContainer.add(data);
        }

        return dataContainer;
    }

    /**
     * The data table accepts data as a Vector of Vectors. Each entry in the
     * parent Vector is a table "row" and the list of values in the child Vector
     * are the data for the row's cells. The child Vector is typed as containing
     * "Object", so we can shove any data type into it and later pull it out and
     * cast it to the correct data type.
     */
    public Vector<Vector<Object>> getTableDataRoastLogs() throws Exception {
        ResultSet rs = DataService.getInstance().getRoastLogs();

        Vector<Vector<Object>> dataContainer = new Vector<Vector<Object>>();

        // Enforce an ISP-8601 datetime format.
        DateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        while (rs.next()) {
            Vector<Object> data = new Vector<Object>();

            // Be sure you add the data in the same order as the columns are set in getColumnsRoastLog() above.
            data.add(rs.getString("Id"));
            data.add(rs.getString("BeanId"));
            data.add(rs.getString("BeanName"));
            data.add(String.format("%.2f", rs.getFloat("Density")));
            data.add(rs.getString("ChargeTemp"));
            data.add(Utility.sqlFloatToString(rs.getFloat("GreenWeight"), "%5.1f"));
            data.add(Utility.sqlFloatToString(rs.getFloat("RoastedWeight"), "%5.1f"));
            data.add(Utility.sqlFloatToString(rs.getFloat("MoistureLossWeight"), "%5.1f"));
            data.add(Utility.sqlFloatToString(rs.getFloat("MoistureLossPercentage"), "%5.1f"));
            data.add(rs.getString("RoastLevel"));
            data.add(Utility.sqlDateToString(rs.getDate("RoastStart"), "MM/dd/yyyy hh:mm a"));
            data.add(Utility.sqlDateToString(rs.getDate("DryTime"), "hh:mm:ss a"));
            data.add(Utility.sqlDateToString(rs.getDate("FirstCrackStart"), "hh:mm:ss a"));
            data.add(Utility.sqlDateToString(rs.getDate("FirstCrackEnd"), "hh:mm:ss a"));
            data.add(Utility.sqlDateToString(rs.getDate("EndRoast"), "hh:mm:ss a"));
            data.add(rs.getString("RoastNotes"));
            data.add(rs.getString("TastingNotes"));
            data.add(rs.getString("TotalDryTime"));
            data.add(rs.getString("TotalFirstCrackTime"));
            data.add(rs.getString("TotalDevelopmentTime"));
            data.add(rs.getString("TotalRoastTime"));

            dataContainer.add(data);
        }

        return dataContainer;
    }


    public NonEditableTableModel getTableModelBeans() {
        Vector tableColumns = this.getColumnsBeans();
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
    public NonEditableTableModel getTableModelRoastLogs() {
        Vector tableColumns = this.getColumnsRoastLog();
        Vector tableData = null;

        try {
            tableData = this.getTableDataRoastLogs();
        } catch (Exception e) {
            tableData = new Vector();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return new NonEditableTableModel(tableData, tableColumns);
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
        this.setColumnWidth(table, "Density", 70);
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
        this.setColumnWidth(table, "Bean Name", 385);
        this.setColumnWidth(table, "Roast Start", 200);
        this.setColumnWidth(table, "Charge Temp", 120);
        this.setColumnWidth(table, "Green Weight (g)", 160);
        this.setColumnWidth(table, "Roasted Weight (g)", 170);
        this.setColumnWidth(table, "Difference (g)", 130);
        this.setColumnWidth(table, "Moisture Loss %", 145);
        this.setColumnWidth(table, "Roast Level", 240);
        this.setColumnWidth(table, "Dry Time", 190);
        this.setColumnWidth(table, "FC Start", 190);
        this.setColumnWidth(table, "FC End", 190);
        this.setColumnWidth(table, "End Roast", 180);
        this.setColumnWidth(table, "Total Dry Time", 180);
        this.setColumnWidth(table, "Total FC Time", 180);
        this.setColumnWidth(table, "Total Dev. Time", 180);
        this.setColumnWidth(table, "Total Roast Time", 180);

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
        TableService.getInstance().setColumnWidthsRoastLog(table);

        //this.tableRoasts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setRowHeight(40);
        // auto-select the first row.
        table.setRowSelectionInterval(0, 0);

        TableModel model = table.getModel();

        int rowCount = model.getRowCount();

        for (int i = 0; i > rowCount; i++) {
            System.out.println("Value for column " + i + " is: " + model.getValueAt(0, i));
        }

        roastNotes.setText((String) model.getValueAt(0, 15));
        tastingNotes.setText((String) model.getValueAt(0, 16));

    }
}
