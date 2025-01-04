package com.smanis.coffee;

import static com.smanis.coffee.Utility.setUIFont;
import com.smanis.coffee.forms.CoffeeFrame;
import com.smanis.coffee.forms.DatabaseLogin;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * This is the main entry point into the Coffee Roasting Log
 *
 * @author semanis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {

//                    String strPrice = "25.95";
//                    String strWeight = "5";
//
//
//                    float price = Float.valueOf(strPrice).floatintValue();
//                    float weight = Float.valueOf(strWeight).intValue();
//
//                    this.textPricePerPound.setText(String.format("%.2f", price / weight));

                    // System.out.println(String.format("%d", 0));
                    UIManager.put("OptionPane.buttonFont", new Font("Liberation Sans", Font.PLAIN, 20));
                    UIManager.put("OptionPane.messageFont", new Font("Liberation Sans", Font.BOLD, 20));
                    UIManager.put("OptionPane.titleText", new Font("Liberation Sans", Font.BOLD, 20));

                    setUIFont(new FontUIResource(new Font("Liberation Sans", Font.PLAIN, 20)));

                    // If a look and feel was saved to preferences, restore the look and feel.
                    String lookAndFeel = AppPreferences.getSettingsPrefs().get("lookAndFeel", "");

                    DatabaseLogin dbLogin = new com.smanis.coffee.forms.DatabaseLogin(null, true);

                    if (!lookAndFeel.isBlank()) {
                        Utility.setLookAndFeel(lookAndFeel, dbLogin);
                    }

                    dbLogin.setVisible(true);

                    if (dbLogin.getLoginCancelled()) {
                        System.exit(0);
                    } else {
                        dbLogin.dispose();

                        CoffeeFrame mainFrame = new CoffeeFrame();
                        mainFrame.setVisible(true);
                    }
                } catch (Exception e) {
                    System.out.println("Error! " + e.getMessage());
                }
            }
        }
        );
    }

}
