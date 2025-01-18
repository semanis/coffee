/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.smanis.coffee.forms;

import com.smanis.coffee.AppPreferences;
import com.smanis.coffee.Utility;
import com.smanis.coffee.service.DataService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author semanis
 */
public class BeanEdit extends javax.swing.JDialog {

    /**
     * Creates new form BeanEdit
     */
    public BeanEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initOther();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelDetails = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        textName = new javax.swing.JTextField();
        labelVendor = new javax.swing.JLabel();
        textVendor = new javax.swing.JTextField();
        labelPrice = new javax.swing.JLabel();
        textPrice = new javax.swing.JTextField();
        labelWeightInPounds = new javax.swing.JLabel();
        textWeightInPounds = new javax.swing.JTextField();
        labelPricePerPound = new javax.swing.JLabel();
        textPricePerPound = new javax.swing.JTextField();
        labelOrigin = new javax.swing.JLabel();
        textOrigin = new javax.swing.JTextField();
        labelAltitude = new javax.swing.JLabel();
        textAltitude = new javax.swing.JTextField();
        labelProcess = new javax.swing.JLabel();
        comboProcess = new javax.swing.JComboBox<>();
        labelDensityGrams = new javax.swing.JLabel();
        textDensityGrams = new javax.swing.JTextField();
        labelDensity = new javax.swing.JLabel();
        textDensity = new javax.swing.JTextField();
        labelAnaerobic = new javax.swing.JLabel();
        checkboxAnaerobic = new javax.swing.JCheckBox();
        labelVariety = new javax.swing.JLabel();
        textVariety = new javax.swing.JTextField();
        labelInStock = new javax.swing.JLabel();
        checkboxInStock = new javax.swing.JCheckBox();
        labelGrindSetting = new javax.swing.JLabel();
        textGrindSetting = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaComments = new javax.swing.JTextArea();
        labelComments = new javax.swing.JLabel();
        panelButtons = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panelDetails.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        panelDetails.setLayout(new java.awt.GridBagLayout());

        labelName.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelName.setText("Bean Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelName, gridBagConstraints);

        textName.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textName.setText(" ");
        textName.setMinimumSize(new java.awt.Dimension(11, 28));
        textName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textNameFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textName, gridBagConstraints);

        labelVendor.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelVendor.setText("Vendor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelVendor, gridBagConstraints);

        textVendor.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textVendor.setMaximumSize(new java.awt.Dimension(300, 28));
        textVendor.setMinimumSize(new java.awt.Dimension(300, 28));
        textVendor.setPreferredSize(new java.awt.Dimension(300, 28));
        textVendor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textVendorFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textVendor, gridBagConstraints);

        labelPrice.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelPrice.setText("Price");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelPrice, gridBagConstraints);

        textPrice.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textPrice.setMinimumSize(new java.awt.Dimension(90, 28));
        textPrice.setPreferredSize(new java.awt.Dimension(90, 28));
        textPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textPriceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textPriceFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textPrice, gridBagConstraints);

        labelWeightInPounds.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelWeightInPounds.setText("Weight (lbs)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelWeightInPounds, gridBagConstraints);

        textWeightInPounds.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textWeightInPounds.setMinimumSize(new java.awt.Dimension(50, 28));
        textWeightInPounds.setPreferredSize(new java.awt.Dimension(50, 28));
        textWeightInPounds.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textWeightInPoundsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textWeightInPoundsFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textWeightInPounds, gridBagConstraints);

        labelPricePerPound.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelPricePerPound.setText("Price Per Pound");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(labelPricePerPound, gridBagConstraints);

        textPricePerPound.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textPricePerPound.setFocusable(false);
        textPricePerPound.setMaximumSize(new java.awt.Dimension(100, 28));
        textPricePerPound.setMinimumSize(new java.awt.Dimension(100, 28));
        textPricePerPound.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textPricePerPound, gridBagConstraints);

        labelOrigin.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelOrigin.setText("Origin");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelOrigin, gridBagConstraints);

        textOrigin.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textOrigin.setText(" ");
        textOrigin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textOriginFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 567;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textOrigin, gridBagConstraints);

        labelAltitude.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelAltitude.setText("Alltitude");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelAltitude, gridBagConstraints);

        textAltitude.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textAltitude.setText(" ");
        textAltitude.setMaximumSize(new java.awt.Dimension(250, 28));
        textAltitude.setMinimumSize(new java.awt.Dimension(250, 28));
        textAltitude.setPreferredSize(new java.awt.Dimension(250, 28));
        textAltitude.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textAltitudeFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textAltitude, gridBagConstraints);

        labelProcess.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelProcess.setText("Process");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelProcess, gridBagConstraints);

        comboProcess.setEditable(true);
        comboProcess.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        comboProcess.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dry", "Honey", "Natural", "Washed" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(comboProcess, gridBagConstraints);

        labelDensityGrams.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelDensityGrams.setText("Density Grams");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelDensityGrams, gridBagConstraints);

        textDensityGrams.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textDensityGrams.setMinimumSize(new java.awt.Dimension(104, 28));
        textDensityGrams.setPreferredSize(new java.awt.Dimension(104, 28));
        textDensityGrams.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textDensityGramsFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textDensityGrams, gridBagConstraints);

        labelDensity.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelDensity.setText("Density");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelDensity, gridBagConstraints);

        textDensity.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textDensity.setText(".00");
        textDensity.setEnabled(false);
        textDensity.setFocusable(false);
        textDensity.setMaximumSize(new java.awt.Dimension(80, 28));
        textDensity.setMinimumSize(new java.awt.Dimension(80, 28));
        textDensity.setPreferredSize(new java.awt.Dimension(80, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textDensity, gridBagConstraints);

        labelAnaerobic.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelAnaerobic.setText("Anaerobic?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelAnaerobic, gridBagConstraints);

        checkboxAnaerobic.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        checkboxAnaerobic.setMaximumSize(new java.awt.Dimension(21, 28));
        checkboxAnaerobic.setMinimumSize(new java.awt.Dimension(21, 28));
        checkboxAnaerobic.setPreferredSize(new java.awt.Dimension(21, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 10, 0);
        panelDetails.add(checkboxAnaerobic, gridBagConstraints);

        labelVariety.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelVariety.setText("Variety");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(labelVariety, gridBagConstraints);

        textVariety.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textVariety.setMaximumSize(new java.awt.Dimension(400, 28));
        textVariety.setMinimumSize(new java.awt.Dimension(400, 28));
        textVariety.setPreferredSize(new java.awt.Dimension(400, 28));
        textVariety.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textVarietyFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textVariety, gridBagConstraints);

        labelInStock.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelInStock.setText("In Stock?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelInStock, gridBagConstraints);

        checkboxInStock.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        checkboxInStock.setMaximumSize(new java.awt.Dimension(21, 28));
        checkboxInStock.setMinimumSize(new java.awt.Dimension(21, 28));
        checkboxInStock.setPreferredSize(new java.awt.Dimension(21, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 10, 0);
        panelDetails.add(checkboxInStock, gridBagConstraints);

        labelGrindSetting.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelGrindSetting.setText("Grind Setting");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(labelGrindSetting, gridBagConstraints);

        textGrindSetting.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textGrindSetting.setMaximumSize(new java.awt.Dimension(70, 28));
        textGrindSetting.setMinimumSize(new java.awt.Dimension(70, 28));
        textGrindSetting.setPreferredSize(new java.awt.Dimension(70, 28));
        textGrindSetting.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textGrindSettingFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(textGrindSetting, gridBagConstraints);

        textAreaComments.setColumns(20);
        textAreaComments.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        textAreaComments.setRows(5);
        textAreaComments.setWrapStyleWord(true);
        textAreaComments.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textAreaCommentsFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(textAreaComments);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelDetails.add(jScrollPane1, gridBagConstraints);

        labelComments.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelComments.setText("Comments");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panelDetails.add(labelComments, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(panelDetails, gridBagConstraints);

        panelButtons.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        btnSave.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        btnSave.setMnemonic('s');
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        btnCancel.setMnemonic('c');
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonsLayout = new javax.swing.GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
            .addGroup(panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelButtonsLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnCancel)
                    .addGap(5, 5, 5)
                    .addComponent(btnSave)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
            .addGroup(panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelButtonsLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnCancel)
                        .addComponent(btnSave))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        getContentPane().add(panelButtons, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        int response = JOptionPane.showConfirmDialog(BeanEdit.this, "Cancel editing?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
        if (response != JOptionPane.OK_OPTION) {
            return;
        }

        this.exitForm();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.exitForm();
    }//GEN-LAST:event_formWindowClosing

    private void textNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textNameFocusGained
        this.textName.selectAll();
    }//GEN-LAST:event_textNameFocusGained

    private void textVendorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textVendorFocusGained
        this.textVendor.selectAll();
    }//GEN-LAST:event_textVendorFocusGained

    private void textOriginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textOriginFocusGained
        this.textOrigin.selectAll();
    }//GEN-LAST:event_textOriginFocusGained

    private void textVarietyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textVarietyFocusGained
        this.textVariety.selectAll();
    }//GEN-LAST:event_textVarietyFocusGained

    private void textAltitudeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textAltitudeFocusGained
        this.textAltitude.selectAll();
    }//GEN-LAST:event_textAltitudeFocusGained

    private void textGrindSettingFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textGrindSettingFocusGained
        this.textGrindSetting.selectAll();
    }//GEN-LAST:event_textGrindSettingFocusGained

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        this.persistBean();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void textPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPriceFocusGained
        this.textPrice.selectAll();
    }//GEN-LAST:event_textPriceFocusGained

    private void textWeightInPoundsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textWeightInPoundsFocusGained
        this.textWeightInPounds.selectAll();
    }//GEN-LAST:event_textWeightInPoundsFocusGained

    private void textDensityGramsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textDensityGramsFocusGained
        this.textDensityGrams.selectAll();
    }//GEN-LAST:event_textDensityGramsFocusGained

    private void textAreaCommentsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textAreaCommentsFocusGained
        this.textAreaComments.selectAll();
    }//GEN-LAST:event_textAreaCommentsFocusGained

    private void textPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPriceFocusLost
        this.calculatePricePerPound();
    }//GEN-LAST:event_textPriceFocusLost

    private void textWeightInPoundsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textWeightInPoundsFocusLost
        this.calculatePricePerPound();
    }//GEN-LAST:event_textWeightInPoundsFocusLost

    private void calculatePricePerPound() {
        String strPrice = this.textPrice.getText();
        String strWeight = this.textWeightInPounds.getText();
        
        if (strPrice.isEmpty() || strWeight.isEmpty() || strWeight.equals("0")) {
            this.textPricePerPound.setText("");
            return;
        }
        
        float price = Float.valueOf(strPrice);
        float weight = Float.valueOf(strWeight);
        
        this.textPricePerPound.setText(String.format("%.2f", price/weight));
        
    }

    /**
     * This method is invoked during the WindowClosing event or if you press the
     * Exit button. This ensure that the appropriate exit logic is handled where
     * you click the X to close the window, or click the Exit button.
     */
    private void exitForm() {
        AppPreferences.saveWindowPreferences(this);
        this.setVisible(false);
    }

    /**
     * Loads database ResultSet data into the editing form fields.
     *
     * @param rs The target ResultSet.
     */
    private void loadData(String beanId) {

        // Now load the resultset data into the appropriate fields.
        try {
            ResultSet rs = DataService.getInstance().getBeanById(beanId);

            if (rs.next()) {
                this.beanId = (String) rs.getString("Id");
                this.textName.setText(rs.getString("Name"));
                this.textVendor.setText(rs.getString("Vendor"));

                float price = rs.getFloat("Price");
                String formattedPrice = (price == 0.0) ? "00.00" : Utility.sqlFloatToString(price, "%5.2f");
                this.textPrice.setText(formattedPrice);

                this.textWeightInPounds.setText(String.valueOf(rs.getInt("WeightInPounds")));
                this.textPricePerPound.setText(Utility.sqlFloatToString(rs.getFloat("PricePerPound"), "%5.2f"));
                this.textOrigin.setText(rs.getString("Origin"));
                this.textVariety.setText(rs.getString("Variety"));
                this.textAltitude.setText(rs.getString("Altitude"));
                this.comboProcess.setSelectedItem(rs.getString("ProcessMethod"));

                float densityGrams = rs.getFloat("DensityGrams");
                String formattedDensityGrams = (densityGrams == 0.0) ? "000.00" : Utility.sqlFloatToString(densityGrams, "%6.2f");
                this.textDensityGrams.setText(formattedDensityGrams);

                this.textDensity.setText(Utility.sqlFloatToString(rs.getFloat("Density"), "%4.2f"));
                this.checkboxAnaerobic.setSelected(rs.getBoolean("Anaerobic"));
                this.checkboxInStock.setSelected(rs.getBoolean("InStock"));
                this.textGrindSetting.setText(rs.getString("GrindSetting"));
                this.textAreaComments.setText(rs.getString("Comments"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Persists the RoastLog being edited to the database.
     */
    private void persistBean() {
        String beanName = this.textName.getText();
        if (beanName == null || beanName.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter a Bean Name.", "Error", JOptionPane.ERROR_MESSAGE);
            this.textName.requestFocus();
        }

        String stringPrice = (String) this.textPrice.getText();
        float price = 0.0f;

        if (!stringPrice.isBlank()) {
            price = Float.parseFloat(stringPrice);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("Name", beanName);
        map.put("Vendor", this.textVendor.getText());
        map.put("ProcessMethod", (String) this.comboProcess.getSelectedItem());
        map.put("Price", price);

        String stringWeight = this.textWeightInPounds.getText();
        if (stringWeight.isBlank()) {
            stringWeight = "0";
        }
        map.put("WeightInPounds", Integer.valueOf(stringWeight).intValue());

        map.put("Origin", this.textOrigin.getText());
        map.put("Variety", this.textVariety.getText());
        map.put("Altitude", this.textAltitude.getText());

        String stringDensityGrams = this.textDensityGrams.getText();
        if (stringDensityGrams.isBlank()) {
            stringDensityGrams = "000.00";
        }

        map.put("DensityGrams", Float.valueOf(stringDensityGrams).floatValue());
        map.put("Anaerobic", this.checkboxAnaerobic.isSelected() ? 1 : 0);
        map.put("InStock", this.checkboxInStock.isSelected() ? 1 : 0);
        map.put("GrindSetting", this.textGrindSetting.getText());
        map.put("Id", this.beanId);

        try {
            if (this.beanId.equals("")) {
                this.newBeanId = DataService.getInstance().insertBean(map);
            } else {
                DataService.getInstance().updateBean(map);
            }

            JOptionPane.showMessageDialog(this, "Saved.", "Bean Saved", JOptionPane.INFORMATION_MESSAGE);

            this.wasPersisted = true;

            this.exitForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void initOther() {
        JRootPane jrp = this.getRootPane();
        // jrp.setDefaultButton(this.btnSave);

        // Register an action listener on the Escape key which performs a click of the Exit button.
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int response = JOptionPane.showConfirmDialog(BeanEdit.this, "Cancel editing?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
                if (response != JOptionPane.OK_OPTION) {
                    return;
                }

                exitForm();
            }
        };

        // Bind the Escape key to fire the action listener.    
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        jrp.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        AppPreferences.loadWindowPreferences(BeanEdit.this);

    }

    // ===[ Public methods ]======================================================================
    
    public String getNewBeanId() {
        return this.newBeanId;
    }
    
    public void setBeanId(String editBeanId) {
        this.setTitle("Edit Bean");
        this.beanId = editBeanId;

        this.loadData(this.beanId);
    }

    //============================================================================================
    
    public Boolean wasPersisted = false;
    private String beanId = "";
    // If a new Bean is inserted, it's ID will be stored here, so the caller can retrieve it.
    private String newBeanId = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox checkboxAnaerobic;
    private javax.swing.JCheckBox checkboxInStock;
    private javax.swing.JComboBox<String> comboProcess;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAltitude;
    private javax.swing.JLabel labelAnaerobic;
    private javax.swing.JLabel labelComments;
    private javax.swing.JLabel labelDensity;
    private javax.swing.JLabel labelDensityGrams;
    private javax.swing.JLabel labelGrindSetting;
    private javax.swing.JLabel labelInStock;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelOrigin;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelPricePerPound;
    private javax.swing.JLabel labelProcess;
    private javax.swing.JLabel labelVariety;
    private javax.swing.JLabel labelVendor;
    private javax.swing.JLabel labelWeightInPounds;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelDetails;
    private javax.swing.JTextField textAltitude;
    private javax.swing.JTextArea textAreaComments;
    private javax.swing.JTextField textDensity;
    private javax.swing.JTextField textDensityGrams;
    private javax.swing.JTextField textGrindSetting;
    private javax.swing.JTextField textName;
    private javax.swing.JTextField textOrigin;
    private javax.swing.JTextField textPrice;
    private javax.swing.JTextField textPricePerPound;
    private javax.swing.JTextField textVariety;
    private javax.swing.JTextField textVendor;
    private javax.swing.JTextField textWeightInPounds;
    // End of variables declaration//GEN-END:variables
}
