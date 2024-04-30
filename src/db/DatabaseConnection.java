
package db;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        return ConnectionPool.getConnection();
    }

    public static void releaseConnection(Connection connection) {
        ConnectionPool.releaseConnection(connection);
    }
}
