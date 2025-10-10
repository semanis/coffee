/*
 */
package com.smanis.coffee;

import com.smanis.coffee.forms.RoastLogEdit;
import java.awt.GraphicsEnvironment;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author semanis
 */
public class Utility {

    /**
     * Calculates the Moisture Loss percentage of the roast.
     *
     * @param greenWeight The weight of the green beans.
     * @param roastedWeight The weight of the beans after roasting.
     *
     * @return A String representation of the moisture loss percentage.
     */
    public static String calculateMoistureLossPercentage(float greenWeight, float roastedWeight) {
        float weightDelta = greenWeight - roastedWeight;

        float calculatedPercentage = (weightDelta / greenWeight) * 100;
        return String.format("%.2f", calculatedPercentage);
    }

    
    public static void checkForCompareAlert(String timerText, long secondsElapsed, ArrayList<String> listTimerAlerts) {
            if (timerText != null) {
                // Beep if the roast timer seconds is within 5 seconds of the comparison timestamps.
                if (AppPreferences.getPrefs().getBoolean(Constants.COMPARISON_TIMER_ALERT, false) == true) {
                    boolean isWithinFiveSeconds = Utility.isWithinFiveSeconds(secondsElapsed, timerText);

                    if (secondsElapsed > 5 && !listTimerAlerts.contains(timerText) && isWithinFiveSeconds == true) {
                        listTimerAlerts.add(timerText);
                        Utility.playComparisonTimerAlert();
                    }
                }
            }

    }
    
    /**
     * Creates a MaskFormatter for use with a JFormattedTextField.
     *
     * @param mask
     *
     * @return A MaskFormatter which uses the specified mask.
     */
    public static MaskFormatter createMaskFormatter(String mask) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(mask);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Utility.createFormatter(String mask): " + e.getMessage());
        }

        formatter.setValidCharacters("0123456789");
        formatter.setOverwriteMode(true);
        return formatter;
    }

    public static DefaultFormatterFactory createDecimalFormatterFactory(String mask, Integer minIntegerDigits, Integer minFractionDigits, Integer maxFractionDigits) {
        DecimalFormat df = new DecimalFormat(mask);
        df.setMinimumIntegerDigits(minIntegerDigits);  // ensures "0.x" is valid
        df.setMinimumFractionDigits(minFractionDigits);

        df.setMaximumFractionDigits(maxFractionDigits);

        // Wrap DecimalFormat in a NumberFormatter
        NumberFormatter numberFormatter = new NumberFormatter(df);
        numberFormatter.setValueClass(Float.class);
        numberFormatter.setAllowsInvalid(false);   // reject invalid characters immediately
        numberFormatter.setCommitsOnValidEdit(true); // commit after valid edit

        return new javax.swing.text.DefaultFormatterFactory(numberFormatter);
    }

    public static DefaultFormatterFactory createIntegerFormatterFactory() {
        // Create a NumberFormat for integers only
        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        intFormat.setGroupingUsed(false); // no commas

        // Create NumberFormatter
        NumberFormatter intFormatter = new NumberFormatter(intFormat);
        intFormatter.setValueClass(Integer.class); // enforce integer type
        intFormatter.setAllowsInvalid(false); // reject non-digits
        intFormatter.setCommitsOnValidEdit(true); // commit as soon as valid
        intFormatter.setMinimum(Integer.MIN_VALUE);
        intFormatter.setMaximum(Integer.MAX_VALUE);

        // Wrap in DefaultFormatterFactory
        return new DefaultFormatterFactory(intFormatter);
    }

    /**
     * Utility method to list available fonts on the target system.
     *
     * @return A String array of font names.
     */
    public static String[] getAvailableFonts() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (String font : fonts) {
            System.out.println("font: " + font);
        }

        return fonts;
    }

    /**
     * Gets the current date formatted as "MM/dd/yyyy".
     *
     * @return A String date representation.
     */
    public static String getFormattedDate() {
        return Utility.getFormattedDate(LocalDateTime.now());
    }

    /**
     * Format a specified Date as "MM/dd/yyyy".
     *
     * @return A String date representation.
     */
    public static String getFormattedDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return formatter.format(dateTime);
    }

    public static String getFormattedTime() {
        return Utility.getFormattedTime(LocalTime.now());
    }

    public static String getFormattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return formatter.format(time);
    }

    public static String getRoastLevel(float moistureLossPercentage) {
        String roastLevel = "";

        if (moistureLossPercentage < 12.10) {
            roastLevel = "City Minus / Cinnamon (11.5%)";
        } else if (moistureLossPercentage < 13.00) {
            roastLevel = "City (12.7%)";
        } else if (moistureLossPercentage < 13.99) {
            roastLevel = "City Plus / Light-Medium (13.3%)";
        } else if (moistureLossPercentage < 14.89) {
            roastLevel = "Full City / Full Medium (14.5%)";
        } else if (moistureLossPercentage < 15.35) {
            roastLevel = "Full City Plus / Medium-Dark (15.1%)";
        } else if (moistureLossPercentage < 16.19) {
            roastLevel = "French Roast (15.6%)";
        } else {
            roastLevel = "Burnt (16.6%+)";
        }

        return roastLevel;
    }

    /**
     * Convenience method to calculate the minutes/seconds between two
     * time-based fields.
     *
     * @param fieldRoastDate
     * @param fieldStart
     * @param fieldEnd
     */
    public static String getTimeDeltaString(JFormattedTextField fieldRoastDate, JFormattedTextField fieldStart, JFormattedTextField fieldEnd) {
        String delta = "";

        if (Utility.isEmptyDate(fieldRoastDate) || Utility.isEmptyTime(fieldStart) || Utility.isEmptyTime(fieldEnd)) {
            return delta;
        }

        String stringRoastDate = (String) fieldRoastDate.getValue();
        String stringStartTime = (String) fieldStart.getValue();
        String stringEndTime = (String) fieldEnd.getValue();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            Date endDate = sdf.parse(stringRoastDate + " " + stringEndTime);
            Date startDate = sdf.parse(stringRoastDate + " " + stringStartTime);

            long diffSeconds = (endDate.getTime() - startDate.getTime()) / 1000;

            long minutes = diffSeconds / 60;
            diffSeconds = diffSeconds - (minutes * 60);

            delta = String.format("%02d", minutes) + ":" + String.format("%02d", diffSeconds);

        } catch (Exception e) {
            Logger.getLogger(RoastLogEdit.class.getName()).log(Level.SEVERE, "Utility.getTimeDeltaString() - " + e.getMessage(), e);
        }

        return delta;
    }

    public static boolean hasTimeValue(JFormattedTextField field) {
        String value = (String) field.getValue();

        return value != null && !value.equals("  :  :  ");
    }

    public static boolean isEmptyDate(JFormattedTextField field) {
        String value = (String) field.getValue();

        return value == null || value.equals("  /  /    ");
    }

    public static boolean isEmptyTime(JFormattedTextField field) {
        String value = (String) field.getValue();

        return value == null || value.equals("  :  :  ");
    }

    public static boolean isEmptyWeight(JFormattedTextField field) {
        Float value = (Float) field.getValue();

        return value == null || value.equals(0.0);
    }

    public static boolean isValidDate(JFormattedTextField textField) {
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        boolean isValidDateString = true;

        try {
            dateFormatter.parse(textField.getText());
        } catch (Exception e) {
            isValidDateString = false;
        }

        return isValidDateString;
    }

    public static boolean isValidTime(JFormattedTextField textField) {
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        boolean isValidTime = true;

        try {
            timeFormatter.parse(textField.getText());
        } catch (Exception e) {
            isValidTime = false;
        }

        return isValidTime;
    }

    /**
     * Used by the RoastLogEdit form to determine if the seconds elapsed for a roast timer is 
     * within 5 seconds of a String timestamp (formatted as "hh:mm:ss").
     * 
     * @param secondsElapsed
     * @param timestamp
     * @return 
     */
    public static boolean isWithinFiveSeconds(long secondsElapsed, String timestamp) {
     
        // Parse the string into a LocalTime object
        LocalTime time = LocalTime.parse(timestamp);
        
        // Convert to total seconds since midnight
        long timestampSeconds = time.toSecondOfDay();
        
        System.out.println("seconds elapsed: " + secondsElapsed);
        System.out.println("timestamp seconds: " + timestampSeconds + "\n");
        
        return timestampSeconds - secondsElapsed <= 5;
    }
    
    public static MaskFormatter getMaskFormatter(String pattern) {
        MaskFormatter mf = null;

        try {
            mf = new MaskFormatter(pattern);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Utility.getMaskFormatter(String pattern): " + e.getMessage());
        }

        return mf;
    }

    /**
     * Converts an MDY date string (e.g., "MM/dd/yyyy" to "yyyy-MM-dd".
     *
     * @return A date string in the ISO-8601 format.
     */
    public static String dateMdyToYmd(String mdy) {
        SimpleDateFormat sdfMdy = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyy-MM-dd");
        String result = "";

        try {
            Date d = sdfMdy.parse(mdy);
            result = sdfYmd.format(d);
        } catch (Exception e) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, "Utility.dateMdyToYdm(String mdy) : " + e.getMessage(), e);
        }

        return result;
    }

    /**
     * Utility method to play a simple tones.
     *
     * @param hz The frequency in Hertz of the beep to be played.
     * @param msecs The millisecond duration of the beep.
     *
     * @throws LineUnavailableException If audio
     */
    public static void playTone(int hz, int msecs) {
        float sampleRate = 44100;
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < msecs * sampleRate / 1000; i++) {
                double angle = i / (sampleRate / hz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127);  // generate waveform
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
        } catch (Exception e) {
            System.out.println("unable to play tone: " + e.getMessage());
        }
    }

    public static void playComparisonTimerAlert() {
        Utility.playTone(900, 95);
        Utility.playTone(900, 95);
        Utility.playTone(600, 100);
    }

    /**
     * Alert tone for 2 minute boundary notifications.s
     */
    public static void play2MinuteAlert() {
        Utility.playTone(1200, 170);
        Utility.playTone(1200, 170);
    }

    /**
     * JFormattedTextFields won't highlight their contents on focus via a call
     * to its own selectAll() method. This generic method cures that issues by
     * way of a runnable, which gets the process off the main Swing GUI thread,
     * so that the GUI thread can finish it's work first.
     *
     * @param field Any field which is/extends a JTextField instance.
     */
    public static void selectAll(JTextField field) {
        Runnable runner = () -> field.selectAll();
        SwingUtilities.invokeLater(runner);
    }

//    public static void showToast(String message) {
//        // Create a JFrame for the Toast container
//        JFrame toastFrame = new JFrame();
//        toastFrame.setUndecorated(true); // No border or title bar
//        toastFrame.setType(JFrame.Type.UTILITY); // Make it a small utility window
//        toastFrame.setSize(500, 400); // Set the size of the Toast window
//        //toastFrame.setLocationRelativeTo(null); 
//
//        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = (int) d.getWidth();
//        int height = (int) d.getHeight();
//
//        int toastXPos = (int) ((width - toastFrame.getWidth()) / 2);
//        int toastYPos = (int) ((height - toastFrame.getHeight()) / 2);
//        System.out.println("xpos is " + toastXPos);
//        System.out.println("ypos is " + toastYPos);
//
//        //toastFrame.setLocation(toastXPos, toastYPos);
//        toastFrame.setLocation(0, 100);
//
//        toastFrame.setBackground(Color.GREEN);
//
//        // Set the background color and make the frame transparent
//        toastFrame.getContentPane().setBackground(new Color(0, 0, 0, 0)); // Transparent background
//        toastFrame.setOpacity(0.8f); // Set the opacity for the frame
//
//        // Create a label to display the message
//        JLabel toastLabel = new JLabel(message, SwingConstants.CENTER);
//        toastLabel.setFont(new Font("Arial", Font.PLAIN, 18));
//        toastLabel.setForeground(Color.WHITE);
//        toastFrame.getContentPane().add(toastLabel);
//
//        // Show the Toast notification on screen
    ////        javax.swing.SwingUtilities.invokeLater(new Runnable() {
////            public void run() {
////                toastFrame.setVisible(true);
////            }
////        });
//        
//        toastFrame.setVisible(true);
//        // Fade the Toast away after 3 seconds
//        Timer fadeOutTimer = new Timer(500, new ActionListener() {
//            private float opacity = 0.8f;
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                opacity -= 0.1f; // Decrease opacity over time
//                if (opacity <= 0) {
//                    toastFrame.setVisible(false); // Hide the Toast when it's completely transparent
//                    ((Timer) e.getSource()).stop(); // Stop the timer
//                } else {
//                    toastFrame.setOpacity(opacity); // Update opacity
//                }
//            }
//        });
//
//        fadeOutTimer.setInitialDelay(200); // Start fading out after 3 seconds
//        fadeOutTimer.start();
//    }

    public static Date stringToDate(String stringDate) {
        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Date parsedDate = sdf.parse(stringDate);
            date = parsedDate;
        } catch (ParseException e) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, "Utility.stringToDate(String stringDate): " + e.getMessage());
        }

        return date;
    }

    public static String sqlDateToString(java.sql.Date date, String pattern) {
        String result = "";

        if (date != null) {
            result = new SimpleDateFormat(pattern).format(date);
        }

        return result;
    }

    public static String sqlFloatToString(float f, String pattern) {
        return String.format(pattern, f);
    }

    public static LocalTime parseTimeString(String timeString) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(timeString, parser);

        return localTime;
    }

    public static void reloadTableRoastLogs() {
    }

    public static void setLookAndFeel(String laf, java.awt.Window window) {
        AppPreferences.getSettingsPrefs().put("lookAndFeel", laf);

        String lookAndFeel = "";

        switch (laf) {
            case "gtk":
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                break;

            case "metal":
                lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
                break;

            case "motif":
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                break;

            case "nimbus":
                lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                break;

            case "windows":
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                break;
            case "windowsclassic":
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
                break;

        }

        if (lookAndFeel.isBlank()) {
            JOptionPane.showMessageDialog(window, "Invalid Look And Feel '" + laf + "'. Valid options are gtk, metal, motif, nimbus, windows, and windowsclassic");
        }

        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(window);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window, "The selected Look and Feel is not available for your Operating System.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Sets the value of a specified JFormattedTextField to a formatted String
     * representation of the specified date.
     *
     * @param field The target JFormattedTextField.
     *
     * @param date A java.sql.Date from a ResultSet field.
     */
    public static void setDate(JFormattedTextField field, java.sql.Date date) {
        if (date == null) {
            field.setValue("");
        } else {
            field.setValue(new SimpleDateFormat("MM/dd/yyyy").format(date));
        }
    }

    public static void setTime(JFormattedTextField field, java.sql.Date date) {
        if (date == null) {
            field.setValue("");
        } else {
            field.setValue(new SimpleDateFormat("hh:mm:ss").format(date));
        }
    }

    public static void setWeight(JFormattedTextField field, float weight) {
        field.setValue(String.format("%4.1f", weight));
    }

    public static void setUIFont(FontUIResource font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }

    /**
     * Validates that a field is not empty and that the value specified is
     * valid, based on the type of field.
     *
     * @param textField Any field derived from JTextField (which includes
     * JFormattedTextFields).
     *
     * @param label The label to use in error messaging for the specified field.
     *
     * @param fieldType Valid types are "date", "string", and "time".
     *
     * @return a boolean <code>true</code> if the specified field is valid,
     *
     * otherwise a <code>false</code>
     */
    public static boolean validateField(JFormattedTextField textField, String label, String fieldType) {
        boolean isValid = true;

        switch (fieldType) {
            case "date":
                if (Utility.isEmptyDate(textField)) {
                    JOptionPane.showMessageDialog(null, "Please enter " + label + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    textField.requestFocus();
                    isValid = false;
                } else if (!Utility.isValidDate(textField)) {
                    JOptionPane.showMessageDialog(null, label + " is not a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                    textField.requestFocus();
                    isValid = false;
                }

                break;

            case "string":
                String text = textField.getText();

                if (text.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please enter " + label + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    textField.requestFocus();
                    return false;
                }

                break;

            case "time":
                if (Utility.isEmptyTime(textField)) {
                    JOptionPane.showMessageDialog(null, "Please enter " + label + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    textField.requestFocus();
                    return false;
                } else if (!Utility.isValidTime(textField)) {
                    JOptionPane.showMessageDialog(null, label + " is not a valid time.", "Error", JOptionPane.ERROR_MESSAGE);
                    textField.requestFocus();
                    isValid = false;
                }

                break;

            case "weight":
                if (Utility.isEmptyWeight(textField)) {
                    JOptionPane.showMessageDialog(null, "Please enter " + label + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    textField.requestFocus();
                    return false;
                }

                break;

        }

        return isValid;
    }

    public static boolean verifyOverwrite() {
        int choice = JOptionPane.showConfirmDialog(null, "Overwrite existing value?", "Warning - Existing Value", JOptionPane.YES_NO_OPTION);

        return choice == JOptionPane.YES_OPTION;
    }

    public static void verifyOverwriteDate(JFormattedTextField field) {
        if (!field.getText().equals("  /  /    ")) {
            if (!Utility.verifyOverwrite()) {
                return;
            }
        }

        Utility.setDate(field, new java.sql.Date(new Date().getTime()));

        try {
            field.commitEdit();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void verifyOverwriteTime(JFormattedTextField field) {
        if (!field.getText().equals("  :  :  ")) {
            if (!Utility.verifyOverwrite()) {
                return;
            }
        }

        field.setText(Utility.getFormattedTime());

        try {
            field.commitEdit();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
