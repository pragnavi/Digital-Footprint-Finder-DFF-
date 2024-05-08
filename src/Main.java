import java.awt.Dimension;

import javax.swing.*;
import gui.LoginFrame;
import gui.AdminLoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            AdminLoginFrame adminLoginFrame = new AdminLoginFrame();

            loginFrame.setPreferredSize(new Dimension(800, 600));
            adminLoginFrame.setPreferredSize(new Dimension(800, 600));

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("User", loginFrame);
            tabbedPane.addTab("Admin", adminLoginFrame);

            JFrame mainFrame = new JFrame("Digital Footprint Finder");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.getContentPane().add(tabbedPane);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}