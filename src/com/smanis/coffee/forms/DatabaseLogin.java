/*
 */
package com.smanis.coffee.forms;

import com.smanis.coffee.AppPreferences;
import com.smanis.coffee.service.DataService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author semanis
 */
public class DatabaseLogin extends javax.swing.JDialog {

    /**
     * Creates new form Login
     */
    public DatabaseLogin(java.awt.Frame parent, boolean modal) {
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

        btnLogin = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelUsername = new javax.swing.JLabel();
        textUsername = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        textPassword = new javax.swing.JPasswordField();
        checkboxRememberUsername = new javax.swing.JCheckBox();

        setTitle("Coffee Roasting Log");
        setName("databaseLogin"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogin.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/smanis/coffee/assets/login.png"))); // NOI18N
        btnLogin.setMnemonic('L');
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, -1, -1));

        btnExit.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/smanis/coffee/assets/exitSmall.png"))); // NOI18N
        btnExit.setMnemonic('x');
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Database Credentials", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog.plain", 0, 20))); // NOI18N
        jPanel1.setFocusable(false);
        jPanel1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelUsername.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelUsername.setText("Username");
        jPanel1.add(labelUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        textUsername.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        jPanel1.add(textUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 160, -1));

        labelPassword.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        labelPassword.setText("Password");
        jPanel1.add(labelPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        textPassword.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        jPanel1.add(textPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 160, -1));

        checkboxRememberUsername.setFont(new java.awt.Font("Dialog.plain", 0, 20)); // NOI18N
        checkboxRememberUsername.setText("remember username");
        jPanel1.add(checkboxRememberUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 380, 170));

        setSize(new java.awt.Dimension(439, 293));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.handleRememberUsername();

        this.loginCancelled = true;
        this.setVisible(false);

    }//GEN-LAST:event_btnExitActionPerformed

    private void handleRememberUsername() {
        if (this.checkboxRememberUsername.isSelected()) {
            AppPreferences.setShouldRememberUsername(true);
            AppPreferences.setRememberedUsername(this.textUsername.getText());
        } else {
            AppPreferences.setShouldRememberUsername(false);
            AppPreferences.setRememberedUsername("");
        }
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = this.textUsername.getText();
        String password = String.valueOf(this.textPassword.getPassword());

        this.handleRememberUsername();

        if (username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter a database user name and password", "Error", JOptionPane.ERROR_MESSAGE);

            if (username.isBlank()) {
                this.textUsername.requestFocus();
            } else if (password.isBlank()) {
                this.textPassword.requestFocus();
            }

            return;
        }

        DataService service = DataService.getInstance();
        service.setCredentials(username, password);

        try {
            service.getConnection();
            this.setVisible(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            this.textPassword.selectAll();
            this.textPassword.requestFocus();
        }

    }//GEN-LAST:event_btnLoginActionPerformed

    public boolean getLoginCancelled() {
        return this.loginCancelled;
    }

    private void initOther() {
        // Setting the default button allows you press ENTER at any time to
        // activate it (so long as the window has focus).
        JRootPane jrp = this.getRootPane();
        jrp.setDefaultButton(this.btnLogin);

        // register an action listener on the Escape key which performs a click
        // of the Exit button.
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnExit.doClick();
            }
        };
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        jrp.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.checkboxRememberUsername.setSelected(AppPreferences.getShouldRememberUsername());

        if (this.checkboxRememberUsername.isSelected()) {
            this.textUsername.setText(AppPreferences.getRememberedUsername());

            // Spin off the focus request to a separate thread so the renderer can finish the 
            // initial render of the view,
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textPassword.requestFocus();
                }
            });
            
        }
    }

    private boolean loginCancelled = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox checkboxRememberUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JPasswordField textPassword;
    private javax.swing.JTextField textUsername;
    // End of variables declaration//GEN-END:variables
}
