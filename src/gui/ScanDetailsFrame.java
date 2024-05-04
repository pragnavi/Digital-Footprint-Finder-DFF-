package gui;

import javax.swing.*;

import util.UIEffects;

import java.awt.*;
import java.util.Map;

public class ScanDetailsFrame extends JFrame {

	private JTextArea emailTextArea;
	private JTextArea ipDetailsTextArea;
    private JTextArea googleSearchTextArea;
    private JTextArea socialMediaTextArea;
    private JButton logoutButton;

    public ScanDetailsFrame(Map<String, Object> userData) {
        initUI(userData);
    }

    private void initUI(Map<String, Object> userData) {
    	setTitle("Digital Footprint Finder");
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
        
        constraints.gridy = 0;
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
