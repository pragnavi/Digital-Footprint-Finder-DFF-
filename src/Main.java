import java.awt.Dimension;

import javax.swing.*;
import gui.LoginFrame;
import gui.AdminLoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create instances of both user and admin login frames
            LoginFrame loginFrame = new LoginFrame();
            AdminLoginFrame adminLoginFrame = new AdminLoginFrame();

            // Set the size of the login frames
            loginFrame.setPreferredSize(new Dimension(800, 600)); // Set your preferred size
            adminLoginFrame.setPreferredSize(new Dimension(800, 600)); // Set your preferred size

            // Create a tabbed pane to contain both login frames
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("User", loginFrame);
            tabbedPane.addTab("Admin", adminLoginFrame);

            JFrame mainFrame = new JFrame("Digital Footprint Finder");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.getContentPane().add(tabbedPane); // Use getContentPane() to add the tabbed pane
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}