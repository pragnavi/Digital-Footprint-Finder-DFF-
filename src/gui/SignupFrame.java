package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import db.DatabaseConnection;
import util.SecurityUtil;
import util.UIEffects;
import java.sql.*;

public class SignupFrame extends JFrame {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JButton backButton;

    public SignupFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Sign Up");
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

        userTextField = new JTextField(20);
        userTextField.setFont(font);
        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        signupButton = new JButton("Sign Up");
        backButton = new JButton("Back to Login");

        UIEffects.styleButton(signupButton);
        UIEffects.styleButton(backButton);
        UIEffects.applyButtonAnimation(signupButton);

        constraints.gridy = 0;
        add(new JLabel("Username:"), constraints);
        constraints.gridy++;
        add(userTextField, constraints);
        constraints.gridy++;
        add(new JLabel("Password:"), constraints);
        constraints.gridy++;
        add(passwordField, constraints);
        constraints.gridy++;
        add(signupButton, constraints);
        constraints.gridy++;
        add(backButton, constraints);

        signupButton.addActionListener(this::signupAction);
        backButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
        });
    }

    private void signupAction(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = SecurityUtil.hashPassword(password);
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Signup successful!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Username already exists or database error: " + ex.getMessage(), "Signup Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                DatabaseConnection.releaseConnection(conn);
            }
        }
    }
}
