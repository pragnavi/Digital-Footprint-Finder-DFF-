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
        add(new JLabel("Email:"), constraints);
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

        loginButton.addActionListener(e -> {
			try {
				loginAction(e);
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		});
        signupButton.addActionListener(e -> {
            SignupFrame signupFrame = new SignupFrame();
            signupFrame.setVisible(true);
            dispose();
        });
    }

    private void loginAction(ActionEvent e) throws SQLException {
        String email = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email or password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = SecurityUtil.hashPassword(password);
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
//                SwingUtilities.invokeLater(() -> new ScanEmailFrame());
//                SwingUtilities.invokeLater(() -> {
//                	ScanEmailFrame scanEmailFrame = new ScanEmailFrame();
//                	scanEmailFrame.setVisible(true);
//                });
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            if (conn != null) {
                DatabaseConnection.releaseConnection(conn);
            }
        }
    }
}

