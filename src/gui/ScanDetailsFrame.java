package gui;

import javax.swing.*;

import db.DatabaseConnection;
import util.UIEffects;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ScanDetailsFrame extends JFrame {

	private JTextArea emailTextArea;
	private JTextArea ipDetailsTextArea;
    private JTextArea googleSearchTextArea;
    private JTextArea socialMediaTextArea;
    private JButton logoutButton;
    
    private JToggleButton subscribeToggleButton;

    public ScanDetailsFrame(Map<String, Object> userData, boolean isSubscribed) {
        initUI(userData, isSubscribed);
    }

    private void initUI(Map<String, Object> userData, boolean isSubscribed) {
    	setTitle("Digital Footprint Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
//        getContentPane().setBackground(UIEffects.BACKGROUND_COLOR);
        
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        
        Font font = new Font("Arial", Font.PLAIN, 14);

        // Create JTextAreas to display information
        emailTextArea = new JTextArea();
        emailTextArea.setEditable(false);
        JScrollPane emailScrollPane = new JScrollPane(emailTextArea);

        
        ipDetailsTextArea = new JTextArea(10, 30);
        ipDetailsTextArea.setEditable(false);
        JScrollPane ipScrollPane = new JScrollPane(ipDetailsTextArea);

        googleSearchTextArea = new JTextArea(10, 30);
        googleSearchTextArea.setEditable(false);
        JScrollPane googleScrollPane = new JScrollPane(googleSearchTextArea);
        
        socialMediaTextArea = new JTextArea(10, 30);
        socialMediaTextArea.setEditable(false);
        JScrollPane socialMediaScrollPane = new JScrollPane(socialMediaTextArea);
        
        if (userData.containsKey("email")) {
        	emailTextArea.append("Email: " + userData.get("email") + "\n");
        }
        
        if (userData.containsKey("ipapi")) {
            Map<String, Object> ipapiData = (Map<String, Object>) userData.get("ipapi");
            ipDetailsTextArea.append("IP Address: " + ipapiData.get("ip") + "\n");
            ipDetailsTextArea.append("City: " + ipapiData.get("city") + "\n");
            ipDetailsTextArea.append("Region: " + ipapiData.get("region") + "\n");
            ipDetailsTextArea.append("Country: " + ipapiData.get("country") + "\n");
            ipDetailsTextArea.append("Postal Code: " + ipapiData.get("postal") + "\n");
        }

        if (userData.containsKey("google_search")) {
            java.util.List<String> googleSearchList = (java.util.List<String>) userData.get("google_search");
            if (googleSearchList != null && !googleSearchList.isEmpty()) {
	            for (String result : googleSearchList) {
	                googleSearchTextArea.append(result + "\n");
	            }
            }
        }

        if (userData.containsKey("instagram_exists")) {
            socialMediaTextArea.append("Instagram: " + userData.get("instagram_exists") + "\n");
        }

        if (userData.containsKey("spotify_exists")) {
            socialMediaTextArea.append("Facebook: " + userData.get("facebook_exists") + "\n");
        }

        if (userData.containsKey("twitter_exists")) {
            socialMediaTextArea.append("Twitter: " + userData.get("twitter_exists") + "\n");
        }
        
        subscribeToggleButton = new JToggleButton(isSubscribed ? "Subscribed" : "Subscribe");
        subscribeToggleButton.addActionListener(e -> {
        	Connection conn = null;
        	try {
        		conn = DatabaseConnection.getConnection();
        		if (subscribeToggleButton.isSelected()) {
                    String sql = "UPDATE Users SET subscribed = ? WHERE email = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setBoolean(1, true);
                    statement.setString(2, (String) userData.get("email"));
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Subscribed!");
                        subscribeToggleButton.setText("Unsubscribe");
                    } else {
                        JOptionPane.showMessageDialog(this, "Subscription failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
            	}
        		else {
        			String sql = "UPDATE Users SET subscribed = ? WHERE email = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setBoolean(1, false);
                    statement.setString(2, (String) userData.get("email"));
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Unsubscribed!");
                        subscribeToggleButton.setText("Subscribe");
                    } else {
                        JOptionPane.showMessageDialog(this, "Unsubscription failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        	} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
                if (conn != null) {
                    DatabaseConnection.releaseConnection(conn);
                }
            }
        });
        
        constraints.gridy = 0;
        add(new JLabel("Subscribe for bi-weekly scans"), constraints);        
        
        constraints.gridy++;
        add(subscribeToggleButton, constraints);
        
        constraints.gridy++;
        add(new JLabel("Scan Results"), constraints);
        
        constraints.gridy++;
        add(emailTextArea, constraints);
        
        constraints.gridy++;
        add(new JLabel("ipDetails:"), constraints);
        
        constraints.gridy++;
        add(ipDetailsTextArea, constraints);
        
        constraints.gridy++;
        add(new JLabel("googleSearch:"), constraints);
        
        constraints.gridy++;
        add(googleSearchTextArea, constraints);
        
        constraints.gridy++;
        add(new JLabel("Social Media Presence:"), constraints);
        
        constraints.gridy++;
        add(socialMediaTextArea, constraints);
        

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            showLoginFrame();
        });
        constraints.gridy++;
        add(logoutButton, constraints);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void showLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}
