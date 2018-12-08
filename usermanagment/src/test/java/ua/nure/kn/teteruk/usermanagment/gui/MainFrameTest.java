package ua.nure.kn.teteruk.usermanagment.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static junit.extensions.jfcunit.TestHelper.cleanUp;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;

    @Before
    public void setUp() {
        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @After
    public void tearDown() {
        mainFrame.setVisible(false);
        cleanUp(this);
    }

    @Test
    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        find(JTable.class, "userTable");
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
    }

    @Test
    public void testAddUser() {
        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        find(JTextField.class, "firstNameField");
        find(JTextField.class, "lastNameField");
        find(JTextField.class, "dateOfBirthField");
        find(JButton.class, "okButton");
        find(JButton.class, "cancelButton");
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component \'" + name + "\'", component);
        return component;
    }
}