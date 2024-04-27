import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class SignupFrame extends JFrame {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton signupButton;

    public SignupFrame() {
        setTitle("Sign Up");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        userTextField = new JTextField();
        passwordField = new JPasswordField();
        signupButton = new JButton("Sign Up");

        add(new JLabel("Username:"));
        add(userTextField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel()); // Placeholder
        add(signupButton);

        signupButton.addActionListener(this::signupAction);
    }

    private void signupAction(ActionEvent e) {
        String username = userTextField.getText();
        String password = SecurityUtil.hashPassword(new String(passwordField.getPassword()));
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            int result = statement.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Signup successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Signup failed.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
        }
    }
}
