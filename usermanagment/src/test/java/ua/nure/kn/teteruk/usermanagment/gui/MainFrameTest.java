package ua.nure.kn.teteruk.usermanagment.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.MockDAOFactory;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static junit.extensions.jfcunit.TestHelper.cleanUp;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;
    private Mock mockUserDao;

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDAOFactory.class.getName());
        DAOFactory.init(properties);
        mockUserDao = ((MockDAOFactory) DAOFactory.getInstance()).getMockUserDao();

        mockUserDao.expectAndReturn("findAll", new ArrayList());

        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @After
    public void tearDown() {
        mockUserDao.verify();
        mainFrame.setVisible(false);
        cleanUp(this);
    }

    @Test
    public void testBrowseControls() {
        JTable table = (JTable) find(JTable.class, "userTable");

        assertEquals(3, table.getColumnCount());
        assertEquals("ID", table.getColumnName(0));
        assertEquals("Имя", table.getColumnName(1));
        assertEquals("Фамилия", table.getColumnName(2));

        find(JPanel.class, "browsePanel");
        find(JTable.class, "userTable");
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
    }

    @Test
    public void testAddUser() {
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        Date now = new Date();

        User user = new User(firstName, lastName, now);
        User expectedUser = new User(id, firstName, lastName, now);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        List users = new ArrayList();
        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(0, userTable.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        find(JButton.class, "cancelButton");
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
        JButton okButton = (JButton) find(JButton.class, "okButton");

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        DateFormat format = DateFormat.getDateInstance();
        String date = format.format(now);
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component \'" + name + "\'", component);
        return component;
    }
}