package com.smanis.coffee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class AnimatedTimeline extends JPanel {

    private int timelineWidth;
    private int timelineHeight;
    private int minutesToDisplay;
    private int secondsElapsed = 0;

    private final int paddingLeft = 40;
    private final int paddingRight = 40;
    private final int paddingTop = 60;    // Added extra top padding for P# text above timeline
    private final int paddingBottom = 60; // Added bottom padding for F# text below timeline

    private Timer animationTimer;
    private boolean running = false;

    private float fontSize = 14f;
    private Font baseFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private Font boldFont = baseFont.deriveFont(Font.BOLD, fontSize);

    private static class KeyPressInfo {

        final int digit;
        final int timeInSeconds;

        KeyPressInfo(int digit, int timeInSeconds) {
            this.digit = digit;
            this.timeInSeconds = timeInSeconds;
        }
    }

    private final List<KeyPressInfo> fKeyPresses = new ArrayList<>();     // CTRL+number = F#
    private final List<KeyPressInfo> pKeyPresses = new ArrayList<>();     // SHIFT+number = P#

    public AnimatedTimeline(int width, int height, int minutes) {
        this.timelineWidth = width;
        this.timelineHeight = height;
        this.minutesToDisplay = minutes;
        // Increase preferred height by paddingTop + paddingBottom to fit above & below text
        setPreferredSize(new Dimension(width, height + paddingTop + paddingBottom));
        setOpaque(false);

        animationTimer = new Timer(1000, e -> {
            secondsElapsed++;
            repaint();
        });

        setupKeyBindings();
    }

    public void setFontSize(float size) {
        if (size <= 0) {
            return;
        }
        fontSize = size;
        baseFont = baseFont.deriveFont(fontSize);
        boldFont = baseFont.deriveFont(Font.BOLD);
        repaint();
    }

    private void setupKeyBindings() {
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // CTRL + number => F#
        for (int i = 1; i <= 9; i++) {
            final int digit = i;
            String key = "ctrl " + i;
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + i, KeyEvent.CTRL_DOWN_MASK), key);
            am.put(key, new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    addFKeyInfo(digit);
                }
            });
        }

        // SHIFT + number => P#
        for (int i = 1; i <= 9; i++) {
            final int digit = i;
            String key = "shift " + i;
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + i, KeyEvent.SHIFT_DOWN_MASK), key);
            am.put(key, new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    addPKeyInfo(digit);
                }
            });
        }
    }

    private void addFKeyInfo(int digit) {
        fKeyPresses.add(new KeyPressInfo(digit, secondsElapsed));
        repaint();
    }

    private void addPKeyInfo(int digit) {
        pKeyPresses.add(new KeyPressInfo(digit, secondsElapsed));
        repaint();
    }

    public void start() {
        secondsElapsed = 0;
        running = true;
        animationTimer.start();
    }

    public void stop() {
        running = false;
        animationTimer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int totalSeconds = minutesToDisplay * 60;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        FontMetrics fm = g2.getFontMetrics(baseFont);
        g2.setFont(baseFont);

        String startLabel = "0m";
        String endLabel = minutesToDisplay + "m";
        int startLabelWidth = fm.stringWidth(startLabel);
        int endLabelWidth = fm.stringWidth(endLabel);
        int maxLabelHalfWidth = Math.max(startLabelWidth, endLabelWidth) / 2;

        int adjustedPaddingLeft = paddingLeft + maxLabelHalfWidth;
        int adjustedPaddingRight = paddingRight + maxLabelHalfWidth;

        double usableWidth = getWidth() - adjustedPaddingLeft - adjustedPaddingRight;
        double pixelsPerSecond = usableWidth / totalSeconds;

        // baselineY is now shifted down by paddingTop to allow space above timeline
        int baselineY = paddingTop + timelineHeight / 2;

        // Draw baseline
        g2.setColor(Color.BLACK);
        g2.drawLine(adjustedPaddingLeft, baselineY, getWidth() - adjustedPaddingRight, baselineY);

        // Draw ticks and labels
        for (int sec = 0; sec <= totalSeconds; sec++) {
            int x = adjustedPaddingLeft + (int) (sec * pixelsPerSecond);

            if (sec % 60 == 0) {
                g2.drawLine(x, baselineY - 15, x, baselineY + 15);
                String label = (sec / 60) + "m";
                int labelWidth = fm.stringWidth(label);
                int drawX = x - (labelWidth / 2);
                g2.drawString(label, drawX, baselineY - 20);
            } else if (sec % 15 == 0) {
                g2.drawLine(x, baselineY - 8, x, baselineY + 8);
            }
        }

        // Draw ellipse marker
        if (running) {
            int ellipseX = adjustedPaddingLeft + (int) (secondsElapsed * pixelsPerSecond);
            drawEllipseMarker(g2, ellipseX, baselineY);
        }

        int lineHeight = fm.getHeight();
        int verticalSpacing = 4;

        // Draw P# key presses above timeline (dark red)
        g2.setColor(new Color(180, 0, 0));
        g2.setFont(boldFont);

        // Position P# text well above the timeline line with padding
        int topTextBaselineY = baselineY - 60;

        for (KeyPressInfo kp : pKeyPresses) {
            int x = adjustedPaddingLeft + (int) (kp.timeInSeconds * pixelsPerSecond);

            String pText = "P" + kp.digit;
            int min = kp.timeInSeconds / 60;
            int sec = kp.timeInSeconds % 60;
            String timeText = String.format("%02d:%02d", min, sec);

            int pTextWidth = fm.stringWidth(pText);
            int timeTextWidth = fm.stringWidth(timeText);
            int maxTextWidth = Math.max(pTextWidth, timeTextWidth);

            int pTextX = x - (maxTextWidth / 2) + (maxTextWidth - pTextWidth) / 2;
            int timeTextX = x - (maxTextWidth / 2) + (maxTextWidth - timeTextWidth) / 2;

            // Clamp horizontally
            pTextX = Math.max(pTextX, paddingLeft);
            pTextX = Math.min(pTextX, getWidth() - paddingRight - pTextWidth);
            timeTextX = Math.max(timeTextX, paddingLeft);
            timeTextX = Math.min(timeTextX, getWidth() - paddingRight - timeTextWidth);

            int yP = topTextBaselineY - lineHeight;         // P# on top line
            int yTime = yP + lineHeight + verticalSpacing;  // minutes:seconds below P#

            g2.drawString(pText, pTextX, yP);
            g2.drawString(timeText, timeTextX, yTime);
        }

        // Draw F# key presses below timeline (blue)
        g2.setColor(Color.BLUE.darker());
        g2.setFont(boldFont);

        int bottomTextBaselineY = baselineY + 45;

        for (KeyPressInfo kp : fKeyPresses) {
            int x = adjustedPaddingLeft + (int) (kp.timeInSeconds * pixelsPerSecond);

            String fText = "F" + kp.digit;
            int min = kp.timeInSeconds / 60;
            int sec = kp.timeInSeconds % 60;
            String timeText = String.format("%02d:%02d", min, sec);

            int fTextWidth = fm.stringWidth(fText);
            int timeTextWidth = fm.stringWidth(timeText);
            int maxTextWidth = Math.max(fTextWidth, timeTextWidth);

            int fTextX = x - (maxTextWidth / 2) + (maxTextWidth - fTextWidth) / 2;
            int timeTextX = x - (maxTextWidth / 2) + (maxTextWidth - timeTextWidth) / 2;

            // Clamp horizontally
            fTextX = Math.max(fTextX, paddingLeft);
            fTextX = Math.min(fTextX, getWidth() - paddingRight - fTextWidth);
            timeTextX = Math.max(timeTextX, paddingLeft);
            timeTextX = Math.min(timeTextX, getWidth() - paddingRight - timeTextWidth);

            int yF = bottomTextBaselineY;
            int yTime = yF + lineHeight + verticalSpacing;

            g2.drawString(fText, fTextX, yF);
            g2.drawString(timeText, timeTextX, yTime);
        }

        g2.dispose();
    }

    private void drawEllipseMarker(Graphics2D g2, int x, int baselineY) {
        int width = 15;
        int height = 40;
        int y = baselineY - (height / 2);
        int ellipseX = x - (width / 2);

        Composite originalComposite = g2.getComposite();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setColor(new Color(0, 150, 0));
        g2.fillOval(ellipseX, y, width, height);

        g2.setComposite(originalComposite);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(ellipseX, y, width, height);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Animated Timeline Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1600, 600);

            JPanel container = new JPanel(new GridBagLayout());
            container.setBorder(BorderFactory.createEmptyBorder());

            AnimatedTimeline timeline = new AnimatedTimeline(1500, 100, 15);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.weighty = 0.0;
            container.add(timeline, gbc);

            frame.add(container);
            frame.setVisible(true);

            timeline.start();
            timeline.requestFocusInWindow();
        });
    }
}

//public class AnimatedTimeline extends JPanel {
//
//    private int timelineWidth;
//    private int timelineHeight;
//    private int minutesToDisplay;
//    private int secondsElapsed = 0;
//
//    private final int paddingLeft = 40;
//    private final int paddingRight = 40;
//    private final int paddingTop = 60;    // Added extra top padding for P# text above timeline
//    private final int paddingBottom = 60; // Added bottom padding for F# text below timeline
//
//    private Timer animationTimer;
//    private boolean running = false;
//
//    private float fontSize = 14f;
//    private Font baseFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
//    private Font boldFont = baseFont.deriveFont(Font.BOLD, fontSize);
//
//    private static class KeyPressInfo {
//
//        final int digit;
//        final int timeInSeconds;
//
//        KeyPressInfo(int digit, int timeInSeconds) {
//            this.digit = digit;
//            this.timeInSeconds = timeInSeconds;
//        }
//    }
//
//    private final List<KeyPressInfo> fKeyPresses = new ArrayList<>();     // CTRL+number = F#
//    private final List<KeyPressInfo> pKeyPresses = new ArrayList<>();     // SHIFT+number = P#
//
//    public AnimatedTimeline(int width, int height, int minutes) {
//        this.timelineWidth = width;
//        this.timelineHeight = height;
//        this.minutesToDisplay = minutes;
//        // Increase preferred height by paddingTop + paddingBottom to fit above & below text
//        setPreferredSize(new Dimension(width, height + paddingTop + paddingBottom));
//        setOpaque(false);
//
//        animationTimer = new Timer(1000, e -> {
//            secondsElapsed++;
//            repaint();
//        });
//
//        setupKeyBindings();
//    }
//
//    public void setFontSize(float size) {
//        if (size <= 0) {
//            return;
//        }
//        fontSize = size;
//        baseFont = baseFont.deriveFont(fontSize);
//        boldFont = baseFont.deriveFont(Font.BOLD);
//        repaint();
//    }
//
//    private void setupKeyBindings() {
//        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//        ActionMap am = getActionMap();
//
//        // CTRL + number => F#
//        for (int i = 1; i <= 9; i++) {
//            final int digit = i;
//            String key = "ctrl " + i;
//            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + i, KeyEvent.CTRL_DOWN_MASK), key);
//            am.put(key, new AbstractAction() {
//                @Override
//                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    addFKeyInfo(digit);
//                }
//            });
//        }
//
//        // SHIFT + number => P#
//        for (int i = 1; i <= 9; i++) {
//            final int digit = i;
//            String key = "shift " + i;
//            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + i, KeyEvent.SHIFT_DOWN_MASK), key);
//            am.put(key, new AbstractAction() {
//                @Override
//                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    addPKeyInfo(digit);
//                }
//            });
//        }
//    }
//
//    private void addFKeyInfo(int digit) {
//        fKeyPresses.add(new KeyPressInfo(digit, secondsElapsed));
//        repaint();
//    }
//
//    private void addPKeyInfo(int digit) {
//        pKeyPresses.add(new KeyPressInfo(digit, secondsElapsed));
//        repaint();
//    }
//
//    public void start() {
//        secondsElapsed = 0;
//        running = true;
//        animationTimer.start();
//    }
//
//    public void stop() {
//        running = false;
//        animationTimer.stop();
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        int totalSeconds = minutesToDisplay * 60;
//
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        FontMetrics fm = g2.getFontMetrics(baseFont);
//        g2.setFont(baseFont);
//
//        String startLabel = "0m";
//        String endLabel = minutesToDisplay + "m";
//        int startLabelWidth = fm.stringWidth(startLabel);
//        int endLabelWidth = fm.stringWidth(endLabel);
//        int maxLabelHalfWidth = Math.max(startLabelWidth, endLabelWidth) / 2;
//
//        int adjustedPaddingLeft = paddingLeft + maxLabelHalfWidth;
//        int adjustedPaddingRight = paddingRight + maxLabelHalfWidth;
//
//        double usableWidth = getWidth() - adjustedPaddingLeft - adjustedPaddingRight;
//        double pixelsPerSecond = usableWidth / totalSeconds;
//
//        // baselineY is now shifted down by paddingTop to allow space above timeline
//        int baselineY = paddingTop + timelineHeight / 2;
//
//        // Draw baseline
//        g2.setColor(Color.BLACK);
//        g2.drawLine(adjustedPaddingLeft, baselineY, getWidth() - adjustedPaddingRight, baselineY);
//
//        // Draw ticks and labels
//        for (int sec = 0; sec <= totalSeconds; sec++) {
//            int x = adjustedPaddingLeft + (int) (sec * pixelsPerSecond);
//
//            if (sec % 60 == 0) {
//                g2.drawLine(x, baselineY - 15, x, baselineY + 15);
//                String label = (sec / 60) + "m";
//                int labelWidth = fm.stringWidth(label);
//                int drawX = x - (labelWidth / 2);
//                g2.drawString(label, drawX, baselineY - 20);
//            } else if (sec % 15 == 0) {
//                g2.drawLine(x, baselineY - 8, x, baselineY + 8);
//            }
//        }
//
//        // Draw ellipse marker
//        if (running) {
//            int ellipseX = adjustedPaddingLeft + (int) (secondsElapsed * pixelsPerSecond);
//            drawEllipseMarker(g2, ellipseX, baselineY);
//        }
//
//        int lineHeight = fm.getHeight();
//        int verticalSpacing = 4;
//
//        // Draw P# key presses above timeline (dark red)
//        g2.setColor(new Color(180, 0, 0));
//        g2.setFont(boldFont);
//
//        // Position P# text well above the timeline line with padding
//        int topTextBaselineY = baselineY - 30;
//
//        for (KeyPressInfo kp : pKeyPresses) {
//            int x = adjustedPaddingLeft + (int) (kp.timeInSeconds * pixelsPerSecond);
//
//            String pText = "P" + kp.digit;
//            int min = kp.timeInSeconds / 60;
//            int sec = kp.timeInSeconds % 60;
//            String timeText = String.format("%02d:%02d", min, sec);
//
//            int pTextWidth = fm.stringWidth(pText);
//            int timeTextWidth = fm.stringWidth(timeText);
//            int maxTextWidth = Math.max(pTextWidth, timeTextWidth);
//
//            int pTextX = x - (maxTextWidth / 2) + (maxTextWidth - pTextWidth) / 2;
//            int timeTextX = x - (maxTextWidth / 2) + (maxTextWidth - timeTextWidth) / 2;
//
//            // Clamp horizontally
//            pTextX = Math.max(pTextX, paddingLeft);
//            pTextX = Math.min(pTextX, getWidth() - paddingRight - pTextWidth);
//            timeTextX = Math.max(timeTextX, paddingLeft);
//            timeTextX = Math.min(timeTextX, getWidth() - paddingRight - timeTextWidth);
//
//            int yP = topTextBaselineY - lineHeight;         // P# on top line
//            int yTime = yP + lineHeight + verticalSpacing;  // minutes:seconds below P#
//
//            g2.drawString(pText, pTextX, yP);
//            g2.drawString(timeText, timeTextX, yTime);
//        }
//
//        // Draw F# key presses below timeline (blue)
//        g2.setColor(Color.BLUE.darker());
//        g2.setFont(boldFont);
//
//        int bottomTextBaselineY = baselineY + 45;
//
//        for (KeyPressInfo kp : fKeyPresses) {
//            int x = adjustedPaddingLeft + (int) (kp.timeInSeconds * pixelsPerSecond);
//
//            String fText = "F" + kp.digit;
//            int min = kp.timeInSeconds / 60;
//            int sec = kp.timeInSeconds % 60;
//            String timeText = String.format("%02d:%02d", min, sec);
//
//            int fTextWidth = fm.stringWidth(fText);
//            int timeTextWidth = fm.stringWidth(timeText);
//            int maxTextWidth = Math.max(fTextWidth, timeTextWidth);
//
//            int fTextX = x - (maxTextWidth / 2) + (maxTextWidth - fTextWidth) / 2;
//            int timeTextX = x - (maxTextWidth / 2) + (maxTextWidth - timeTextWidth) / 2;
//
//            // Clamp horizontally
//            fTextX = Math.max(fTextX, paddingLeft);
//            fTextX = Math.min(fTextX, getWidth() - paddingRight - fTextWidth);
//            timeTextX = Math.max(timeTextX, paddingLeft);
//            timeTextX = Math.min(timeTextX, getWidth() - paddingRight - timeTextWidth);
//
//            int yF = bottomTextBaselineY;
//            int yTime = yF + lineHeight + verticalSpacing;
//
//            g2.drawString(fText, fTextX, yF);
//            g2.drawString(timeText, timeTextX, yTime);
//        }
//
//        g2.dispose();
//    }
//
//    private void drawEllipseMarker(Graphics2D g2, int x, int baselineY) {
//        int width = 15;
//        int height = 40;
//        int y = baselineY - (height / 2);
//        int ellipseX = x - (width / 2);
//
//        Composite originalComposite = g2.getComposite();
//
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//        g2.setColor(new Color(0, 150, 0));
//        g2.fillOval(ellipseX, y, width, height);
//
//        g2.setComposite(originalComposite);
//        g2.setColor(Color.BLACK);
//        g2.setStroke(new BasicStroke(2));
//        g2.drawOval(ellipseX, y, width, height);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Animated Timeline Demo");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(1600, 600);
//
//            JPanel container = new JPanel(new GridBagLayout());
//            container.setBorder(BorderFactory.createEmptyBorder());
//
//            AnimatedTimeline timeline = new AnimatedTimeline(1500, 100, 15);
//            timeline.setFontSize(22);
//            
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            gbc.weightx = 1.0;
//            gbc.weighty = 0.0;
//            container.add(timeline, gbc);
//
//            frame.add(container);
//            frame.setVisible(true);
//
//            timeline.start();
//            timeline.requestFocusInWindow();
//        });
//    }
//}
//
