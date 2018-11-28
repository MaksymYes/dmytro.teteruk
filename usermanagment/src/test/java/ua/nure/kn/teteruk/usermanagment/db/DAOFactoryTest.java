package ua.nure.kn.teteruk.usermanagment.db;

import org.junit.Test;

import static org.junit.Assert.*;

public class DAOFactoryTest {

    @Test
    public void getUserDao() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        assertNotNull("DAOFactory is null", daoFactory);

        UserDao userDao = daoFactory.getUserDao();
        assertNotNull(userDao);
    }
}