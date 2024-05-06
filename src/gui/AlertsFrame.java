package gui;

import javax.swing.*;

import db.DatabaseConnection;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
//        getContentPane().setBackground(UIEffects.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(800, 600)); 

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
        	Connection conn = null;
        	try {
        		conn = DatabaseConnection.getConnection();
                String sql = "SELECT email from Users WHERE subscribed = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setBoolean(1, true);
                ResultSet resultSet = statement.executeQuery();
                
                ArrayList<String> emailList = new ArrayList<>();
                
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    emailList.add(email);
                }
                
                // Calculate the number of emails each thread will process
                int numThreads = 2; // Change this to the desired number of threads
                int emailsPerThread = emailList.size() / numThreads;

                // Create and start multiple threads to send emails
                List<Thread> threads = new ArrayList<>();
                for (int i = 0; i < numThreads; i++) {
                    int startIdx = i * emailsPerThread;
                    int endIdx = (i == numThreads - 1) ? emailList.size() : (i + 1) * emailsPerThread;

                    // Create a sublist of emailList for the current thread
                    List<String> sublist = emailList.subList(startIdx, endIdx);

                    // Create a Runnable task for sending emails in the sublist
                    Runnable sendEmailTask = () -> {
                        for (String email : sublist) {
                        	ScanEmail.executeScan(email);
                            try {
								SendEmail.sendEmail(email);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        }
                    };

                    // Create and start a new thread for sending emails in the sublist
                    Thread thread = new Thread(sendEmailTask);
                    thread.start();
                    threads.add(thread);
                }

                // Wait for all threads to finish
                for (Thread thread : threads) {
                    thread.join();
                }
                
        	} catch (SQLException | InterruptedException e1) {
				e1.printStackTrace();
			} finally {
                if (conn != null) {
                    DatabaseConnection.releaseConnection(conn);
                }
            }
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
