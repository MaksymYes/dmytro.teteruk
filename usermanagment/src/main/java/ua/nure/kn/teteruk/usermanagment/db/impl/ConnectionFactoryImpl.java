package ua.nure.kn.teteruk.usermanagment.db.impl;

import ua.nure.kn.teteruk.usermanagment.db.ConnectionFactory;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
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
