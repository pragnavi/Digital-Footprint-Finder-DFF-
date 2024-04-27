//package gui;
//import javax.swing.*;
//
//import db.DatabaseConnection;
//import util.SecurityUtil;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.sql.*;
//
//public class LoginFrame extends JFrame {
//    private JTextField userTextField;
//    private JPasswordField passwordField;
//    private JButton loginButton;
//    private JButton signupButton;
//
//    public LoginFrame() {
//        initUI();
//    }
//
//    private void initUI() {
//        setTitle("Login");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set frame to maximum size
//        setUndecorated(true);  // Optionally remove frame decorations
//        getContentPane().setBackground(new Color(255, 250, 250));  // Soft white background
//
//        setLayout(new GridBagLayout());
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.insets = new Insets(10, 10, 10, 10);
//        constraints.gridx = 0;
//
//        Font font = new Font("Arial", Font.PLAIN, 14);
//
//        userTextField = new JTextField(20);
//        userTextField.setFont(font);
//        passwordField = new JPasswordField(20);
//        passwordField.setFont(font);
//        loginButton = new JButton("Login");
//        styleButton(loginButton);
//        signupButton = new JButton("Register Now");
//        styleButton(signupButton);
//
//        constraints.gridy = 0;
//        add(new JLabel("Username:"), constraints);
//        constraints.gridy++;
//        add(userTextField, constraints);
//        constraints.gridy++;
//        add(new JLabel("Password:"), constraints);
//        constraints.gridy++;
//        add(passwordField, constraints);
//        constraints.gridy++;
//        add(loginButton, constraints);
//        constraints.gridy++;
//        add(signupButton, constraints);
//
//        loginButton.addActionListener(this::loginAction);
//        signupButton.addActionListener(e -> {
//            SignupFrame signupFrame = new SignupFrame();
//            signupFrame.setVisible(true);
//            dispose();  // Close the login window
//        });
//    }
//
//    private void styleButton(JButton button) {
//        button.setFont(new Font("Arial", Font.BOLD, 14));
//        button.setBackground(new Color(230, 230, 250));  // Lavender background
//        button.setForeground(Color.DARK_GRAY);
//    }
//
//    private void loginAction(ActionEvent e) {
//        String username = userTextField.getText();
//        String password = new String(passwordField.getPassword());
//
//        if (username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String hashedPassword = SecurityUtil.hashPassword(password);
//        try (Connection conn = DatabaseConnection.getConnection()) {
//            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, username);
//            statement.setString(2, hashedPassword);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                JOptionPane.showMessageDialog(this, "Login Successful!");
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
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

public class LoginFrame extends JFrame {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        getContentPane().setBackground(UIEffects.BACKGROUND_COLOR);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;

        Font font = new Font("Arial", Font.PLAIN, 14);

        userTextField = new JTextField(20);
        userTextField.setFont(font);
        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        UIEffects.styleButton(loginButton);
        UIEffects.styleButton(signupButton);
        UIEffects.applyButtonAnimation(loginButton);

        constraints.gridy = 0;
        add(new JLabel("Username:"), constraints);
        constraints.gridy++;
        add(userTextField, constraints);
        constraints.gridy++;
        add(new JLabel("Password:"), constraints);
        constraints.gridy++;
        add(passwordField, constraints);
        constraints.gridy++;
        add(loginButton, constraints);
        constraints.gridy++;
        add(signupButton, constraints);

        loginButton.addActionListener(this::loginAction);
        signupButton.addActionListener(e -> {
            SignupFrame signupFrame = new SignupFrame();
            signupFrame.setVisible(true);
            dispose();
        });
    }

    private void loginAction(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = SecurityUtil.hashPassword(password);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
