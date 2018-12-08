package ua.nure.kn.teteruk.usermanagment.db;

import static ua.nure.kn.teteruk.usermanagment.db.constants.ExceptionConstants.CANNOT_CREATE_INSTANCE;

public class DAOFactoryImpl extends DAOFactory {

    private static final String USER_DAO = "dao.ua.nure.kn.teteruk.usermanagement.db.UserDao";

    @Override
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
        return new ConnectionFactoryImpl(properties);
    }
}
