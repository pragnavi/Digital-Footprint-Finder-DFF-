package gui;

import javax.swing.*;

import java.awt.*;
import util.UIEffects;

public class AlertsFrame extends JFrame {

    private JButton pushAlertsButton;
    private JButton logoutButton;

    public AlertsFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Alerts");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        getContentPane().setBackground(UIEffects.BACKGROUND_COLOR);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;

        Font font = new Font("Arial", Font.PLAIN, 14);

        pushAlertsButton = new JButton("Push Alerts");
        logoutButton = new JButton("Logout");

        UIEffects.styleButton(pushAlertsButton);
        UIEffects.applyButtonAnimation(pushAlertsButton);
        
        UIEffects.styleButton(logoutButton);
        UIEffects.applyButtonAnimation(logoutButton);

        constraints.gridy = 0;
        add(new JLabel("Alerts"), constraints);

        constraints.gridy++;
        add(pushAlertsButton, constraints);
        
        constraints.gridy++;
        add(logoutButton, constraints);

        pushAlertsButton.addActionListener(e -> {
            // Implement push alerts functionality here
        });
        
        logoutButton.addActionListener(e -> {
            dispose(); // Close the current frame
            AdminLoginFrame adminLoginFrame = new AdminLoginFrame();
            adminLoginFrame.setVisible(true); // Open AdminLoginFrame
        });

        pack();
        setLocationRelativeTo(null);
    }
}
