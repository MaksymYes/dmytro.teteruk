package ua.nure.kn.teteruk.usermanagment.web;

import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.teteruk.usermanagment.User;

import java.text.DateFormat;
import java.util.Date;

public class DetailsServletTest extends MockServletTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    @Test
    public void testDetails() {
        Date date = new Date();
        User user = new User(new Long(1000), "John", "Doe", date);
        getMockUserDao().expectAndReturn("find", new Long(1000), user);

        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
    }
}