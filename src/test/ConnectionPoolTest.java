package test;

import db.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {

    public static void main(String[] args) {
        // Test acquiring and releasing a connection multiple times
        try {
            for (int i = 0; i < 20; i++) {
                // Simulate random usage of connections
                new Thread(() -> {
                    try {
                        Connection conn = DatabaseConnection.getConnection();
                        System.out.println("Acquired connection: " + conn);
                        // Simulate some work with the connection
                        Thread.sleep((long) (Math.random() * 2000));  // Sleep between 0 to 2 seconds
                        DatabaseConnection.releaseConnection(conn);
                        System.out.println("Released connection: " + conn);
                    } catch (SQLException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
