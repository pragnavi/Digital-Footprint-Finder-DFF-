package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/DigitalFootprintFinder";
    private static final String USER = "root";
    private static final String PASSWORD = "Prags@1398";
    private static final int MAX_POOL_SIZE = 10;
    private static final List<Connection> connectionPool = new ArrayList<>();
    private static final List<Connection> usedConnections = new ArrayList<>();

    public static synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if ((usedConnections.size() + connectionPool.size()) < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(URL, USER, PASSWORD));
                System.out.println("Creating new connection, total created: " + (usedConnections.size() + connectionPool.size()));
            } else {
                System.out.println("Waiting for available connection");
                while (connectionPool.isEmpty()) {
                    try {
                        ConnectionPool.class.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        connectionPool.add(connection);
        ConnectionPool.class.notifyAll();
        System.out.println("Released connection, new pool size: " + connectionPool.size());
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
