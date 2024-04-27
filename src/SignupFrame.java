//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.sql.*;
//
//public class SignupFrame extends JFrame {
//    private JTextField userTextField;
//    private JPasswordField passwordField;
//    private JButton signupButton;
//    private JButton backButton;
//
//    public SignupFrame() {
//        initUI();
//    }
//
//    private void initUI() {
//        setTitle("Sign Up");
//        setSize(320, 220);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        getContentPane().setBackground(new Color(255, 250, 250));  // Soft white background
//
//        setLayout(new GridBagLayout());
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.insets = new Insets(10, 10, 10, 10);
//
//        Font font = new Font("Arial", Font.PLAIN, 14);
//
//        userTextField = new JTextField(20);
//        userTextField.setFont(font);
//        passwordField = new JPasswordField(20);
//        passwordField.setFont(font);
//        signupButton = new JButton("Sign Up");
//        styleButton(signupButton);
//        backButton = new JButton("Back to Login");
//        styleButton(backButton);
//
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        add(new JLabel("Username:"), constraints);
//        constraints.gridy++;
//        add(userTextField, constraints);
//        constraints.gridy++;
//        add(new JLabel("Password:"), constraints);
//        constraints.gridy++;
//        add(passwordField, constraints);
//        constraints.gridy++;
//        constraints.gridwidth = 1;
//        add(signupButton, constraints);
//        constraints.gridx++;
//        add(backButton, constraints);
//
//        signupButton.addActionListener(this::signupAction);
//        backButton.addActionListener(e -> {
//            LoginFrame loginFrame = new LoginFrame();
//            loginFrame.setVisible(true);
//            dispose();  // Close the signup window
//        });
//    }
//
//    private void styleButton(JButton button) {
//        button.setFont(new Font("Arial", Font.BOLD, 14));
//        button.setBackground(new Color(230, 230, 250));  // Lavender background
//        button.setForeground(Color.DARK_GRAY);
//    }
//
//    private void signupAction(ActionEvent e) {
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
//            String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, username);
//            statement.setString(2, hashedPassword);
//            statement.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Signup successful!");
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "Username already exists.", "Signup Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set frame to maximum size
        setUndecorated(true); // Optionally remove frame decorations
        getContentPane().setBackground(new Color(255, 250, 250)); // Soft white background

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        Font font = new Font("Arial", Font.PLAIN, 14);

        userTextField = new JTextField(20);
        userTextField.setFont(font);
        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        signupButton = new JButton("Sign Up");
        styleButton(signupButton);
        backButton = new JButton("Back to Login");
        styleButton(backButton);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Username:"), constraints);
        constraints.gridy++;
        add(userTextField, constraints);
        constraints.gridy++;
        add(new JLabel("Password:"), constraints);
        constraints.gridy++;
        add(passwordField, constraints);
        constraints.gridy++;
        constraints.gridwidth = 1;
        add(signupButton, constraints);
        constraints.gridx++;
        add(backButton, constraints);

        signupButton.addActionListener(this::signupAction);
        backButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose(); // Close the signup window
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(230, 230, 250)); // Lavender background
        button.setForeground(Color.DARK_GRAY);
    }

    private void signupAction(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = SecurityUtil.hashPassword(password);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Signup successful!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Username already exists.", "Signup Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
