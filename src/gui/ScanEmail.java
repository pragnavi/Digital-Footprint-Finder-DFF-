package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.SwingUtilities;

public class ScanEmail {

    public static void executeTool(String userEmail, boolean subscribed) {
        try {
        	String currentDirectory = System.getProperty("user.dir");

            Process process = Runtime.getRuntime().exec(new String[]{
                    "./src/gui/mosint",
                    userEmail,
                    "-s",
                    "-o", "output.json",
                    "-c", "./src/gui/config.yaml"
            });

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                Map<String, Object> userData = readOutputJson();

                if (userData != null) {
                    SwingUtilities.invokeLater(() -> {
                        ScanDetailsFrame userDetailsUI = new ScanDetailsFrame(userData, subscribed);
                        userDetailsUI.setVisible(true);
                    });
                } else {
                    System.out.println("Failed to read output.json");
                }
            } else {
                System.out.println("Tool execution failed");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static Map<String, Object> readOutputJson() {
        Map<String, Object> userData = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("output.json"))) {
            Gson gson = new Gson();
            userData = gson.fromJson(reader, new TypeToken<Map<String, Object>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userData;
    }
}
