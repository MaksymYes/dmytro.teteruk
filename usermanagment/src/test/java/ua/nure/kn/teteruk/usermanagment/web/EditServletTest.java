package ua.nure.kn.teteruk.usermanagment.web;

import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.teteruk.usermanagment.User;

import java.text.DateFormat;
import java.util.Date;

public class EditServletTest extends MockServletTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    @Test
    public void testEdit() {
        Date date = new Date();
        User user = new User(new Long(1000), "John", "Doe", new Date());
        getMockUserDao().expect("update", user);

        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
    }
}
