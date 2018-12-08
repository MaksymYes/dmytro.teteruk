package ua.nure.kn.teteruk.usermanagment.db;

import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private static final String CONNECTION_DRIVER = "connection.driver";
    private static final String CONNECTION_URL = "connection.url";
    private static final String CONNECTION_USER = "connection.user";
    private static final String CONNECTION_PASS = "connection.password";
    private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:file:db/usermanagement";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl() {
        driver = DRIVER;
        url = URL;
        user = USER;
        password = PASSWORD;
    }

    public ConnectionFactoryImpl(Properties properties) {
        driver = properties.getProperty(CONNECTION_DRIVER);
        url = properties.getProperty(CONNECTION_URL);
        user = properties.getProperty(CONNECTION_USER);
        password = properties.getProperty(CONNECTION_PASS);
    }

    @Override
    public Connection createConnection() throws DatabaseException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find jdbcDriver", e);
        }

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseException("Cannot get connection", e);
        }
    }
}
