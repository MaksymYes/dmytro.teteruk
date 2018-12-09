package ua.nure.kn.teteruk.usermanagment.db;

import com.mockobjects.dynamic.Mock;

public class MockDAOFactory extends DAOFactory {

    private Mock mockUserDao;

    public MockDAOFactory() {
        mockUserDao = new Mock(UserDao.class);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    @Override
    public UserDao getUserDao() {
        return (UserDao) mockUserDao.proxy();
    }
}
