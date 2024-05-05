//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import db.DatabaseConnection;
//import util.SecurityUtil;
//import util.UIEffects;
//import java.sql.*;
//
//public class AdminLoginFrame extends JPanel {
//	private JTextField adminUserTextField;
//    private JPasswordField adminPasswordField;
//    private JButton adminLoginButton;
//
//    public AdminLoginFrame() {
//        initUI();
//    }
//
//    private void initUI() {
//        setLayout(new GridBagLayout());
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.insets = new Insets(10, 10, 10, 10);
//        constraints.gridx = 0;
//
//        Font font = new Font("Arial", Font.PLAIN, 14);
//
//        adminUserTextField = new JTextField(20);
//        adminUserTextField.setFont(font);
//        adminPasswordField = new JPasswordField(20);
//        adminPasswordField.setFont(font);
//        adminLoginButton = new JButton("Admin Login");
//
//        UIEffects.styleButton(adminLoginButton);
//        UIEffects.applyButtonAnimation(adminLoginButton);
//
//        constraints.gridy = 0;
//        add(new JLabel("Admin Email:"), constraints);
//        constraints.gridy++;
//        add(adminUserTextField, constraints);
//        constraints.gridy++;
//        add(new JLabel("Admin Password:"), constraints);
//        constraints.gridy++;
//        add(adminPasswordField, constraints);
//        constraints.gridy++;
//        add(adminLoginButton, constraints);
//
//        adminLoginButton.addActionListener(this::adminLoginAction);
//    }
//
//    private void adminLoginAction(ActionEvent e) {
//    	String email = adminUserTextField.getText();
//        String password = new String(adminPasswordField.getPassword());
//
//        if (email.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Email or password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
////        String hashedPassword = SecurityUtil.hashPassword(password);
//        Connection conn = null;
//        try {
//            conn = DatabaseConnection.getConnection();
//            String sql = "SELECT * FROM AdminUsers WHERE email = ? AND password = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, email);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                JOptionPane.showMessageDialog(this, "Admin Login Successful!");
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            if (conn != null) {
//                DatabaseConnection.releaseConnection(conn);
//            }
//        }
//    }
//}
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import db.DatabaseConnection;
import util.SecurityUtil;
import util.UIEffects;
import java.sql.*;

public class AdminLoginFrame extends JPanel {
    private JTextField adminUserTextField;
    private JPasswordField adminPasswordField;
    private JButton adminLoginButton;

    public AdminLoginFrame() {
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;

        Font font = new Font("Arial", Font.PLAIN, 14);

        adminUserTextField = new JTextField(20);
        adminUserTextField.setFont(font);
        adminPasswordField = new JPasswordField(20);
        adminPasswordField.setFont(font);
        adminLoginButton = new JButton("Admin Login");

        UIEffects.styleButton(adminLoginButton);
        UIEffects.applyButtonAnimation(adminLoginButton);

        constraints.gridy = 0;
        add(new JLabel("Admin Email:"), constraints);
        constraints.gridy++;
        add(adminUserTextField, constraints);
        constraints.gridy++;
        add(new JLabel("Admin Password:"), constraints);
        constraints.gridy++;
        add(adminPasswordField, constraints);
        constraints.gridy++;
        add(adminLoginButton, constraints);

        adminLoginButton.addActionListener(this::adminLoginAction);
    }

    private void adminLoginAction(ActionEvent e) {
        String email = adminUserTextField.getText();
        String password = new String(adminPasswordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email or password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = SecurityUtil.hashPassword(password);
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM AdminUsers WHERE email = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Open AlertsFrame upon successful login
                AlertsFrame alertsFrame = new AlertsFrame();
                alertsFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                DatabaseConnection.releaseConnection(conn);
            }
        }
    }
}
