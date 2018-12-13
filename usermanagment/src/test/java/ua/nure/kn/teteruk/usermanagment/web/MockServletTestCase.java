package ua.nure.kn.teteruk.usermanagment.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import org.junit.After;
import org.junit.Before;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.MockDAOFactory;

import java.util.Properties;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    private Mock mockUserDao;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDAOFactory.class.getName());
        DAOFactory.init(properties);
        setMockUserDao(((MockDAOFactory) DAOFactory.getInstance()).getMockUserDao());
    }

    @After
    public void tearDown() throws Exception {
        getMockUserDao().verify();
        super.tearDown();
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public MockServletTestCase setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
        return this;
    }
}
