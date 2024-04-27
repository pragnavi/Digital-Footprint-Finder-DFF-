import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        userTextField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        add(new JLabel("Username:"));
        add(userTextField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(signupButton);

        loginButton.addActionListener(this::loginAction);
        signupButton.addActionListener(e -> {
            SignupFrame signupFrame = new SignupFrame();
            signupFrame.setVisible(true);
            dispose();
        });
    }

    private void loginAction(ActionEvent e) {
//        String username = userTextField.getText();
//        String password = new String(passwordField.getPassword());
//
//        // Add your authentication logic here
//        System.out.println("Login attempted with username: " + username + " and password: " + password);
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
