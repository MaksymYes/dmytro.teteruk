package ua.nure.kn.teteruk.usermanagment.db;

import ua.nure.kn.teteruk.usermanagment.db.impl.ConnectionFactoryImpl;

import java.io.IOException;
import java.util.Properties;

import static ua.nure.kn.teteruk.usermanagment.db.constants.ExceptionConstants.CANNOT_CREATE_INSTANCE;

public class DAOFactory {

    private static final String CONNECTION_DRIVER = "connection.driver";
    private static final String CONNECTION_URL = "connection.url";
    private static final String CONNECTION_USER = "connection.user";
    private static final String CONNECTION_PASS = "connection.password";
    private static final String PROPERTIES_FILE_NAME = "settings.properties";
    private static final String USER_DAO = "dao.ua.nure.teteruk.usermanagement.db.UserDao";

    private final Properties properties;

    private final static DAOFactory INSTANCE = new DAOFactory();

    private DAOFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    public UserDao getUserDao() {
        try {
            Class clazz = Class.forName(properties.getProperty(USER_DAO));
            UserDao userDao = (UserDao) clazz.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
            return userDao;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(CANNOT_CREATE_INSTANCE, e);
        }
    }

    private ConnectionFactory getConnectionFactory() {
        String driver = properties.getProperty(CONNECTION_DRIVER);
        String url = properties.getProperty(CONNECTION_URL);
        String user = properties.getProperty(CONNECTION_USER);
        String password = properties.getProperty(CONNECTION_PASS);
        return new ConnectionFactoryImpl(driver, url, user, password);
    }
}