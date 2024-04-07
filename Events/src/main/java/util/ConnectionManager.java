package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL_KEY = "db.url";

    private static final String USER_KEY = "db.user";

    private static final String PASSWORD_KEY = "db.password";

    public static final String DRIVER_KEY = "db.driver";


    static {
        try {
            Class.forName(Propertiy.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Propertiy.get(URL_KEY),
                    Propertiy.get(USER_KEY), Propertiy.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
