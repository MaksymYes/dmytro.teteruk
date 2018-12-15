package ua.nure.kn.teteruk.usermanagment.web;

import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.teteruk.usermanagment.User;

import java.util.*;

public class BrowseServletTest extends MockServletTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    @Test
    public void testBrowse() {
        User user = new User(new Long(1000), "John", "Doe", new Date());
        List<User> list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);
        doGet();
        Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull("Could not find list of users in session", collection);
        assertSame(list, collection);
    }

    @Test
    public void testEdit() {
        User user = new User(new Long(1000), "John", "Doe", new Date());
        getMockUserDao().expectAndReturn("find", new Long(1000), user);
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user in session", user);
        assertSame(user, userInSession);
    }

    @Test
    public void testDelete() {
        User user = new User(new Long(1000), "John", "Doe", new Date());
        List<User> users = new ArrayList<>();
        users.add(user);
        getMockUserDao().expectAndReturn("findAll", users);
        getMockUserDao().expectAndReturn("find", new Long(1000), user);
        getMockUserDao().expect("delete", user);
        addRequestParameter("deleteButton", "Delete");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("Find user in session", userInSession);
    }
}
