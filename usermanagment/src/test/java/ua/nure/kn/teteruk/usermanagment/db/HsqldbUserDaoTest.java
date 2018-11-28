package ua.nure.kn.teteruk.usermanagment.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.ConnectionFactory;
import ua.nure.kn.teteruk.usermanagment.db.ConnectionFactoryImpl;
import ua.nure.kn.teteruk.usermanagment.db.HsqldbUserDao;
import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Petrov";

    private User user;
    private ConnectionFactory factory;
    private UserDao dao;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        factory = new ConnectionFactoryImpl();
        return new DatabaseConnection(factory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Date birthDate = new SimpleDateFormat("d-MM-yyyy").parse("10-01-1988");

        user = new User(null, FIRST_NAME, LAST_NAME, birthDate);
        dao = new HsqldbUserDao(factory);
    }

    @Test
    public void testCreate() {
        assertNull(user.getId());

        try {
            User actualUser = dao.create(user);
            assertNotNull(actualUser);
            assertNotNull(actualUser.getId());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testFindAll() {
        int expectedSize = 2;
        try {
            Collection<User> collection = dao.findAll();
            assertNotNull("Collection is null", collection);
            assertEquals("Collection size.", expectedSize, collection.size());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }
}