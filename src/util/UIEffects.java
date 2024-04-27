package util;

import javax.swing.*;
import java.awt.*;

public class UIEffects {
    public static final Color PRIMARY_COLOR = new Color(60, 179, 113); // Medium Sea Green, used sparingly
    public static final Color BUTTON_COLOR = new Color(0, 128, 128); // Teal, darker for better visibility
    public static final Color BACKGROUND_COLOR = new Color(190, 255, 250); // Mint Cream, very light
    public static final Color FONT_COLOR = Color.BLACK; // White for text inside buttons for high contrast
    public static final Color BORDER_COLOR = Color.BLACK;//new Color(255, 255, 255); // White border for contrast

    public static void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(FONT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2), // Outer border for visibility
                BorderFactory.createEmptyBorder(10, 25, 10, 25) // Padding inside the button
        ));
    }

    public static void applyButtonAnimation(JButton button) {
        Timer timer = new Timer(1000, e -> {
            if (button.getBackground().equals(BUTTON_COLOR)) {
                button.setBackground(BUTTON_COLOR.brighter()); // A slightly lighter shade for the animation
            } else {
                button.setBackground(BUTTON_COLOR);
            }
        });
        timer.start();
    }
}
