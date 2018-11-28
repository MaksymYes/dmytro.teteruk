package ua.nure.kn.teteruk.usermanagment.db;

import ua.nure.kn.teteruk.usermanagment.db.impl.ConnectionFactoryImpl;
import ua.nure.kn.teteruk.usermanagment.db.impl.HsqldbUserDao;

import java.io.IOException;
import java.util.Properties;

public class DAOFactory {

    private static final String CONNECTION_DRIVER = "connection.driver";
    private static final String CONNECTION_URL = "connection.url";
    private static final String CONNECTION_USER = "connection.user";
    private static final String CONNECTION_PASS = "connection.password";
    private static final String PROPERTIES_FILE_NAME = "settings.properties";

    private final Properties properties;

    public DAOFactory() throws IOException {
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
    }

    public UserDao getUserDao() {
        UserDao result = null;
        return result;
    }

    private ConnectionFactory getConnectionFactory() {
        String driver = properties.getProperty(CONNECTION_DRIVER);
        String url = properties.getProperty(CONNECTION_URL);
        String user = properties.getProperty(CONNECTION_USER);
        String password = properties.getProperty(CONNECTION_PASS);
        return new ConnectionFactoryImpl(driver, url, user, password);
    }
}