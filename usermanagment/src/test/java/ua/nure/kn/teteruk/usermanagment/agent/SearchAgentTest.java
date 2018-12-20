package ua.nure.kn.teteruk.usermanagment.agent;

import static junit.extensions.jfcunit.TestHelper.cleanUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.swing.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.ConstraintMatcher;
import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.HsqldbUserDao;
import ua.nure.kn.teteruk.usermanagment.db.MockDAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

public class SearchAgentTest extends JFCTestCase {

    private SearchGui searchGui;
    private Mock mockUserDao;
    private SearchAgent searchAgent;

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDAOFactory.class.getName());
        DAOFactory.init(properties);
        mockUserDao = ((MockDAOFactory) DAOFactory.getInstance()).getMockUserDao();

        setHelper(new JFCTestHelper());
        searchAgent = new SearchAgent();
        searchAgent.setup();
        searchGui = new SearchGui(searchAgent);
        searchGui.setVisible(true);

        find(JPanel.class, "searchPanel");
        find(JTextField.class, "firstNameField");
        find(JTextField.class, "lastNameField");
    }

    @After
    public void tearDown() {
        mockUserDao.verify();
        searchGui.setVisible(false);
        cleanUp(this);
    }

    @Test
    public void testSearch() throws DatabaseException {
        String firstName = "Jonh";
        String lastName = "Doe";
        User user = new User(firstName, lastName, null);
        User expectedUser = new User(new Long(333), firstName, lastName, null);
        Collection<User> users = new ArrayList<>();
        users.add(expectedUser);

        mockUserDao.expectAndReturn("create", user, expectedUser);
        DAOFactory.getInstance().getUserDao().create(user);

        mockUserDao.expectAndReturn("find", new ConstraintMatcher() {
            @Override
            public boolean matches(Object[] objects) {
                return objects.length == getConstraints().length;
            }

            @Override
            public Object[] getConstraints() {
                return new Object[]{firstName, lastName};
            }
        }, users);

        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));

        JButton searchButton = (JButton) find(JButton.class, "searchButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, searchButton));

    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(searchGui, 0);
        assertNotNull("Could not find component \'" + name + "\'", component);
        return component;
    }
}