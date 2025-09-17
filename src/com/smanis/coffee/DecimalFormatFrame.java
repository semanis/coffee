package com.smanis.coffee;

/**
 *
 * @author semanis
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;

public class DecimalFormatFrame extends JFrame {

    public DecimalFormatFrame() {
        super("Decimal Format Input (##0.0)");

        // Create DecimalFormat with pattern "##0.0"
        DecimalFormat df = new DecimalFormat("##0.0");
        df.setMinimumFractionDigits(1); // ensure at least 1 decimal
        df.setMaximumFractionDigits(1); // enforce exactly 1 decimal
        df.setMinimumIntegerDigits(1);  // ensures "0.x" is valid

        // Wrap DecimalFormat in a NumberFormatter
        NumberFormatter numberFormatter = new NumberFormatter(df);
        numberFormatter.setValueClass(Double.class);
        numberFormatter.setAllowsInvalid(false);   // reject invalid characters immediately
        numberFormatter.setCommitsOnValidEdit(true); // commit after valid edit

        JFormattedTextField amountField = new JFormattedTextField(numberFormatter);
        amountField.setColumns(6);
        amountField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        amountField.setValue(0.0); // initial value shown as 0.0

        JButton readButton = new JButton(new AbstractAction("Read value") {
            @Override public void actionPerformed(ActionEvent e) {
                Object val = amountField.getValue();
                JOptionPane.showMessageDialog(
                    DecimalFormatFrame.this,
                    "Parsed value: " + val,
                    "Value",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        form.add(new JLabel("Amount (##0.0):"));
        form.add(amountField);
        form.add(readButton);

        setContentPane(form);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationByPlatform(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DecimalFormatFrame().setVisible(true));
    }
}
