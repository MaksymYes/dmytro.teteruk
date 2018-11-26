package ua.nure.kn.teteruk.usermanagment.db.impl;

import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.*;

public class HsqldbUserDaoTest {

    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Petrov";

    private User user;
    private UserDao dao;

    @Before
    public void setUp() throws Exception {
        Date birthDate = new SimpleDateFormat("d-MM-yyyy").parse("10-01-1988");

        user = new User(null, FIRST_NAME, LAST_NAME, birthDate);
        dao = new HsqldbUserDao();
    }

    @Test
    public void testCreate() throws ParseException {
        assertNull(user.getId());

        try {
            User actualUser = dao.create(user);
            assertNotNull(actualUser);
            assertNotNull(actualUser.getId());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }
}