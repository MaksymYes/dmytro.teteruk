package ua.nure.kn.teteruk.usermanagment.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DAOFactory {

    private static final String PROPERTIES_FILE_NAME = "settings.properties";
    private static final String DAO_FACTORY = "dao.factory";

    protected static Properties properties;
    private static DAOFactory instance;

    static {
        properties = new Properties();
        try {
            properties.load(DAOFactory.class.getResourceAsStream(
                    PROPERTIES_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected DAOFactory() {
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            try {
                Class factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DAOFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public void init(Properties properties) {
        this.properties = properties;
        instance = null;
    }

    public abstract UserDao getUserDao();
}