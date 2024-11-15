package com.smanis.coffee;

import java.util.prefs.Preferences;
import java.awt.Point;
import java.awt.Dimension;
import java.util.prefs.BackingStoreException;
import javax.swing.JOptionPane;

public class AppPreferences
{
  public AppPreferences()
  {
  }

  public static final Preferences getPrefs()
  {
    return Preferences.userRoot().node(Constants.APPLICATIONS_HOME).node(Constants.APPLICATION_NAME);  
  }
  
  public static final Preferences getSettingsPrefs()
  {
    return Preferences.userRoot().node(Constants.APPLICATIONS_HOME).node(Constants.APPLICATION_NAME).node(Constants.SETTINGS);  
  }

  public static final void clearWindowPreferences( java.awt.Window window ) 
  {
    try {    
      Preferences.userRoot().node(Constants.APPLICATIONS_HOME).removeNode();
    }
    catch (BackingStoreException ex) {
      JOptionPane.showMessageDialog(window, ex);
    }
  }
  
  public static final void loadWindowPreferences( java.awt.Window window )
  {
    String windowName = window.getName();
    
    Preferences prefs = AppPreferences.getPrefs().node(Constants.SETTINGS).node(Constants.WINDOW_SETTINGS).node(windowName);
    
    Point currentLocation = window.getLocation();
    double currentWindowX = currentLocation.getX();
    double currentWindowY = currentLocation.getY();
    double windowX = prefs.getDouble( Constants.WINDOW_X_POSITION, currentWindowX );
    double windowY = prefs.getDouble( Constants.WINDOW_Y_POSITION, currentWindowY );
    Point newLocation = new Point( (int)windowX, (int)windowY );
    window.setLocation(newLocation);
    
    Dimension currentSize = window.getSize();
    double currentWidth = currentSize.getWidth();
    double currentHeight = currentSize.getHeight();
    double windowWidth = prefs.getDouble( Constants.WINDOW_WIDTH, currentWidth );
    double windowHeight = prefs.getDouble( Constants.WINDOW_HEIGHT, currentHeight );
    Dimension newSize = new Dimension( (int)windowWidth, (int)windowHeight);
    window.setSize(newSize);    
  }

  public static final void saveWindowPreferences(java.awt.Window window)
  {
    String windowName = window.getName();
    
    Preferences prefs = AppPreferences.getPrefs().node(Constants.SETTINGS).node(Constants.WINDOW_SETTINGS).node(windowName);
    
    Point currentLocation = window.getLocation();
    double currentWindowX = currentLocation.getX();
    double currentWindowY = currentLocation.getY();
    prefs.putDouble( Constants.WINDOW_X_POSITION, currentWindowX );
    prefs.putDouble( Constants.WINDOW_Y_POSITION, currentWindowY );
    
    Dimension currentSize = window.getSize();
    double currentWidth = currentSize.getWidth();
    double currentHeight = currentSize.getHeight();
    prefs.putDouble( Constants.WINDOW_WIDTH, currentWidth );
    prefs.putDouble( Constants.WINDOW_HEIGHT, currentHeight );
    
  }

  
  public static void setShouldRememberUsername(boolean shouldRemember) {
      AppPreferences.getPrefs().putBoolean(Constants.SHOULD_REMEMBER_USERNAME, shouldRemember);
  }

  public static boolean getShouldRememberUsername() {
      return AppPreferences.getPrefs().getBoolean(Constants.SHOULD_REMEMBER_USERNAME, false);
  }

  public static void setRememberedUsername(String username) {
      AppPreferences.getPrefs().put(Constants.REMEMBERED_USERNAME, username);
  }

  public static String getRememberedUsername() {
      return AppPreferences.getPrefs().get(Constants.REMEMBERED_USERNAME, "");
  }
  
}


